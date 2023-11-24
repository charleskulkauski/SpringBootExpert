package io.github.charleskulkauski.rest.controller;

import io.github.charleskulkauski.domain.entity.Cliente;
import io.github.charleskulkauski.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private Clientes clientes;
    public ClienteController( Clientes clientes ) {
        this.clientes = clientes;
    }
    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Integer id){
       return clientes.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cliente save(@RequestBody Cliente cliente){
        return clientes.save(cliente);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientes.findById(id).map( cliente -> {
            clientes.delete(cliente);
        return cliente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Cliente cliente){
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
    public List<Cliente> find(Cliente filtro){
        //Filtro que faz um match de informações, ignorando caixa alta, contendo as palavras do matcher
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        //Filtro igual matcher
        Example example = Example.of(filtro, matcher);
        return clientes.findAll();
    }
}
