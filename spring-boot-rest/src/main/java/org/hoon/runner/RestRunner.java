package org.hoon.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RestRunner implements ApplicationRunner
{
	@Autowired
	RestTemplateBuilder builder;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		RestTemplate template = builder.build();

		StopWatch watch = new StopWatch();
		watch.start();

		/**
		 * RestTemplate는 Blocking IO 기법으로 동기 방식의 API임.
		 * -> getForObject가 종료되기 전까지 다음 line 의 코드가 수행되지 않음
		 */
		String helloResult = template.getForObject("http://127.0.0.1:8080/hello", String.class);
		System.out.println(helloResult);

		String worldResult = template.getForObject("http://127.0.0.1:8080/world", String.class);
		System.out.println(worldResult);

		watch.stop();
		System.out.println(watch.prettyPrint());

		////////////////////////////////////////////////////////////////////////////////////////////

		WebClient webClient = webClientBuilder.build();
		StopWatch finalWatch = new StopWatch();
		finalWatch.start();

		Mono<String> helloMono = webClient.get().uri("http://127.0.0.1:8080/hello")
				.retrieve()
				.bodyToMono(String.class);

		helloMono.subscribe(s -> {
			System.out.println(s);

			if (finalWatch.isRunning())
			{
				finalWatch.stop();
			}
			System.out.println(finalWatch.prettyPrint());
			finalWatch.start();
		});

		Mono<String> worldMono = webClient.get().uri("http://127.0.0.1:8080/world")
				.retrieve()
				.bodyToMono(String.class);

		worldMono.subscribe(s -> {
			System.out.println(s);

			if (finalWatch.isRunning())
			{
				finalWatch.stop();
			}
			System.out.println(finalWatch.prettyPrint());
			finalWatch.start();
		});
	}
}
