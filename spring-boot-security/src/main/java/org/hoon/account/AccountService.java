package org.hoon.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService
{
	@Autowired
	private AccountRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 사용자 생성 메서드
	 * @param username 생성할 사용자의 username
	 * @param password 생성할 사용자의 password
	 * @return 생성된 사용자 객체
	 */
	public Account createAccount (String username, String password)
	{
		Account newAccount = new Account();
		newAccount.setUsername(username);
		newAccount.setPassword(passwordEncoder.encode(password));

		return repository.save(newAccount);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Optional<Account> account = repository.findByUsername(username);

		Account user = account.orElseThrow(() -> new UsernameNotFoundException(username));

		return new User(user.getUsername(), user.getPassword(), authorities());
	}

	private Collection<? extends GrantedAuthority> authorities()
	{
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
}

