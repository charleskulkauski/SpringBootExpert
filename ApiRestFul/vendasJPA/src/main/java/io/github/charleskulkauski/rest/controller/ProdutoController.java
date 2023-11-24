package io.github.charleskulkauski.rest.controller;

import io.github.charleskulkauski.domain.entity.Cliente;
import io.github.charleskulkauski.domain.entity.Produto;
import io.github.charleskulkauski.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private Produtos repository;


    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save (@RequestBody @Valid Produto produto){
        return repository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update (@PathVariable Integer id, @RequestBody @Valid Produto produto){
        repository
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    repository.save(produto);
                    return produto;
                }).orElseThrow(() ->
                    new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository
                .findById(id)
                //quando abrir aspas, exemplo: p -> {}
                //Significa que há necessidade de retorno

                //quando nao tem aspas, exemplo: (p -> nomeMetodo(parametro))
                //Não tem necessidade de retorno
                .map(p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping("/{id}")
    public Produto getById(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        //Filtro que faz um match de informações, ignorando caixa alta, contendo as palavras do matcher
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        //Filtro igual matcher
        Example example = Example.of(filtro, matcher);
        return repository.findAll();
    }

}
