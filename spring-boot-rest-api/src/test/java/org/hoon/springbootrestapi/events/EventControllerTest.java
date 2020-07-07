package org.hoon.springbootrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest
{
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	EventRepository repository;

	@Test
	public void createEvent() throws Exception
	{
		Event event = Event.builder()
								.name("Spring")
								.description("테스트")
								.beginEnrollmentDateTime(LocalDateTime.of(2020,7,7,19,30))
								.closeEnrollmentDateTime(LocalDateTime.of(2020,7,8,19,30))
								.beginEventDateTime(LocalDateTime.of(2020,8,6,19,30))
								.endEventDateTime(LocalDateTime.of(2020,8,7,19,30))
								.basePrice(100)
								.maxPrice(200)
								.limitOfEnrollment(100)
								.location("어딘지 몰라요")
							.build();
		event.setId(10);
		Mockito.when(repository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaTypes.HAL_JSON_VALUE)
					.content(mapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
	}

}
