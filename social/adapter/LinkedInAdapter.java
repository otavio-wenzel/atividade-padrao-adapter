package social.adapter;

import social.Plataforma;
import social.core.CanalMidiaSocial;
import social.model.*;
import social.resposta.*;
import social.legacy.LinkedInClient;
import social.strategy.FormatacaoConteudoStrategy;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public final class LinkedInAdapter implements CanalMidiaSocial<String> {
    private final LinkedInClient api;
    private final FormatacaoConteudoStrategy formatacao;

    public LinkedInAdapter(LinkedInClient api, FormatacaoConteudoStrategy formatacao) {
        this.api = api; this.formatacao = formatacao;
    }

    @Override
    public Resultado<Publicacao<String>> publicar(Conteudo c) {
        try {
            String text = formatacao.formatar(c);
            byte[] img = c.getMidias().isEmpty() ? null : c.getMidias().get(0).getBytes(StandardCharsets.UTF_8);
            String id = api.share(text, img);
            Publicacao<String> pub = new Publicacao<>(id, Plataforma.LINKEDIN, "https://www.linkedin.com/feed/update/"+id, Instant.now());
            return Resultado.sucesso(pub, Plataforma.LINKEDIN);
        } catch (RuntimeException e) {
            String code = "AUTH".equals(e.getMessage()) ? "AUTH_FAILED" : "API_ERROR";
            return Resultado.falha(Plataforma.LINKEDIN, java.util.List.of(new Erro(code, e.getMessage())));
        }
    }

    @Override
    public Resultado<Estatisticas> estatisticas(String id) {
        try {
            var s = api.statsOf(id);
            return Resultado.sucesso(new Estatisticas(s.reactions(), s.comments(), s.reshares(), s.views()), Plataforma.LINKEDIN);
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.LINKEDIN, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }

    @Override
    public Resultado<Void> deletar(String id) {
        try {
            boolean ok = api.deleteShare(id);
            return ok ? Resultado.sucesso(null, Plataforma.LINKEDIN)
            : Resultado.falha(Plataforma.LINKEDIN, java.util.List.of(new Erro("NOT_FOUND", "Share não encontrado")));
        } catch (RuntimeException e) {
            return Resultado.falha(Plataforma.LINKEDIN, java.util.List.of(new Erro("API_ERROR", e.getMessage())));
        }
    }
}