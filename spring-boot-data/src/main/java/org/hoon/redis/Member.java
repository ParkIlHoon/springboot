package org.hoon.redis;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("member")
public class Member
{
	@Id
	private String id;

	private String name;

	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
