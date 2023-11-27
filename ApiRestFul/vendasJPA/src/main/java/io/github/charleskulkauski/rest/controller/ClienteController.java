package io.github.charleskulkauski.rest.controller;

import io.github.charleskulkauski.domain.entity.Cliente;
import io.github.charleskulkauski.domain.repository.Clientes;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
@Api("Api de Clientes")
public class ClienteController {
    private Clientes clientes;
    public ClienteController( Clientes clientes ) {
        this.clientes = clientes;
    }
    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
    })
    public Cliente getClienteById(@PathVariable @ApiParam("Id do cliente") Integer id){
       return clientes.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return clientes.save(cliente);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletar um cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")
    })
    public void delete(@PathVariable @ApiParam("Id do cliente") Integer id){
        clientes.findById(id).map( cliente -> {
            clientes.delete(cliente);
        return cliente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Cliente não encontrado")
    })
    public void update(@PathVariable @ApiParam("Id do cliente") Integer id, @RequestBody @Valid Cliente cliente){
        clientes
                .findById(id)
                .map( clienteExistente -> {
                                            cliente.setId(clienteExistente.getId());
                                            clientes.save(cliente);
                                            return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
        ;
    }

    @GetMapping
    @ApiOperation("Procurar cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado com sucesso"),
            @ApiResponse(code = 400, message = "Cliente não encontrado")
    })
    public List<Cliente> find(@RequestBody Cliente filtro){
        //Filtro que faz um match de informações, ignorando caixa alta, contendo as palavras do matcher
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        //Filtro igual matcher
        Example example = Example.of(filtro, matcher);
        return clientes.findAll();
    }
}
