package org.hoon.springbootrestapi.events;

import org.hoon.springbootrestapi.account.Account;
import org.hoon.springbootrestapi.account.AccountAdapter;
import org.hoon.springbootrestapi.common.CurrentUser;
import org.hoon.springbootrestapi.common.ErrorResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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

	@Autowired
	ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors, @CurrentUser Account account)
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
		mapped.update();
		mapped.setManager(account);
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
	public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler, @CurrentUser Account account)
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

		if (account != null)
		{
			pageResource.add(linkTo(EventController.class).withRel("create-event"));
		}

		return ResponseEntity.ok(pageResource);
	}

	@GetMapping("/{id}")
	public ResponseEntity getEvent(@PathVariable Integer id, @CurrentUser Account account)
	{
		Optional<Event> optionalEvent = this.repository.findById(id);

		if (optionalEvent.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}

		Event event = optionalEvent.get();
		EventResource eventResource = new EventResource(event);
		eventResource.add(new Link("/docs/index.html#get-event").withRel("profile"));

		if (event.getManager().equals(account))
		{
			eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}

		return ResponseEntity.ok(eventResource);
	}

	@PutMapping("/{id}")
	public ResponseEntity updateEvent(@PathVariable Integer id, @RequestBody @Valid EventDto eventDto, Errors errors, @CurrentUser Account account)
	{
		if (errors.hasErrors())
		{
			return ResponseEntity.badRequest().body(new ErrorResource(errors));
		}

		Optional<Event> optionalEvent = this.repository.findById(id);

		if (optionalEvent.isEmpty())
		{
			return ResponseEntity.notFound().build();
		}
		else
		{
			validator.validate(eventDto, errors);

			if (errors.hasErrors())
			{
				return ResponseEntity.badRequest().body(new ErrorResource(errors));
			}

			Event event = optionalEvent.get();

			// 업데이트 할 이벤트의 Manager가 현재 로그인한 사용자가 아닌 경우
			if(!event.getManager().equals(account))
			{
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
			}

			this.modelMapper.map(eventDto, event);

			Event savedEvent = this.repository.save(event);

			EventResource eventResource = new EventResource(savedEvent);
			eventResource.add(new Link("/docs/index.html#update-event").withRel("profile"));

			return ResponseEntity.ok(eventResource);
		}
	}

}
