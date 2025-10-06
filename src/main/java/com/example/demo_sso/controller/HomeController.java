package com.example.demo_sso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    // Trang công khai
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Trang sau khi login
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            model.addAttribute("name", oidcUser.getFullName());
            model.addAttribute("email", oidcUser.getEmail());
            model.addAttribute("claims", oidcUser.getClaims());
        }
        return "home";
    }

    // Endpoint test access token và refresh token
    @GetMapping("/tokens")
    @ResponseBody
    public Map<String, Object> tokens(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient client) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", oidcUser != null ? oidcUser.getFullName() : "anonymous");
        map.put("accessToken", client.getAccessToken().getTokenValue());
        map.put("accessTokenExpiresAt", client.getAccessToken().getExpiresAt());
        map.put("refreshToken", client.getRefreshToken() != null ? client.getRefreshToken().getTokenValue() : "null");
        return map;
    }
}
