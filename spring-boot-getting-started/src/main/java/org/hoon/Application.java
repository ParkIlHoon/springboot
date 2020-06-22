package org.hoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 애플리케이션의 최상위 패키지에 위치해야함.
 * We generally recommend that you locate your main application class in a root package above other classes.
 * The @SpringBootApplication annotation is often placed on your main class, and it implicitly defines a base “search package” for certain items.
 */
/*
@SpringBootApplication 은 아래 3개의 어노테이션을 합친것
@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
 */
@SpringBootApplication
public class Application
{
	/**
	 * 어플리케이션 구동 메서드
	 * @param args
	 */
	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	/**
	 * 아래 Bean은 ilhoon-spring-boot-starter 때문에 무시된다.
	 * ComponentScan 먼저 동작하기 때문에 덮어씌워짐
	 *
	 * @Configuration 에서 @ConditionalOnMissingBean 지정 시 반영됨
	 */
	/*
	@Bean
	public Holoman holoman ()
	{
		Holoman holoman = new Holoman();
		holoman.setName("빈 또 등록");
		holoman.setHowLong(100);

		return holoman;
	}
	 */
}
