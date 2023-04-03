package io.github.charleskulkauski.domain.repository;

import io.github.charleskulkauski.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
}
