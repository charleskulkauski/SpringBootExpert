package io.github.charleskulkauski.rest.controller;

import io.github.charleskulkauski.domain.entity.ItemPedido;
import io.github.charleskulkauski.domain.entity.Pedido;
import io.github.charleskulkauski.domain.enums.StatusPedido;
import io.github.charleskulkauski.domain.repository.Pedidos;
import io.github.charleskulkauski.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.charleskulkauski.rest.dto.InformacaoItemPedidoDTO;
import io.github.charleskulkauski.rest.dto.InformacoesPedidoDTO;
import io.github.charleskulkauski.rest.dto.PedidoDTO;
import io.github.charleskulkauski.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private Pedidos repository;

    @GetMapping
    public List<Pedido> findAll(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado para o ID: " + id));
    }

    //@PatchMapping: atualiza apenas um dado do objeto
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        //String -> Enum: Para transformar, é só chamar a classe enum e colocar .valueOf(String)
        //ValueOf vai indicar se a string comparada (novoStatus) contém alguma letra igual a do status
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }


    private InformacoesPedidoDTO converter (Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                //DateTimeFormatter.ofPattern: Passa format de data em string no formato desejado
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens
                .stream()
                .map(
                        item -> InformacaoItemPedidoDTO
                                .builder()
                                .descricaoPoroduto(item.getProduto().getDescricao())
                                .precoUnitario(item.getProduto().getPreco())
                                .quantidade(item.getQuantidade())
                                .build()
                ).collect(Collectors.toList());
    }
}
