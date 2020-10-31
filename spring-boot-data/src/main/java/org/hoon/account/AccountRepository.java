package org.hoon.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

//	Optional<Account> findByUsernameOptional(String username);
}
