package org.hoon;

import org.hoon.mongodb.Account;
import org.hoon.mongodb.MongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class Application {
    @Autowired
    MongoTemplate template;

    @Autowired
    MongoDBRepository repository;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
//			Account account = new Account();
//			account.setName("일훈");
//			account.setEmail("chiwoo2074@gmail.com");
//
//			template.insert(account);

//			Account account = new Account();
//			account.setName("일훈2");
//			account.setEmail("chiwoo2074@gmail.com");
//
//			repository.save(account);
        };
    }
}
