package org.hoon.springbootrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hoon.springbootrestapi.common.RestDocConfig;
import org.hoon.springbootrestapi.common.TestDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfig.class)
public class EventControllerTest
{
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

//	@MockBean
//	EventRepository repository;

	@Test
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception
	{
		EventDto event = EventDto.builder()
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
//								.id(100)
//								.free(true)
//								.eventStatus(EventStatus.PUBLISHED)
							.build();
		//event.setId(10);
		//Mockito.when(repository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaTypes.HAL_JSON_VALUE)
					.content(mapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("id").value(Matchers.not(100)))
				.andExpect(jsonPath("free").value(Matchers.not(true)))
				.andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.toString()))
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.query-events").exists())
				.andExpect(jsonPath("_links.update-event").exists())
				.andDo(document(
						"create-event",
						links(
								halLinks(),
								linkWithRel("self").description("Link to Self"),
								linkWithRel("query-events").description("Link to query-events"),
								linkWithRel("update-event").description("Link to update-event")
						),
						requestHeaders(
								headerWithName(HttpHeaders.ACCEPT).description("Header Accept Type"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("Header Content Type")
						),
						requestFields(
								fieldWithPath("name").description("Name of new Event"),
								fieldWithPath("description").description("description of new Event"),
								fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new Event"),
								fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new Event"),
								fieldWithPath("beginEventDateTime").description("beginEventDateTime of new Event"),
								fieldWithPath("endEventDateTime").description("endEventDateTime of new Event"),
								fieldWithPath("location").description("location of new Event"),
								fieldWithPath("basePrice").description("basePrice of new Event"),
								fieldWithPath("maxPrice").description("maxPrice of new Event"),
								fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new Event")
						),
						responseHeaders(
								headerWithName(HttpHeaders.LOCATION).description("Location of Response Header"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type of Response Header")
						),
						responseFields(
								fieldWithPath("id").description("id of new Event"),
								fieldWithPath("name").description("Name of new Event"),
								fieldWithPath("description").description("description of new Event"),
								fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new Event"),
								fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new Event"),
								fieldWithPath("beginEventDateTime").description("beginEventDateTime of new Event"),
								fieldWithPath("endEventDateTime").description("endEventDateTime of new Event"),
								fieldWithPath("location").description("location of new Event"),
								fieldWithPath("basePrice").description("basePrice of new Event"),
								fieldWithPath("maxPrice").description("maxPrice of new Event"),
								fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new Event"),
								fieldWithPath("free").description("free of new Event"),
								fieldWithPath("offline").description("offline of new Event"),
								fieldWithPath("eventStatus").description("status of new Event"),
								fieldWithPath("_links.self.href").description("self link of new Event"),
								fieldWithPath("_links.query-events.href").description("query-event link of new Event"),
								fieldWithPath("_links.update-event.href").description("update-event link of new Event")
						)
				));
	}

	@Test
	@TestDescription("입력 받을 수 없는 값이 입력됬을 때 BadRequest 응답 테스트")
	public void createEvent_badRequest() throws Exception
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
								.id(100)
								.free(true)
								.eventStatus(EventStatus.PUBLISHED)
							.build();

		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON_VALUE)
				.content(mapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("입력 값이 비어있을 때 BadRequest 응답 테스트")
	public void createEvent_badRequest_empty_input() throws Exception
	{
		EventDto eventDto = EventDto.builder().build();

		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("입력 값이 잘못됬을 때 BadRequest 응답 테스트")
	public void createEvent_badRequest_wrong_input() throws Exception
	{
		EventDto eventDto = EventDto.builder()
										.name("Spring")
										.description("테스트")
										.beginEnrollmentDateTime(LocalDateTime.of(2020,7,7,19,30))
										.closeEnrollmentDateTime(LocalDateTime.of(2020,7,8,19,30))
										.beginEventDateTime(LocalDateTime.of(2020,8,6,19,30))
										.endEventDateTime(LocalDateTime.of(2020,8,7,19,30))
										.basePrice(1000)
										.maxPrice(100)
										.limitOfEnrollment(100)
										.location("어딘지 몰라요")
									.build();

		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("$[0].objectName").exists())
//				.andExpect(jsonPath("$[0].field").exists())
				.andExpect(jsonPath("$[0].defaultMessage").exists())
//				.andExpect(jsonPath("$[0].rejectedValue").exists())
				.andExpect(jsonPath("$[0].code").exists());
	}
}
