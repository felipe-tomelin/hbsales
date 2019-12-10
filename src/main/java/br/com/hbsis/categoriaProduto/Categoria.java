package br.com.hbsis.categoriaProduto;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;

@Entity
@Table(name =  "seg_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_categoria", length = 50)
    private String nome_categoria;
    @Column(name = "codigo", unique = true, length = 10)
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_categoria() {
        return nome_categoria;
    }

    public void setNome_categoria(String nome_categoria) {
        this.nome_categoria = nome_categoria;
    }

    @Override
    public String toString(){
        return "Categoria{" +
                "id=" + id +
                ", nome_categoria'" + nome_categoria + '\'' +
                ", codigo'" + codigo + '\'' +
                ", fornecedor'" + fornecedor + '\'' +
                '}';
    }

}
