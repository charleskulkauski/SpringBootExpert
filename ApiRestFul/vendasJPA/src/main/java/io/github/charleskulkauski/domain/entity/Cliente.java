package io.github.charleskulkauski.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    //Updatable = false; propriedade que não permite dar "update" na tabela
    @Column(name = "nome", length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio]")
    private String nome;

    //Obtendo os pedidos do cliente referenciados aqui, lá da classe clientes
    @JsonIgnore //Ignorar este atributo
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    @Column(name= "cpf", length= 11)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @Override
    public String toString() {
        return "CLIENTE " +
                "\n[ID]:" + id +
                "\n[NOME]: " + nome + "\n";
    }
}
