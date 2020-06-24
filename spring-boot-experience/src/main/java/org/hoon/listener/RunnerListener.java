package org.hoon.listener;

import org.hoon.properties.IhoonProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerListener implements ApplicationRunner
{
	@Value("${ilhoon.test}")
	private String test;

	@Autowired
	IhoonProp prop;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		System.out.println("[RunnerListener] ilhoonProp.name : " + prop.getName());
		System.out.println("[RunnerListener] ilhoonProp.sessionTimeout : " + prop.getSessionTimeout());
		System.out.println("[RunnerListener] foo : " + args.containsOption("foo"));
		System.out.println("[RunnerListener] bar : " + args.containsOption("bar"));
	}
}
