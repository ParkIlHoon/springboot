package org.hoon.springbootrestapi.common;

import org.hoon.springbootrestapi.events.EventController;
import org.hoon.springbootrestapi.index.IndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorResource extends RepresentationModel
{
	public ErrorResource(Errors content, Link... links)
	{
		this.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
	}
}
