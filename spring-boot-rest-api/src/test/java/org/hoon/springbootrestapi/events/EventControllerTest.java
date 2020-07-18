package org.hoon.springbootrestapi.events;

import org.hamcrest.Matchers;
import org.hoon.springbootrestapi.account.Account;
import org.hoon.springbootrestapi.account.AccountRepository;
import org.hoon.springbootrestapi.account.AccountRole;
import org.hoon.springbootrestapi.account.AccountService;
import org.hoon.springbootrestapi.common.AppProperties;
import org.hoon.springbootrestapi.common.BaseControllerTest;
import org.hoon.springbootrestapi.common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.hateoas.MediaTypes;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest
{
	@Autowired
	EventRepository eventRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AppProperties appProperties;

	@Before
	public void setUp()
	{
		this.eventRepository.deleteAll();
		this.accountRepository.deleteAll();
	}

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
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
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
								linkWithRel("update-event").description("Link to update-event"),
								linkWithRel("profile").description("Link to profile")
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
								fieldWithPath("manager.id").description("manager of new Event"),
								fieldWithPath("_links.self.href").description("self link of new Event"),
								fieldWithPath("_links.query-events.href").description("query-event link of new Event"),
								fieldWithPath("_links.update-event.href").description("update-event link of new Event"),
								fieldWithPath("_links.profile.href").description("profile link of new Event")
						)
				));
	}

	private String getAccessToken() throws Exception
	{
		createAccount();

		String clientId = this.appProperties.getClientId();
		String clientSecret = this.appProperties.getClientSecret();

		ResultActions perform = this.mockMvc.perform(post("/oauth/token")
													.with(httpBasic(clientId, clientSecret))
													.param("username", this.appProperties.getUserUserName())
													.param("password", this.appProperties.getUserPassword())
													.param("grant_type", "password"));

		MockHttpServletResponse response = perform.andReturn().getResponse();
		String responseContent = response.getContentAsString();

		Jackson2JsonParser parser = new Jackson2JsonParser();
		return parser.parseMap(responseContent).get("access_token").toString();
	}

	private String getAccessToken(boolean isNeedToCreateAccount) throws Exception
	{
		String userName = this.appProperties.getUserUserName();
		String password = this.appProperties.getUserPassword();

		if (isNeedToCreateAccount)
		{
			Account user = createAccount();
			userName = user.getEmail();
			password = user.getPassword();
		}

		String clientId = this.appProperties.getClientId();
		String clientSecret = this.appProperties.getClientSecret();

		ResultActions perform = this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(clientId, clientSecret))
				.param("username", userName)
				.param("password", password)
				.param("grant_type", "password"));

		MockHttpServletResponse response = perform.andReturn().getResponse();
		String responseContent = response.getContentAsString();

		Jackson2JsonParser parser = new Jackson2JsonParser();
		return parser.parseMap(responseContent).get("access_token").toString();
	}

	private Account createAccount()
	{
		String username = this.appProperties.getUserUserName();
		String password = this.appProperties.getUserPassword();
		Account account = Account.builder()
				.email(username)
				.password(password)
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		return this.accountService.saveAccount(account);
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
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
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
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
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
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest())
				.andDo(print())
//				.andExpect(jsonPath("$[0].objectName").exists())
//				.andExpect(jsonPath("$[0].field").exists())
//				.andExpect(jsonPath("$[0].defaultMessage").exists())
//				.andExpect(jsonPath("$[0].rejectedValue").exists())
//				.andExpect(jsonPath("$[0].code").exists())
				.andExpect(jsonPath("_links.index").exists());
	}

	@Test
	@TestDescription("30의 이벤트를 10개씩 두번째 페이지 조회하기")
	public void queryEvents() throws Exception
	{
		IntStream.range(0, 30).forEach(i -> {
			this.generateEvent(i);
		});

		this.mockMvc.perform(get("/api/events/")
					.param("page","1")
					.param("size", "10")
					.param("sort","name,DESC"))
				.andDo(print())
				.andDo(document("query-events"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_links.profile").exists())
				.andExpect(jsonPath("page").exists())
				.andExpect(jsonPath("_embedded.eventResourceList[0]._links.self").exists());
	}

	@Test
	@TestDescription("30의 이벤트를 10개씩 두번째 페이지 조회하기")
	public void queryEventsWithAuthentication() throws Exception
	{
		IntStream.range(0, 30).forEach(i -> {
			this.generateEvent(i);
		});

		this.mockMvc.perform(get("/api/events/")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.param("page","1")
				.param("size", "10")
				.param("sort","name,DESC"))
				.andDo(print())
				.andDo(document("query-events"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_links.profile").exists())
				.andExpect(jsonPath("_links.create-event").exists())
				.andExpect(jsonPath("page").exists())
				.andExpect(jsonPath("_embedded.eventResourceList[0]._links.self").exists());
	}

	private Event generateEvent(int idx)
	{
		Event event = buildEvent();
		event.setId(idx);
		return this.eventRepository.save(event);
	}

	private Event generateEvent(int idx, Account manager)
	{
		Event event = buildEvent();
		event.setId(idx);
		event.setManager(manager);
		return this.eventRepository.save(event);
	}

	private Event buildEvent()
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
				.free(false)
				.offline(true)
				.eventStatus(EventStatus.DRAFT)
				.build();
		return event;
	}

	@Test
	@TestDescription("이벤트 한 건을 조회하기")
	public void getEvent() throws Exception
	{
		Account account = createAccount();
		Event event = this.generateEvent(100, account);

		this.mockMvc.perform(get("/api/events/{id}", event.getId()))
				.andDo(print())
				.andDo(document("get-event"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("description").exists())
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.profile").exists());

	}

	@Test
	@TestDescription("존재하지 않는 이벤트를 조회하기")
	public void getEmptyEvent() throws Exception
	{
		this.mockMvc.perform(get("/api/events/{id}", 1234))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@TestDescription("이벤트 정보를 수정하기")
	public void updateEvent() throws Exception
	{
		Account account = createAccount();
		Event event = generateEvent(400, account);
		String changeName = "수정한 이벤트";

		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		eventDto.setName(changeName);

		this.mockMvc.perform(put("/api/events/{id}", event.getId())
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken(false))
								.contentType(MediaType.APPLICATION_JSON)
								.content(this.mapper.writeValueAsString(eventDto)))
				.andDo(print())
				.andDo(document("update-event"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value(changeName))
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.profile").exists());
	}

	@Test
	@TestDescription("입력값이 비어있는 경우에 이벤트 수정 실패")
	public void updateEvent_empty() throws Exception
	{
		Event event = this.generateEvent(300);

		EventDto eventDto = new EventDto();

		this.mockMvc.perform(put("/api/events/{id}", event.getId())
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
								.contentType(MediaType.APPLICATION_JSON)
								.content(this.mapper.writeValueAsString(eventDto)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("입력값이 잘못되있는 경우에 이벤트 수정 실패")
	public void updateEvent_wrong() throws Exception
	{
		Event event = this.generateEvent(200);

		EventDto eventDto = this.modelMapper.map(event, EventDto.class);

		eventDto.setBasePrice(1000);
		eventDto.setMaxPrice(100);

		this.mockMvc.perform(put("/api/events/{id}", event.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(eventDto)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@TestDescription("존재하지 않는 이벤트 수정 실패")
	public void updateEvent_notExist() throws Exception
	{
		Event event = this.generateEvent(200);

		EventDto eventDto = this.modelMapper.map(event, EventDto.class);

		this.mockMvc.perform(put("/api/events/{id}", 12345)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(eventDto)))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
