package org.hoon.springbootrestapi.events;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events/", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController
{
	@Autowired
	EventRepository repository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	EventValidator validator;

	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors)
	{
		if (errors.hasErrors())
		{
			return ResponseEntity.badRequest().body(errors);
		}

		validator.validate(eventDto, errors);

		if (errors.hasErrors())
		{
			return ResponseEntity.badRequest().body(errors);
		}

		Event mapped = mapper.map(eventDto, Event.class);

		Event newEvent = repository.save(mapped);

		// HATEOAS 처리
		ControllerLinkBuilder selfBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI createUri = selfBuilder.toUri();

		EventResource eventResource = new EventResource(newEvent);
		eventResource.add(linkTo(EventController.class).withRel("query-events"));
		//eventResource.add(selfBuilder.withSelfRel());
		eventResource.add(selfBuilder.withRel("update-event"));

		return ResponseEntity.created(createUri).body(eventResource);
	}

}
