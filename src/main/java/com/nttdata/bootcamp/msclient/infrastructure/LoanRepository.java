package com.nttdata.bootcamp.msclient.infrastructure;

import com.nttdata.bootcamp.msclient.config.WebClientConfig;
import com.nttdata.bootcamp.msclient.model.Credit;
import com.nttdata.bootcamp.msclient.model.Loan;
import com.nttdata.bootcamp.msclient.util.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class LoanRepository {

    @Value("${local.property.host.ms-loan}")
    private String propertyHostMsLoan;

    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    @CircuitBreaker(name = Constants.LOAN_CB, fallbackMethod = "getDefaultLoanByDocumentNumber")
    public Flux<Loan> findLoanByDocumentNumber(String documentNumber) {

        log.info("Inicio----findLoanByDocumentNumber-------: ");
        WebClientConfig webconfig = new WebClientConfig();
        Flux<Loan> alerts = webconfig.setUriData("http://" + propertyHostMsLoan + ":8092")
                .flatMap(d -> webconfig.getWebclient().get()
                        .uri("/api/loans/loansDetails/" + documentNumber).retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                        .bodyToFlux(Loan.class)
                        // .transform(it -> reactiveCircuitBreakerFactory.create("parameter-service").run(it, throwable -> Flux.just(new Loan())))
                        .collectList()
                )
                .flatMapMany(iterable -> Flux.fromIterable(iterable));
        return alerts;
    }

    public Flux<Credit> getDefaultLoanByDocumentNumber(String documentNumber, Exception e) {
        log.info("Inicio----getDefaultLoanByDocumentNumber-------documentNumber: " + documentNumber);
        return Flux.empty();
    }
}