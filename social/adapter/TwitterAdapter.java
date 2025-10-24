package social.adapter;

import social.Plataforma;
import social.core.CanalMidiaSocial;
import social.model.*;
import social.resposta.*;
import social.legacy.TwitterApi;
import social.strategy.FormatacaoConteudoStrategy;
import java.time.Instant;

public final class TwitterAdapter implements CanalMidiaSocial<String> {
    private final TwitterApi api;
    private final FormatacaoConteudoStrategy formatacao;

    public TwitterAdapter(TwitterApi api, FormatacaoConteudoStrategy formatacao) {
        this.api = api; this.formatacao = formatacao;
    }

    @Override
    public Resultado<Publicacao<String>> publicar(Conteudo c) {
        try {
            String text = formatacao.formatar(c);
            String id = api.postTweet(text, c.getMidias());
            Publicacao<String> pub = new Publicacao<>(id, Plataforma.TWITTER, "https://twitter.com/i/web/status/"+id, Instant.now());
            return Resultado.sucesso(pub, Plataforma.TWITTER);
        } catch (RuntimeException e) {
            String code = switch (e.getMessage()) { case "AUTH" -> "AUTH_FAILED"; case "INVALID_TEXT" -> "INVALID_TEXT"; default -> "API_ERROR"; };
            return Resultado.falha(Plataforma.TWITTER, java.util.List.of(new Erro(code, e.getMessage())));
        }
    }

    @Override
    public Resultado<Estatisticas> estatisticas(String id) {
        try {
            var m = api.getMetrics(id);
            return Resultado.sucesso(new Estatisticas(m.likes(), m.replies(), m.retweets(), m.views()), Plataforma.TWITTER);
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.TWITTER, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }

    @Override
    public Resultado<Void> deletar(String id) {
        try {
            boolean ok = api.deleteTweet(id);
            return ok ? Resultado.sucesso(null, Plataforma.TWITTER)
            : Resultado.falha(Plataforma.TWITTER, java.util.List.of(new Erro("NOT_FOUND", "Tweet não encontrado")));
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.TWITTER, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }
}