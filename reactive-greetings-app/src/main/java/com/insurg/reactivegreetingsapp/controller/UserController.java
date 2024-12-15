package com.insurg.reactivegreetingsapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("user")
public class UserController {

    @ModelAttribute("principal")
    public Mono<Principal> principal(Mono<Principal> principalMono) {
        return principalMono;
    }

    @GetMapping
    public String getUserPage() {
        return "user";
    }
}
