package social.impl;

import social.Plataforma;
import social.config.Config;
import social.core.*;
import social.factory.SocialMediaFactory;
import social.model.*;
import social.resposta.*;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.*;

public final class GerenciadorMidiaSocialImpl implements GerenciadorMidiaSocial, AutoCloseable {
    private final Config config;
    private final ConcurrentMap<Plataforma, CanalMidiaSocial<?>> canais = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler;

    public GerenciadorMidiaSocialImpl(Config config) {
        this.config = Objects.requireNonNull(config);
        this.scheduler = Executors.newScheduledThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
        );
    }

    @SuppressWarnings("unchecked")
    private <R> CanalMidiaSocial<R> canal(Plataforma p) {
        return (CanalMidiaSocial<R>) canais.computeIfAbsent(p, key -> SocialMediaFactory.criar(key, config));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Resultado<Publicacao<R>> publicar(Plataforma plataforma, Conteudo conteudo) {
        CanalMidiaSocial<R> c = canal(plataforma);
        return (Resultado<Publicacao<R>>) (Resultado<?>) c.publicar(conteudo);
    }

    @Override
    public <R> CompletableFuture<Resultado<Publicacao<R>>> agendar(Plataforma plataforma, Conteudo conteudo, Instant quando) {
        long delay = Math.max(0, quando.toEpochMilli() - System.currentTimeMillis());
        CompletableFuture<Resultado<Publicacao<R>>> fut = new CompletableFuture<>();
        scheduler.schedule(() -> {
            try { fut.complete(publicar(plataforma, conteudo)); }
            catch (Throwable t) { fut.completeExceptionally(t); }
        }, delay, TimeUnit.MILLISECONDS);
        return fut;
    }

    @Override
    public Resultado<Estatisticas> estatisticas(Plataforma plataforma, Object id) {
        try {
            return switch (plataforma) {
                case TWITTER, INSTAGRAM, LINKEDIN -> canal(plataforma).estatisticas(String.valueOf(id));
                case TIKTOK -> canal(plataforma).estatisticas(Long.valueOf(String.valueOf(id)));
            };
        } catch (NumberFormatException nfe) {
            return Resultado.falha(plataforma, java.util.List.of(new Erro("INVALID_ID", "Formato de ID inválido para " + plataforma)));
        }
    }

    @Override
    public Resultado<Void> deletar(Plataforma plataforma, Object id) {
        try {
            return switch (plataforma) {
                case TWITTER, INSTAGRAM, LINKEDIN -> canal(plataforma).deletar(String.valueOf(id));
                case TIKTOK -> canal(plataforma).deletar(Long.valueOf(String.valueOf(id)));
            };
        } catch (NumberFormatException nfe) {
            return Resultado.falha(plataforma, java.util.List.of(new Erro("INVALID_ID", "Formato de ID inválido para " + plataforma)));
        }
    }

    @Override
    public void close() { scheduler.shutdownNow(); }
}