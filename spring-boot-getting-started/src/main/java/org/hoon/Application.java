package org.hoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 애플리케이션의 최상위 패키지에 위치해야함.
 * We generally recommend that you locate your main application class in a root package above other classes.
 * The @SpringBootApplication annotation is often placed on your main class, and it implicitly defines a base “search package” for certain items.
 */
@SpringBootApplication
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
