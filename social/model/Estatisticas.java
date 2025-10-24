package social.model;

public final class Estatisticas {
    private final long curtidas;
    private final long comentarios;
    private final long compartilhamentos;
    private final long visualizacoes;
    
    
    public Estatisticas(long curtidas, long comentarios, long compartilhamentos, long visualizacoes) {
    this.curtidas = curtidas; this.comentarios = comentarios; this.compartilhamentos = compartilhamentos; this.visualizacoes = visualizacoes;
    }
    
    
    public long getCurtidas() { return curtidas; }
    public long getComentarios() { return comentarios; }
    public long getCompartilhamentos() { return compartilhamentos; }
    public long getVisualizacoes() { return visualizacoes; }

    @Override
    public String toString() {
        return "Estatisticas{" +
                "curtidas=" + curtidas +
                ", comentarios=" + comentarios +
                ", compartilhamentos=" + compartilhamentos +
                ", visualizacoes=" + visualizacoes +
                '}';
    }
}