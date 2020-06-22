package org.hoon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * holmanRunner
 * 
 * Application 구동 시 holman Bean을 toString해 콘솔에 찍는다.
 */
@Component
public class HolomanRunner implements ApplicationRunner
{
	@Autowired
	Holoman holoman;

	/**
	 * holman Bean을 toString해 콘솔에 찍는다.
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		System.out.println(holoman.toString());
	}
}
