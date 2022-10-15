package com.nttdata.bootcamp.msclient.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nttdata.bootcamp.msclient.application.ClientService;
import com.nttdata.bootcamp.msclient.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> listClients() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> viewClientDetails(@PathVariable("id") String idClient) {
        return service.findById(idClient).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
    }
    @GetMapping("/documentNumber/{documentNumber}")
    public Mono<ResponseEntity<Client>> clientbydocumentNumber(@PathVariable("documentNumber") String documentNumber){
        return service.clientbydocumentNumber(documentNumber).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveClient(@Valid @RequestBody Mono<Client> monoClient) {
        Map<String, Object> request = new HashMap<>();
        return monoClient.flatMap(client -> {
            return service.save(client).map(c -> {
                request.put("Cliente", c);
                request.put("mensaje", "Cliente guardado con exito");
                request.put("timestamp", new Date());
                return ResponseEntity.created(URI.create("/api/clients/".concat(c.getIdClient())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(request);
            });
        });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> editClient(@Valid @RequestBody Client client, @PathVariable("id") String idClient) {
        return service.update(client,idClient)
                .map(c -> ResponseEntity.created(URI.create("/api/clients/".concat(idClient)))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable("id") String idClient) {
        return service.delete(idClient).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }
}
