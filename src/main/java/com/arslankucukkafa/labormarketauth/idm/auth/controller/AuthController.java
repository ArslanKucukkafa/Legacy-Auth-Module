package com.arslankucukkafa.labormarketauth.idm.auth.controller;

import com.arslankucukkafa.labormarketauth.idm.auth.model.AuthLoginDto;
import com.arslankucukkafa.labormarketauth.idm.auth.model.AuthRegisterDto;
import com.arslankucukkafa.labormarketauth.idm.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody AuthRegisterDto registerDto) {
        authService.saveUser(registerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity login(@RequestBody AuthLoginDto loginDto) {
        return authService.signIn(loginDto);
    }

}
