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
            cliente.setNome("Charles");
            clientes.salvar(cliente);

            //Exibindo os clientes com as informações do banco
            clientes.salvar(new Cliente("Outro cliente"));

            List<Cliente> todosClientes = clientes.obterTodos();
            //For aprimorado para rodar toda a lista e printar
            todosClientes.forEach(System.out::println);


            //Atualizar cliente
            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clientes.atualizar(c);
            });

            //Buscar por nome
            clientes.buscarPorNome("Cli").forEach(System.out::println);

//            //Deletar
//            clientes.obterTodos().forEach(c -> {
//                clientes.deletar(c);
//            });



            todosClientes = clientes.obterTodos();
            if (todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado!");

            }else{
                todosClientes.forEach(System.out::println);
            }



        };



    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}