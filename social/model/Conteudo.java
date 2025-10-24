package social.model;

import java.time.Instant;
import java.util.Collections;
import java.util.List;


public final class Conteudo {
    private final String texto;
    private final List<String> midias;
    private final Instant publicarEm;


    public Conteudo(String texto, List<String> midias, Instant publicarEm) {
        this.texto = texto == null ? "" : texto;
        this.midias = midias == null ? List.of() : List.copyOf(midias);
        this.publicarEm = publicarEm;
    }


    public String getTexto() { return texto; }
    public List<String> getMidias() { return Collections.unmodifiableList(midias); }
    public Instant getPublicarEm() { return publicarEm; }
}