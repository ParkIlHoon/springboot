package org.hoon.springbootrestapi.index;

import org.hoon.springbootrestapi.events.EventController;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {

    @GetMapping("/api/")
    public RepresentationModel index() {
        RepresentationModel model = new RepresentationModel();
        model.add(linkTo(EventController.class).withRel("events"));
        return model;
    }
}
