package com.nttdata.bootcamp.msclient.application;

import com.nttdata.bootcamp.msclient.exception.ResourceNotFoundException;
import com.nttdata.bootcamp.msclient.infrastructure.ClientRepository;
import com.nttdata.bootcamp.msclient.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Mono<Client> findById(String idClient) {
        return Mono.just(idClient)
                .flatMap(clientRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente", "Id", idClient)));
    }

    @Override
    public Mono<Client> save(Client client) {
        return client.validateClientType(client.getClientType())
                .flatMap(c -> {
                    if (c.equals(true)) {
                        return clientRepository.save(client);
                    } else {
                        return Mono.error(new ResourceNotFoundException("Tipo Cliente", "ClientType", client.getClientType()));
                    }
                });
    }

    @Override
    public Mono<Client> update(Client client, String idClient) {
        return client.validateClientType(client.getClientType())
                .flatMap(ct -> {
                    if (ct.equals(true)) {
                        return clientRepository.findById(idClient)
                                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente", "Id", idClient)))
                                .flatMap(c -> {
                                    c.setNames(client.getNames());
                                    c.setSurnames(client.getSurnames());
                                    c.setClientType(client.getClientType());
                                    c.setDocumentType(client.getDocumentType());
                                    c.setDocumentNumber(client.getDocumentNumber());
                                    c.setCellphone(client.getCellphone());
                                    c.setEmail(client.getEmail());
                                    c.setState(client.getState());
                                    c.setProfile(client.getProfile());
                                    return clientRepository.save(c);
                                });
                    } else {
                        return Mono.error(new ResourceNotFoundException("Tipo Cliente", "ClientType", client.getClientType()));
                    }
                });

    }

    @Override
    public Mono<Void> delete(String idClient) {
        return clientRepository.findById(idClient)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente", "Id", idClient)))
                .flatMap(clientRepository::delete);
    }

    @Override
    public Mono<Client> clientbydocumentNumber(String documentNumber) {
        return Mono.just(documentNumber)
                .flatMap(clientRepository::findByDocumentNumber)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente", "documentNumber", documentNumber)));
    }


    @Override
    public Mono<Client> updateProfileByDocumentNumber(String documentNumber, String profile) {
        return Mono.just(documentNumber)
                .flatMap(clientRepository::findByDocumentNumber)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Cliente", "documentNumber", documentNumber)))
                .flatMap(c -> {
                    log.info("profile-------: " + profile);
                    c.setProfile(profile.equals("0") ? null : profile);
                    return Mono.just(c);
                })
                .flatMap(c -> c.validateClientProfile().then(Mono.just(c)))
                .flatMap(c ->  update(c, c.getIdClient()));
    }

}
