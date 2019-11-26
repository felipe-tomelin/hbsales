package br.com.hbsis.categoriaProduto;

import javax.persistence.*;

@Entity
@Table(name =  "seg_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_categoria", unique = false, length = 70)
    private String nome_categoria;

    @Entity
    public class Item {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_order")
        private Categoria categoria;
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

    public Long getFornecedor_categoria() {
        return fornecedor_categoria;
    }

    public void setFornecedor_categoria(Long fornecedor_categoria) {
        this.fornecedor_categoria = fornecedor_categoria;
    }

    @Override
    public String toString(){
        return "Categoria{" +
                "id=" + id +
                ", nome_categoria'" + nome_categoria + '\'' +
                ", fornecedor_categoria" + fornecedor_categoria + '\'' +
                '}';
    }

}
