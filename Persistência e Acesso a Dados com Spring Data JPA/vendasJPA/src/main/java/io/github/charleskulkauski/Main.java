package io.github.charleskulkauski;

import io.github.charleskulkauski.domain.entity.Cliente;
import io.github.charleskulkauski.domain.entity.Pedido;
import io.github.charleskulkauski.domain.repository.Clientes;
import io.github.charleskulkauski.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication

public class Main {

    @Bean
    public CommandLineRunner init(
            @Autowired
            Clientes clientes,

            @Autowired
            Pedidos pedidos
    ){
        return args -> {
            Cliente charles = new Cliente("Charles");

            //Cadastro de clientes no banco
            System.out.println("Cadastrando clientes....");
            clientes.save(charles);
            //clientes.save(new Cliente("Outro charles"));

            //Criando pedido
            Pedido p = new Pedido();
            p.setCliente(charles);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));


            pedidos.save(p);


            pedidos.findByCliente(charles).forEach(System.out::println);



//            Procurar clientes e seus pedidos respectivamente
//            Cliente cliente = clientes.findClienteFetchPedidos(charles.getId());
//
//            System.out.println(cliente.toString());
//            System.out.println(cliente.getPedidos());

//            List<Cliente> todosClientes = clientes.findAll();
//            //For aprimorado para rodar toda a lista e printar
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("Buscando por find");
//            //Função para procurar pelo nome
//            List<Object> nome = clientes.findByNome("Charles");
//            System.out.println("Nome: " + nome + " encontrado");
//
//
//            System.out.println("Buscando por boolean");
//            //Função boolean para ver se o nome existe
////            boolean exists = clientes.existsByNome("dll");
//            List<Cliente> exists = clientes.encontrarPorNome("dll");
//            System.out.println("Nome existe? " + exists);





//
//            System.out.println("Atualizando clientes...");
//            //Atualizar charles
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
//                System.out.println("Nenhum charles encontrado!");
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