package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoriaProduto.Categoria;

import javax.persistence.*;

@Entity
@Table(name = "seg_linha_categoria")
public class LinhaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinha;
    @Column(name = "codigo_linha", unique = true, length = 10)
    private String codigoLinha;
    @Column(name = "nome_linha", length = 50)
    private String nome_linha;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id", nullable = false)
    private Categoria categoria;

    public Long getId() {
        return idLinha;
    }

    public void setId(Long id) {
        this.idLinha = id;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public String getNome_linha() {
        return nome_linha;
    }

    public void setNome_linha(String nome_linha) {
        this.nome_linha = nome_linha;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "LinhaCategoria{" +
                "id=" + idLinha +
                ", codigoLinha=" + codigoLinha +
                ", nomeLinha='" + nome_linha + '\'' +
                ", categoria=" + categoria +
                '}';
    }
}
