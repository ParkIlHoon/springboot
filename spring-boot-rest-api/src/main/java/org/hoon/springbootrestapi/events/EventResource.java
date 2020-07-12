package org.hoon.springbootrestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends RepresentationModel
{
	@JsonUnwrapped
	private Event event;

	public EventResource(Event e)
	{
		this.event = e;
		// 자신에 대한 self 링크를 자동으로 생성하도록 처리
		this.add(linkTo(EventController.class).slash(e.getId()).withSelfRel());
	}

	public Event getEvent() {
		return event;
	}
}
