package com.example.springoidcsso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            model.addAttribute("name", oidcUser.getFullName());
            model.addAttribute("email", oidcUser.getEmail());
            model.addAttribute("claims", oidcUser.getClaims());
        }
        return "home";
    }
}
