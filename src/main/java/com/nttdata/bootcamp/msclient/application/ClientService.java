package com.nttdata.bootcamp.msclient.application;

import com.nttdata.bootcamp.msclient.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    public Flux<Client> findAll();

    public Mono<Client> findById(String idClient);

    public Mono<Client> save(Client client);

    public Mono<Client> update(Client client, String idClient);

    public Mono<Void> delete(String idClient);
    public Mono<Client> clientbydocumentNumber(String documentNumber);

}
