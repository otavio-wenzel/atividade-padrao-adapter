package social.resposta;

import social.Plataforma;
import java.util.List;
import java.util.UUID;


public final class Resultado<T> {
    public enum Status { SUCESSO, FALHA, PARCIAL }


    private final Status status;
    private final T valor;
    private final List<Erro> erros;
    private final Plataforma plataforma;
    private final String correlationId;


    private Resultado(Status status, T valor, List<Erro> erros, Plataforma plataforma, String correlationId) {
        this.status = status;
        this.valor = valor;
        this.erros = erros == null ? List.of() : List.copyOf(erros);
        this.plataforma = plataforma;
        this.correlationId = correlationId == null ? UUID.randomUUID().toString() : correlationId;
    }


    public static <T> Resultado<T> sucesso(T valor, Plataforma p) {
        return new Resultado<>(Status.SUCESSO, valor, List.of(), p, null);
    }


    public static <T> Resultado<T> falha(Plataforma p, List<Erro> erros) {
        return new Resultado<>(Status.FALHA, null, erros, p, null);
    }


    public static <T> Resultado<T> parcial(T valor, Plataforma p, List<Erro> erros) {
        return new Resultado<>(Status.PARCIAL, valor, erros, p, null);
    }


    public Status getStatus() { return status; }
    public T getValor() { return valor; }
    public List<Erro> getErros() { return erros; }
    public Plataforma getPlataforma() { return plataforma; }
    public String getCorrelationId() { return correlationId; }


    @Override public String toString() {
        return "Resultado{" + status + ", p=" + plataforma + ", id=" + correlationId + (valor!=null?", valor="+valor:"") + (!erros.isEmpty()?", erros="+erros:"") + '}';
    }
}