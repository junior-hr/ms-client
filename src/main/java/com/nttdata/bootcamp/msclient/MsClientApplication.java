package com.nttdata.bootcamp.msclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Class MsClientApplication Main.
 * Client microservice class MsClientApplication.
 */
@SpringBootApplication
@EnableEurekaClient
public class MsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClientApplication.class, args);
    }

}