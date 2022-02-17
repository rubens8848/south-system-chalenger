package br.com.forja.bits.south.controller;

import br.com.forja.bits.south.model.requests.LoginRequest;
import br.com.forja.bits.south.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity loginAdmin(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @GetMapping("/load-session")
    public ResponseEntity loadSessionAdmin() {
        return userService.getResponseByLoggedUser();
    }


}