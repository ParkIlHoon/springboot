package org.hoon.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerListener implements ApplicationRunner
{
	@Value("${ilhoon.test}")
	private String test;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		System.out.println("[RunnerListener] ilhoon.test : " + test);
		System.out.println("[RunnerListener] foo : " + args.containsOption("foo"));
		System.out.println("[RunnerListener] bar : " + args.containsOption("bar"));
	}
}
