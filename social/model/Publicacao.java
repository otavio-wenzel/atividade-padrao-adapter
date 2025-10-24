package social.model;

import social.Plataforma;
import java.time.Instant;


public final class Publicacao<R> {
    private final R idPlataforma;
    private final Plataforma plataforma;
    private final String urlPublica;
    private final Instant criadoEm;


    public Publicacao(R idPlataforma, Plataforma plataforma, String urlPublica, Instant criadoEm) {
        this.idPlataforma = idPlataforma;
        this.plataforma = plataforma;
        this.urlPublica = urlPublica;
        this.criadoEm = criadoEm;
    }


    public R getIdPlataforma() { return idPlataforma; }
    public Plataforma getPlataforma() { return plataforma; }
    public String getUrlPublica() { return urlPublica; }
    public Instant getCriadoEm() { return criadoEm; }


    @Override public String toString() {
        return "Publicacao{" + plataforma + ", id=" + idPlataforma + ", url=" + urlPublica + "}";
    }
}