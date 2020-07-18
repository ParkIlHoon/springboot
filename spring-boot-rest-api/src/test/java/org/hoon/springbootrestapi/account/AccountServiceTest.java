package org.hoon.springbootrestapi.account;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest
{
	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void findByUsername()
	{
		String userName = "testUser";
		String password = "1234";

		Account account = Account.builder()
									.email(userName)
									.password(password)
									.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
								.build();

		//accountRepository.save(account);
		this.accountService.saveAccount(account);

		UserDetailsService userDetailsService = (UserDetailsService) this.accountService;
		UserDetails userDetails = this.accountService.loadUserByUsername(userName);

		assertThat(this.passwordEncoder.matches(password, userDetails.getPassword()));
	}

	@Test
	public void fingByUsername_fail()
	{
		String userName = "testUser1234";

		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(userName));

		UserDetailsService userDetailsService = (UserDetailsService) accountService;
		UserDetails userDetails = accountService.loadUserByUsername(userName);
	}
}
