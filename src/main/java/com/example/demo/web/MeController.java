package com.example.demo.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MeController {

    @GetMapping("/")
    public String home() {
        return "OK. <a href=\"/oauth2/authorization/auth0\">Login with Auth0</a> | <a href=\"/h2\">H2 Console</a>";
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OidcUser user) {
        return Map.of(
                "sub", user.getSubject(),
                "email", user.getEmail(),
                "name", user.getFullName(),
                "claims", user.getClaims()
        );
    }
}
