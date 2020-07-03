package org.hoon.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner
{
	@Autowired
	StringRedisTemplate redisTemplate;

	@Autowired
	MemberRepository repository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set("1hoon", "park");
		values.set("springboot", "redis");
		values.set("hello", "world");
		
		
		Member member = new Member();
		member.setName("일훈");
		member.setEmail("chiwoo2074@gmail.com");
		
		Member member2 = new Member();
		member2.setName("꼬북이");
		member2.setEmail("chiwoo2074@gmail.com");

		repository.save(member);
		repository.save(member2);

		Optional<Member> byId = repository.findById(member.getId());
		System.out.println(byId.get().getName());
		System.out.println(byId.get().getEmail());
	}
}
