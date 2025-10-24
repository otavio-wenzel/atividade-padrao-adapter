package social.resposta;

public final class Erro {
    private final String codigo;
    private final String mensagem;
    
    
    public Erro(String codigo, String mensagem) {
    this.codigo = codigo; this.mensagem = mensagem;
    }
    
    
    public String getCodigo() { return codigo; }
    public String getMensagem() { return mensagem; }
    
    
    @Override public String toString() { return codigo + ": " + mensagem; }
}