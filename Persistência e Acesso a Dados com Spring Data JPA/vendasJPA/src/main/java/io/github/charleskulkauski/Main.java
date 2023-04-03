package io.github.charleskulkauski;

import io.github.charleskulkauski.domain.entity.Cliente;
import io.github.charleskulkauski.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication

public class Main {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args -> {
            Cliente cliente = new Cliente();
            //Cadastro de clientes no banco
            System.out.println("Cadastrando clientes....");
            clientes.save(new Cliente("Charles"));
            clientes.save(new Cliente("Outro cliente"));

            List<Cliente> todosClientes = clientes.findAll();
            //For aprimorado para rodar toda a lista e printar
            todosClientes.forEach(System.out::println);

            System.out.println("Buscando por find");
            //Função para procurar pelo nome
            List<Object> nome = clientes.findByNome("Charles");
            System.out.println("Nome: " + nome + " encontrado");


            System.out.println("Buscando por boolean");
            //Função boolean para ver se o nome existe
//            boolean exists = clientes.existsByNome("dll");
            List<Cliente> exists = clientes.encontrarPorNome("dll");
            System.out.println("Nome existe? " + exists);



//
//            System.out.println("Atualizando clientes...");
//            //Atualizar cliente
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome() + " atualizado");
//                clientes.save(c);
//            });
//
//            //Buscar por nome
//            clientes.findByNomeLike("Cli").forEach(System.out::println);
////
////            //Deletar
////            clientes.findAll().forEach(c -> {
////                clientes.delete(c);
////            });
//
//
//
//            todosClientes = clientes.findAll();
//            if (todosClientes.isEmpty()){
//                System.out.println("Nenhum cliente encontrado!");
//
//            }else{
//                todosClientes.forEach(System.out::println);
//            }



        };



    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}