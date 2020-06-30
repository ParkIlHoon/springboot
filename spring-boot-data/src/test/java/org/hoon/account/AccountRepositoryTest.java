package org.hoon.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest
{
	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void di ()
	{
		Account account = new Account();
		account.setUsername("테스트 사용자");
		account.setPassword("123");

		Account newAccount = accountRepository.save(account);

		assertThat(newAccount).isNotNull();

		Account findAccount = accountRepository.findByUsername(newAccount.getUsername());

		assertThat(findAccount).isEqualTo(newAccount);

//		Optional<Account> optionalAccount = accountRepository.findByUsernameOptional("test");
//
//		assertThat(optionalAccount).isNotNull();
	}

}
