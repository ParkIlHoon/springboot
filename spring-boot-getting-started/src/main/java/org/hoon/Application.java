package org.hoon;

/*import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
@RestController
public class Application
{
	/**
	 * 어플리케이션 구동 메서드
	 * @param args
	 */
	public static void main(String[] args)
	{
		try {
			SpringApplication application = new SpringApplication(Application.class);
			// 아래 설정 지정 시, Web Application이 아닌 것으로 인식해 main 메서드 수행 후 종료됨.
			//application.setWebApplicationType(WebApplicationType.NONE);
			application.run(args);

		}
		catch (Exception e){
			e.printStackTrace();
		}


		//createTomcat();
	}

	/**
	 * 아래 Bean은 ilhoon-spring-boot-starter 때문에 무시된다.
	 * ComponentScan 먼저 동작하기 때문에 덮어씌워짐
	 *
	 * @Configuration 에서 @{@link org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean} 지정 시 반영됨
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

	/**
	 * SpringBoot 자동설정이 아니고 직접 톰캣을 생성해 사용한다.
	 * 아래 코드는 @EnableAutoConfiguration 의 @{@link org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration} 에 포함됨.
	 * @throws LifecycleException

	private static void createTomcat() throws LifecycleException
	{
		// 1. 톰캣 객체 생성
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		
		// 2. ContextPath 생성
		Context context = tomcat.addContext("/", "D:\\workspace_springboot\\spring-boot-getting-started");

		// 3. Servlet 객체 생성
		HttpServlet servlet = new HttpServlet()
		{
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
			{
				PrintWriter writer = resp.getWriter();
				writer.println("<html>hello world</html>");
			}
		};
		
		// 4. 톰캣에 Servlet 추가 및 Request 매핑
		String servletName = "HelloServlet";
		tomcat.addServlet("/", servletName, servlet);
		context.addServletMappingDecoded("/", servletName);

		// 5. 톰캣 구동
		tomcat.start();
		tomcat.getServer().await();
	}
	 */

	@GetMapping("/hello")
	public String hello ()
	{
		return "hello Spring";
	}

	/*
	@Bean
	public ServletWebServerFactory serverFactory ()
	{
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createStandardConnector());

		return tomcat;
	}
	*/

	/*
	 * HTTP Connector 생성 메서드
	 * @return 8080 포트의 HTTP Connector

	private Connector createStandardConnector()
	{
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setPort(8080);
		return connector;
	}
	*/
}
