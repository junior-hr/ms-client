package com.nttdata.bootcamp.msclient.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String idClient;
    private String names;
    private String surnames;
    private String clientType;
    private String documentType;
    private Integer cellphone;
    private String email;
    private Boolean state;

}
