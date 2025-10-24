package social.core;

import social.model.*;
import social.resposta.Resultado;


public interface CanalMidiaSocial<R> {
    Resultado<Publicacao<R>> publicar(Conteudo conteudo);
    Resultado<Estatisticas> estatisticas(R id);
    Resultado<Void> deletar(R id);
}