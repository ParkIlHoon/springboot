package org.hoon.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoDBRepositoryTest {
    @Autowired
    MongoDBRepository repository;

    @Test
    public void findByEmail() {
        Account account = new Account();
        account.setName("일훈");
        account.setEmail("chiwoo2074@gmail.com");

        repository.save(account);

        Optional<Account> byId = repository.findById(account.getId());
        assertThat(byId).isNotNull();

        Optional<Account> byEmail = repository.findByEmail(account.getEmail());
        assertThat(byEmail).isNotNull();

        assertThat(byId).isEqualTo(byEmail);
    }
}
