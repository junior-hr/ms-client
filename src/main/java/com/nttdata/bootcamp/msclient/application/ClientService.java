package com.nttdata.bootcamp.msclient.application;

import com.nttdata.bootcamp.msclient.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    public Flux<Client> findAll();

    public Mono<Client> findById(String idClient);

    public Mono<Client> save(Client client);

    public Mono<Void> delete(Client client);
}
