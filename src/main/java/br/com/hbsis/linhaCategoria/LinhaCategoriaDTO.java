package br.com.hbsis.linhaCategoria;

public class LinhaCategoriaDTO {
    private Long idLinha;
    private Long id_categoria;
    private String nome_linha;
    private String codigoLinha;

    public LinhaCategoriaDTO(){
    }

    public LinhaCategoriaDTO(Long id, Long id_categoria, String nome_linha, String codigoLinha) {
        this.idLinha = id;
        this.id_categoria = id_categoria;
        this.nome_linha = nome_linha;
        this.codigoLinha = codigoLinha;
    }

    public static LinhaCategoriaDTO of(LinhaCategoria linhaCategoria){
        return new LinhaCategoriaDTO(
                linhaCategoria.getId(),
                linhaCategoria.getCategoria().getId(),
                linhaCategoria.getNome_linha(),
                linhaCategoria.getCodigoLinha()
        );
    }

    public Long getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Long idLinha) {
        this.idLinha = idLinha;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNome_linha() {
        return nome_linha;
    }

    public void setNome_linha(String nome_linha) {
        this.nome_linha = nome_linha;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    @Override
    public String toString() {
        return "LinhaCategoriaDTO{" +
                "idLinha=" + idLinha +
                ", id_categoria=" + id_categoria +
                ", nome_linha='" + nome_linha + '\'' +
                ", codigo_linha=" + codigoLinha +
                '}';
    }
}

