package social.strategy;

import social.model.Conteudo;


public class TextoPlanoStrategy implements FormatacaoConteudoStrategy {
    @Override public String formatar(Conteudo conteudo) {
        return conteudo.getTexto();
    }
}