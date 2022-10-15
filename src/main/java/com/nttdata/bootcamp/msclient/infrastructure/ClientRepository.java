package com.nttdata.bootcamp.msclient.infrastructure;

import com.nttdata.bootcamp.msclient.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    public Mono<Client> findByDocumentNumber(String documentNumber);

}
