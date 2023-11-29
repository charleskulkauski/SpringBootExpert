package io.github.charleskulkauski.localizacao.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "tb_cidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cidade {

    @Id
    @Column(name="id_cidade")
    private long id;

    @Column(name = "nome", length = 50, unique = true)
    private String nome;

    @Column(name = "qtd_habitantes", length = 50)
    private Long habitantes;
}
