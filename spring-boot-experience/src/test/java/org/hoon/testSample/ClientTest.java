package org.hoon.testSample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientTest
{
	@Autowired
	WebTestClient webTestClient;

	@Test
	public void hello ()
	{
		webTestClient.get().uri("/hello").exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("hello박일훈");
	}
}
