package io.github.charleskulkauski.service;

import io.github.charleskulkauski.domain.entity.Pedido;
import io.github.charleskulkauski.domain.enums.StatusPedido;
import io.github.charleskulkauski.rest.dto.PedidoDTO;

import java.util.Optional;


public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
