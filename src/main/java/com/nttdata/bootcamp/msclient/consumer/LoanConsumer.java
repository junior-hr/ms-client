package com.nttdata.bootcamp.msclient.consumer;

import com.nttdata.bootcamp.msclient.infrastructure.LoanRepository;
import com.nttdata.bootcamp.msclient.model.Loan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanConsumer {

    @Autowired
    private LoanRepository loanRepository;

    @KafkaListener(topics = "${spring.kafka.topic.loan.name}")
    public void listener(@Payload Loan loan) {
        log.debug("Message received : {} ", loan);
        applyListLoans(loan).blockFirst();
    }

    private Flux<Loan> applyListLoans(Loan request) {
        log.debug("applyListLoans executed : {} ", request);
        return loanRepository.findLoanByDocumentNumber(
                request.getClient().getDocumentNumber());
    }
}
