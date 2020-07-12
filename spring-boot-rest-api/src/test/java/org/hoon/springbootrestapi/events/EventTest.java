package org.hoon.springbootrestapi.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
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

	@Test
//	@Parameters({
//			"0, 0, true",
//			"100, 0, false",
//			"0, 100, false"
//	})
	@Parameters(method = "paramsForFreeTest")
	public void freeTest(int basePrice, int maxPrice, boolean isFree)
	{
		Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();

		event.update();

		assertThat(event.isFree()).isEqualTo(isFree);
	}

	private Object[] paramsForFreeTest()
	{
		return new Object[] {
			new Object[] {0, 0, true},
			new Object[] {100, 0, false},
			new Object[] {0, 100, false}
		};
	}

	@Test
	public void offlineTest()
	{
		Event event = Event.builder()
				.location("테스트")
				.build();

		event.update();

		assertThat(event.isOffline()).isTrue();
	}
}