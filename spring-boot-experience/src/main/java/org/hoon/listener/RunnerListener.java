package org.hoon.listener;

import org.hoon.properties.IhoonProp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerListener implements ApplicationRunner
{
	private Logger logger = LoggerFactory.getLogger(RunnerListener.class);

	@Value("${ilhoon.test}")
	private String test;

	@Autowired
	IhoonProp prop;

	@Autowired
	private String hello;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		logger.debug("[RunnerListener] ilhoonProp.name : " + prop.getName());
		logger.debug("[RunnerListener] ilhoonProp.sessionTimeout : " + prop.getSessionTimeout());
		logger.debug("[RunnerListener] foo : " + args.containsOption("foo"));
		logger.debug("[RunnerListener] bar : " + args.containsOption("bar"));
		logger.debug("[RunnerListener] hello : " + hello);
	}
}
