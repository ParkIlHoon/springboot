package org.hoon.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDBRepository extends MongoRepository<Account, String> {
    Optional<Account> findByEmail(String email);
}
