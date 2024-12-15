package com.insurg.greetingsservice.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurg.greetingsservice.presentation.Greetings;

@RestController
@RequestMapping("/greetings-api/greetings")
public class GreetingsRestController {

    @GetMapping
    public Greetings getGreetings(Principal principal) {
        return new Greetings("Будь как дома, %s!"
            .formatted(principal != null ? principal.getName() : "Путник"));
    }
}
