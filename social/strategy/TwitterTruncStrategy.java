package social.strategy;

import social.model.Conteudo;

public class TwitterTruncStrategy implements FormatacaoConteudoStrategy {
    private final int limite;
    public TwitterTruncStrategy(int limite) { this.limite = Math.max(10, limite); }
        @Override public String formatar(Conteudo c) {
        String t = c.getTexto();
        if (t == null) return "";
        return t.length() <= limite ? t : t.substring(0, limite - 1) + "…";
    }
}