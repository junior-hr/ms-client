package com.nttdata.bootcamp.msclient.infrastructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.msclient.model.Client;
import reactor.core.publisher.Mono;

/**
 * Class ClientRepository.
 * Client microservice class ClientRepository.
 */
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

    public Mono<Client> findByDocumentNumber(String documentNumber);

}
