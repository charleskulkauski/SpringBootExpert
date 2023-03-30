package io.github.charleskulkauski;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    @Value("${application.name}")
    private String nome;

    @GetMapping("/hello")
    public String helloWorld(){
        return nome;
    }



    //________________________________________________________
    // Testando animal

    @Autowired

    @Cachorro
    private Animal animal;

    @Bean(name= "algum")
    public CommandLineRunner executar(){
        return args ->
                this.animal.fazerBarulho();
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }
}