package org.hoon.springbootrestapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest
{
	@Test
	public void builder()
	{
		Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API Development")
				.build();
		assertThat(event).isNotNull();
	}

	@Test
	public void javaBean()
	{
		// Given
		String name = "TEST";
		String description = "설명입니다.";

		// When
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);

		// Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}
}