package com.nttdata.bootcamp.msclient.dto;

import com.nttdata.bootcamp.msclient.model.Credit;
import com.nttdata.bootcamp.msclient.model.Loan;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Builder
public class SummaryProductsDto {

    private String documentNumber;
    private List<Loan> loan;
    private List<Credit> creditCard;

    public Mono<SummaryProductsDto> mapperToSummaryProductsDtoOfCredit(List<Credit> listCredit, List<Loan> listLoan, String documentNumber) {
        log.info("Inicio mapperToSummaryProductsDtoOfCredit-------: ");
        SummaryProductsDto sumProductDto = SummaryProductsDto.builder()
                .documentNumber(documentNumber)
                .creditCard(listCredit)
                .loan(listLoan)
                .build();
        log.info("Fin mapperToSummaryProductsDtoOfCredit-------: ");
        return Mono.just(sumProductDto);
    }

}
