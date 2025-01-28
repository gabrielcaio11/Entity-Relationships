package br.com.gabrielcaio.entityrelationships.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        return "Olá "+ authentication.getName();
    }
}
