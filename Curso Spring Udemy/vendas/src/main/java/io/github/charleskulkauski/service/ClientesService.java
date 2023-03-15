package io.github.charleskulkauski.service;

import ch.qos.logback.core.net.server.Client;
import io.github.charleskulkauski.model.Cliente;
import io.github.charleskulkauski.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {


    private ClientesRepository repository;

    @Autowired //Pode criar construtor do repository automaticamente, sem a necessidade de escreve-lo
    //Aqui nesse caso foi só para deixar claro o que é injeção de dependência

    public ClientesService(ClientesRepository repository){
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);

        this.repository.persistir(cliente);

    }

    public void validarCliente(Object cliente){
        //aplica validações
    }
}
