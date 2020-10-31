package org.hoon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("/exception")
    public String exception() {
        throw new SampleException();
    }

    @ExceptionHandler(SampleException.class)
    public String handler(SampleException exception) {
        return exception.getMessage();
    }
}
