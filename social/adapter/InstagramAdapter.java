package social.adapter;

import social.Plataforma;
import social.core.CanalMidiaSocial;
import social.model.*;
import social.resposta.*;
import social.legacy.InstagramSdk;
import social.strategy.FormatacaoConteudoStrategy;
import java.time.Instant;

public final class InstagramAdapter implements CanalMidiaSocial<String> {
    private final InstagramSdk api;
    private final FormatacaoConteudoStrategy formatacao;

    public InstagramAdapter(InstagramSdk api, FormatacaoConteudoStrategy formatacao) {
        this.api = api; this.formatacao = formatacao;
    }

    @Override
    public Resultado<Publicacao<String>> publicar(Conteudo c) {
        try {
            String caption = formatacao.formatar(c);
            String imageUrl = c.getMidias().isEmpty() ? null : c.getMidias().get(0);
            String id = api.publishPhoto(caption, imageUrl);
            Publicacao<String> pub = new Publicacao<>(id, Plataforma.INSTAGRAM, "https://instagram.com/p/"+id, Instant.now());
            return Resultado.sucesso(pub, Plataforma.INSTAGRAM);
        } catch (RuntimeException e) {
            String code = switch (e.getMessage()) { case "AUTH" -> "AUTH_FAILED"; case "INVALID_MEDIA" -> "INVALID_MEDIA"; default -> "API_ERROR"; };
            return Resultado.falha(Plataforma.INSTAGRAM, java.util.List.of(new Erro(code, e.getMessage())));
        }
    }

    @Override
    public Resultado<Estatisticas> estatisticas(String id) {
        try {
            var ins = api.getInsights(id);
            return Resultado.sucesso(new Estatisticas(ins.likes(), ins.comments(), ins.shares(), ins.impressions()), Plataforma.INSTAGRAM);
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.INSTAGRAM, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }

    @Override
    public Resultado<Void> deletar(String id) {
        try {
            boolean ok = api.removeMedia(id);
            return ok ? Resultado.sucesso(null, Plataforma.INSTAGRAM)
            : Resultado.falha(Plataforma.INSTAGRAM, java.util.List.of(new Erro("NOT_FOUND", "Mídia não encontrada")));
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.INSTAGRAM, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }
}