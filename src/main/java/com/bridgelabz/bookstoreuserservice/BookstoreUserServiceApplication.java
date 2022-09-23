package com.bridgelabz.bookstoreuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookstoreUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreUserServiceApplication.class, args);
    }

}
