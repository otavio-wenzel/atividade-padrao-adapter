package social;

import social.config.Config;
import social.impl.GerenciadorMidiaSocialImpl;
import social.model.Conteudo;
import java.time.Instant;
import java.util.List;

public final class Main {
    public static void main(String[] args) {
        Config cfg = Config.fromEnv();

        cfg.set("TWITTER_TOKEN",  cfg.get("TWITTER_TOKEN")  == null ? "demo" : cfg.get("TWITTER_TOKEN"));
        cfg.set("INSTAGRAM_TOKEN",cfg.get("INSTAGRAM_TOKEN")== null ? "demo" : cfg.get("INSTAGRAM_TOKEN"));
        cfg.set("LINKEDIN_TOKEN", cfg.get("LINKEDIN_TOKEN") == null ? "demo" : cfg.get("LINKEDIN_TOKEN"));
        cfg.set("TIKTOK_TOKEN",   cfg.get("TIKTOK_TOKEN")   == null ? "demo" : cfg.get("TIKTOK_TOKEN"));


        try (GerenciadorMidiaSocialImpl g = new GerenciadorMidiaSocialImpl(cfg)) {
            var conteudoAgora = new Conteudo(
                "Olá, mundo do Adapter! #escobar",
                List.of("https://img.example/foto.jpg"),
                null
            );

            var r1 = g.publicar(Plataforma.TWITTER, conteudoAgora);
            System.out.println(r1);

            var conteudoAgendado = new Conteudo(
                "Post agendado para LinkedIn",
                List.of(),
                Instant.now().plusSeconds(1)
            );

            g.agendar(Plataforma.LINKEDIN, conteudoAgendado, conteudoAgendado.getPublicarEm())
            .thenAccept(r -> System.out.println("Agendado => " + r))
            .join();


            if (r1.getValor() != null) {
                var id = r1.getValor().getIdPlataforma();
                var stats = g.estatisticas(Plataforma.TWITTER, id);
                System.out.println(stats);
            }
        }
    }
}