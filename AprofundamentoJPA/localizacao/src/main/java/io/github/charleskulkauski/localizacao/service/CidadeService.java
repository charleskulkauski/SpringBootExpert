package io.github.charleskulkauski.localizacao.service;

import io.github.charleskulkauski.localizacao.domain.entity.Cidade;
import io.github.charleskulkauski.localizacao.domain.repository.CidadeRepository;
import io.github.charleskulkauski.localizacao.domain.repository.CidadeRepositoryQuerySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.github.charleskulkauski.localizacao.domain.repository.specs.CidadeSpecs.*;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepositoryQuerySQL repositorySQL;
    private CidadeRepository repository;
    public CidadeService(CidadeRepository repository) {
        this.repository = repository;
    }

    public void listarCidadesPorNomeSQL(){
        repositorySQL.findByNomeNativa("Rio de janeiro").forEach(System.out::println);
    }

    public void listarCidadesPorNomeEIdSQL(){
        repositorySQL.findByNomeNativa("Rio de janeiro")
                .stream()
                .map(cidadeProjection -> new Cidade(cidadeProjection.getId(), cidadeProjection.getNome(), null))
                .forEach(System.out::println);
    }

    public void listarQuerys(){
        repository.findByNome("Sao Paulo").forEach(System.out::println);

        System.out.println("Starting with");
        repository.findByNomeStartingWith("Sao").forEach(System.out::println);

        System.out.println("Ending with");
        repository.findByNomeEndingWith("zalo").forEach(System.out::println);

        System.out.println("Containing with");
        repository.findByNomeContaining("%orto%").forEach(System.out::println);

        System.out.println("Cidade por quantidade de habitante");
        repository.findByHabitantesLessThan(10001L);

        System.out.println("Filtro dinamico");
        repository.findByHabitantesLessThanAndNomeLike(null, "Porto").forEach(System.out::println);
    }

    public void listarPorNome(){
        Pageable pageable = PageRequest.of(0, 10);
        repository.findByNomeLike("Porto%", pageable)
                .forEach(System.out::println);
    }

    public List<Cidade> filtroDinamico(Cidade cidade){
        //Evita if's de case, por exemplo
        //Letras maiusculas e minúsculas
        //Letras que batem e etc, atuando como um filtro dinâmico
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
                .withIncludeNullValues();

        Example<Cidade> example = Example.of(cidade, matcher);


        return repository.findAll(example);
    }

    public void listarCidadesByNomeSpec(){

        //As specs nomeEqual e habitantesGreaterThan estao sendo diretamente importadas para serem utilizadas como métodos
        //Melhor forma de se utilizar as Spcs é deixando elas bem fluídas e específicas, como esta
        repository.findAll(nomeEqual("Sao Paulo").and(habitantesGreaterThan(100L))).forEach(System.out::println);
        //repository.findAll(nomeEqual("Sao Paulo").or(habitantesGreaterThan(100))).forEach(System.out::println);

        //Outras formas não muito utilizadas
        //repository.findAll(propertyEqual("nome", "Sao Paulo").and(propertyEqual("habitantes", 123456))).forEach(System.out::println);
        //repository.findAll(nomeEqual("Sao Paulo").and(habitantesGreaterThan(100))).forEach(System.out::println);
    }

    //Querys dinâmicas
    public void listarCidadesSpecsFiltroDinamico(Cidade filtro) {
        Specification<Cidade> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        //select * from cidade where 1=1
        if (Long.valueOf(filtro.getId()) != null) {
            specs = specs.and(idEqual(filtro.getId()));
        }

        //Verifica se existe algum texto na instância filtro
        //Se tiver algo, ele faz uma busca fluída com nomeLike
        if (StringUtils.hasText(filtro.getNome())) {
            specs = specs.and(nomeLike(filtro.getNome()));
        }

        if (filtro.getHabitantes() != null) {
            specs.and(habitantesGreaterThan(filtro.getHabitantes()));
        }

        //Procura com todas as regras impostas acima
        repository.findAll(specs).forEach(System.out::println);
    }

    public void listarCidadesporHabitantes(){
        repository.findByHabitantes(123456L).forEach(System.out::println);
    }
    public void listarCidades(){
        repository.findAll().forEach(System.out::println);
    }

}
