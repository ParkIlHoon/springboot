package org.hoon.neo4j;

import org.hoon.neo4j.Account;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4JRunner implements ApplicationRunner
{
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Neo4JRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		Account account = new Account();
		account.setName("일훈3");
		account.setEmail("chiwoo2074@gmail.com");

		Role role = new Role();
		role.setName("USER");

		account.getRole().add(role);

//		Session session = sessionFactory.openSession();
//		session.save(account);
//		sessionFactory.close();

		repository.save(account);
	}
}
