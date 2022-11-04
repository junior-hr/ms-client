package com.nttdata.bootcamp.msclient;

import com.nttdata.bootcamp.msclient.application.ClientService;
import com.nttdata.bootcamp.msclient.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class MsClientApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClientService clientService;

    @Autowired
    private Mono<Client> client;
    @Autowired
    private Flux<Client> clientList;

    @BeforeEach
    void ini() {
        client = Mono.just(new Client("1", "Jose", "Meyer", "Personal", "DNI", "7654321", 987654321, "jmy@gmail.com", true, null));
        clientList = Flux.just(
                new Client("1", "Jose", "Meyer", "Personal", "DNI", "7654321", 987654321, "jmy@gmail.com", true, "VIP"),
                new Client("2", "Marcos", "Rios", "Business", "DNI", "1234567", 987654320, "mrrs@gmail.com", true, "VIP")
        );
    }

    @Test
    public void createClientTest() {
        when(client.flatMap(cl -> clientService.save(cl))).thenReturn(client);
        webTestClient.post().uri("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(client, Client.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void getAllTest() {
        when(clientService.findAll()).thenReturn(clientList);
        var responseFlux = webTestClient.get().uri("/api/clients")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Client.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectSubscription()
                .expectNext(new Client("1", "Jose", "Meyer", "Personal", "DNI", "7654321", 987654321, "jmy@gmail.com", true, "VIP"))
                .expectNext(new Client("2", "Marcos", "Rios", "Business", "DNI", "1234567", 987654320, "mrrs@gmail.com", true, "VIP"))
                .verifyComplete();
    }

    @Test
    void clientByDocumentNumberTest() {
        when(clientService.clientByDocumentNumber("7654321")).thenReturn(client);
        Mono<Client> obj = clientService.clientByDocumentNumber("7654321");
        assertEquals(client, obj);
        client.subscribe(x -> assertEquals("Jose", x.getNames()));
        client.subscribe(x -> assertEquals("Rios", x.getSurnames()));
        client.subscribe(x -> assertEquals("Business", x.getClientType()));
        client.subscribe(x -> assertEquals("DNI", x.getDocumentType()));
        client.subscribe(x -> assertEquals("7654321", x.getDocumentNumber()));
        client.subscribe(x -> assertEquals(987654321, x.getCellphone()));
        client.subscribe(x -> assertEquals("jmy@gmail.com", x.getEmail()));
        client.subscribe(x -> assertEquals(true, x.getState()));
        client.subscribe(x -> assertEquals("VIP", x.getProfile()));
    }

}