package social.adapter;

import social.Plataforma;
import social.core.CanalMidiaSocial;
import social.model.*;
import social.resposta.*;
import social.legacy.TikTokApi;
import social.strategy.FormatacaoConteudoStrategy;
import java.time.Instant;

public final class TikTokAdapter implements CanalMidiaSocial<Long> {
    private final TikTokApi api;
    private final FormatacaoConteudoStrategy formatacao;

    public TikTokAdapter(TikTokApi api, FormatacaoConteudoStrategy formatacao) {
        this.api = api; this.formatacao = formatacao;
    }

    @Override
    public Resultado<Publicacao<Long>> publicar(Conteudo c) {
        try {
            String desc = formatacao.formatar(c);
            String path = c.getMidias().isEmpty() ? null : c.getMidias().get(0);
            long id = api.uploadVideo(desc, path);
            Publicacao<Long> pub = new Publicacao<>(id, Plataforma.TIKTOK, null, Instant.now());
            return Resultado.sucesso(pub, Plataforma.TIKTOK);
        } catch (RuntimeException e) {
            String code = switch (e.getMessage()) { case "AUTH" -> "AUTH_FAILED"; case "INVALID_MEDIA" -> "INVALID_MEDIA"; default -> "API_ERROR"; };
            return Resultado.falha(Plataforma.TIKTOK, java.util.List.of(new Erro(code, e.getMessage())));
        }
    }

    @Override
    public Resultado<Estatisticas> estatisticas(Long id) {
        try {
            var s = api.stats(id);
            return Resultado.sucesso(new Estatisticas(s.likes(), s.comments(), s.shares(), s.views()), Plataforma.TIKTOK);
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.TIKTOK, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }

    @Override
    public Resultado<Void> deletar(Long id) {
        try {
            api.delete(id);
            return Resultado.sucesso(null, Plataforma.TIKTOK);
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.TIKTOK, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }
}