package social.core;

import social.Plataforma;
import social.model.*;
import social.resposta.Resultado;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public interface GerenciadorMidiaSocial {
    <R> Resultado<Publicacao<R>> publicar(Plataforma plataforma, Conteudo conteudo);
    <R> CompletableFuture<Resultado<Publicacao<R>>> agendar(Plataforma plataforma, Conteudo conteudo, Instant quando);
    Resultado<Estatisticas> estatisticas(Plataforma plataforma, Object id);
    Resultado<Void> deletar(Plataforma plataforma, Object id);
}