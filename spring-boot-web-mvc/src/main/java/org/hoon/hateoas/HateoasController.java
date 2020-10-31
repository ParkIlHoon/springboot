package org.hoon.hateoas;

import org.hoon.vo.Hello;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HateoasController {

    @GetMapping("/hateoas")
    public EntityModel test() {
        Hello returnValue = new Hello();
        returnValue.setName("테스트");
        returnValue.setPrefix("key");

        EntityModel<Hello> helloResource = new EntityModel<>(returnValue);

        helloResource.add(linkTo(methodOn(HateoasController.class).test()).withSelfRel());

        return helloResource;
    }
}
