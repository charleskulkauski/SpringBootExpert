package io.github.charleskulkauski.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.charleskulkauski.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {
    public Pedido(){}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //Referenciando os relacionamentos, muitos para um
    //Juntando na coluna cliente_id
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    //nome da coluna, total de digitos, quatas casas decimais
    // exemplo: 1000.00
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    @JsonManagedReference
    private List<ItemPedido> itens;

}
