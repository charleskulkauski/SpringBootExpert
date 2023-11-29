package io.github.charleskulkauski.localizacao.domain.repository;

import io.github.charleskulkauski.localizacao.domain.entity.Cidade;
import io.github.charleskulkauski.localizacao.domain.repository.projections.CidadeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepositoryQuerySQL extends JpaRepository<Cidade, Long> {

    @Query(nativeQuery = true, value = "select * from tb_cidade as c where c.nome =:nome")
    List<Cidade> findByNomeNativa(@Param("nome") String nome);

    //Nome das colunas, tem que bater com as properiedades da classe CidadeProjection
    @Query(nativeQuery = true, value = "select c.id_cidade as id, c.nome from tb_cidade as c where c.nome =:nome")
    List<CidadeProjection> findByNomeCidadeNativa(@Param("nome") String nome);
}
