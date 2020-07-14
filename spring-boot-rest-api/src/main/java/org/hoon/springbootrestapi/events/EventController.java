package org.hoon.springbootrestapi.events;

import org.hoon.springbootrestapi.common.ErrorResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
			return ResponseEntity.badRequest().body(new ErrorResource(errors));
		}

		validator.validate(eventDto, errors);

		if (errors.hasErrors())
		{
			return ResponseEntity.badRequest().body(new ErrorResource(errors));
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
		eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));

		return ResponseEntity.created(createUri).body(eventResource);
	}

	@GetMapping
	public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler)
	{
		Page<Event> page = this.repository.findAll(pageable);
		//페이지 링크도 받으려면
		//var pageResource = assembler.toModel(page);
		// HATEOAS까지 구현하려면
		var pageResource = assembler.toModel(page, new RepresentationModelAssembler<Event, RepresentationModel<?>>() {
			@Override
			public RepresentationModel<?> toModel(Event entity) {
				return new EventResource(entity);
			}
		});
		pageResource.add(new Link("/docs/index.html#query-events").withRel("profile"));

		return ResponseEntity.ok(pageResource);
	}

}
