package io.github.charleskulkauski.domain.repository;


import io.github.charleskulkauski.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


//JPA Busca procura cliente dentro do banco e utiliza integer para pegar id
public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Convenção findBy + nome da propriedade no banco
    //Select c from Cliente c where c.nome like :nome
    //Exemplos
    List<Object> findByNome(String nome);

    //Existe pelo nome
    Boolean existsByNome(String nome);

    //Utilizando annotation Query
    @Query(value = " select c from Cliente c where c.nome like :nome")
    //@Param serve pra linkar o ":nome" da query com o parâmetro passado direto no método
    List<Cliente> encontrarPorNome(@Param("nome") String nome);


    //Query sem a necessidade de um retorno, caso você queria modificar esse método que já existe através do @Query
    //É preciso colocar a annotation @Modifying
    //@Query
    //@Modifying
    //void deleteByNome(String nome);



    //Carregando clientes com seus pedidos
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);



}
