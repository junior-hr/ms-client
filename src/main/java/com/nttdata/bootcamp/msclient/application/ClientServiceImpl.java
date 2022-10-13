package com.nttdata.bootcamp.msclient.application;

import com.nttdata.bootcamp.msclient.infrastructure.ClientRepository;
import com.nttdata.bootcamp.msclient.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> findById(String IdClient) {
        return clientRepository.findById(IdClient);
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Void> delete(Client client) {
        return clientRepository.delete(client);
    }

}
