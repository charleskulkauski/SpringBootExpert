package io.github.charleskulkauski.localizacao.domain.repository;

import io.github.charleskulkauski.localizacao.domain.entity.Cidade;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {

    //Busca pelo nome
    List<Cidade>findByNome(String nome);

    List<Cidade>findByNomeStartingWith(String nome);

    List<Cidade>findByNomeEndingWith(String nome);

    List<Cidade>findByNomeContaining(String nome);

    List<Cidade> findByHabitantes (Long total);

    List<Cidade> findByHabitantesLessThan (Long habitantes);

    @Query("select c from Cidade c where upper(c.nome) like upper(?1)")
    List<Cidade> findByNomeLike(String nome, Pageable pageable);

    //Query examples
    List<Cidade> findByHabitantesLessThanAndNomeLike(Long habitantes, String nome)
;


}
