package com.insurg.greetingsapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @ModelAttribute("principal")
    public Principal principal(Principal principal) {
        return principal;
    }

    @GetMapping
    public String getUserPage() {
        return "user";
    }
}
