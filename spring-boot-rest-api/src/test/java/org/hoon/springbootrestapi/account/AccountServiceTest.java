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

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void findByUsername()
	{
		String userName = "1hoon";
		String password = "1234";

		Account account = Account.builder()
									.email(userName)
									.password(password)
									.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
								.build();

		accountRepository.save(account);

		UserDetailsService userDetailsService = (UserDetailsService) accountService;
		UserDetails userDetails = accountService.loadUserByUsername(userName);

		assertThat(userDetails.getPassword()).isEqualTo(password);
	}

	@Test
	public void fingByUsername_fail()
	{
		String userName = "1hoon";

		expectedException.expect(UsernameNotFoundException.class);
		expectedException.expectMessage(Matchers.containsString(userName));

		UserDetailsService userDetailsService = (UserDetailsService) accountService;
		UserDetails userDetails = accountService.loadUserByUsername(userName);
	}
}
