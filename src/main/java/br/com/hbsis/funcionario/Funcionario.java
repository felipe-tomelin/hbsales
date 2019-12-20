package br.com.hbsis.funcionario;

import javax.persistence.*;

@Entity
@Table(name = "seg_funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 50)
    String nome;
    @Column(name = "email", length = 50)
    String email;
    @Column(name = "uuid", length = 36)
    String uuid;

    public Funcionario() {
    }

    public Funcionario(String nome, String email, String uuid) {
        this.nome = nome;
        this.email = email;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
