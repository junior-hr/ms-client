package com.nttdata.bootcamp.msclient.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Document(collection = "Client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String idClient;

    @NotEmpty(message = "no debe estar vacío")
    private String names;

    @NotEmpty(message = "no debe estar vacío")
    private String surnames;

    @NotEmpty(message = "no debe estar vacío")
    private String clientType;

    @NotEmpty(message = "no debe estar vacío")
    private String documentType;

    @NotEmpty(message = "no debe estar vacío")
    private String documentNumber;

    @Max(value = 999999999, message = "no debe tener más de 9 cifras")
    private Integer cellphone;

    @Email(message = "debe tener formato de correo")
    private String email;

    private Boolean state;

}
