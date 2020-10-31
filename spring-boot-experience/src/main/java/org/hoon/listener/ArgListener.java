package org.hoon.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ArgListener {

    public ArgListener(ApplicationArguments arguments) {
        System.out.println("[ArgListener] foo : " + arguments.containsOption("foo"));
        System.out.println("[ArgListener] bar : " + arguments.containsOption("bar"));
    }
}
