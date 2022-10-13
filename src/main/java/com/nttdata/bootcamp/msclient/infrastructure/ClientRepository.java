package com.nttdata.bootcamp.msclient.infrastructure;

import com.nttdata.bootcamp.msclient.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {


}
