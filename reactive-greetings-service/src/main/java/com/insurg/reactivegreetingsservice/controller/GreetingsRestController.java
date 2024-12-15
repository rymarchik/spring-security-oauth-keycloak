package com.insurg.reactivegreetingsservice.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurg.reactivegreetingsservice.presentation.Greetings;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/greetings-api/greetings")
public class GreetingsRestController {

    @GetMapping
    public Mono<Greetings> getGreetings(Mono<Principal> principalMono) {
        return principalMono.map(principal -> "Будь как дома, %s!".formatted(principal.getName()))
            .defaultIfEmpty("Будь как дома, Путник")
            .map(Greetings::new);
    }
}
