package io.github.charleskulkauski.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//A presença do table só é necessária quando a classe não é do mesmo nome da sua tabela
//@Table(name="cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Mapear para direcionar na coluna caso tenha nome diferente
    @Column(name = "id")
    private Integer id;

    //Nome da coluna tem que ser igual ao da váriavel
    //Updatable = false ; propriedade que não permite dar update na tabela
    @Column(name = "nome", length = 100)
    private String nome;

    //Obtendo os pedidos do cliente que foram referenciados aqui, lá da classe clientes
    @JsonIgnore //Ignorar este atributo
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    @Column(name= "cpf", length= 11)
    private String cpf;

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "CLIENTE " +
                "\n[ID]:" + id +
                "\n[NOME]: " + nome + "\n";
    }
}
