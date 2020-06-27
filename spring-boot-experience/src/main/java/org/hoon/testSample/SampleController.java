package org.hoon.testSample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController
{
	@Autowired
	private SampleService sampleService;

	@GetMapping("/hello")
	public String hello ()
	{
		return "hello Nice to Meet you, " + sampleService.getName();
	}
}