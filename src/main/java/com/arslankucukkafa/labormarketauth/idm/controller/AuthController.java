package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.idm.model.DTO.UserLoginDto;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.UserRegisterDto;
import com.arslankucukkafa.labormarketauth.idm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


/*    @GetMapping("/hello")
    public ResponseEntity<Set> deıfjnk() {
        List<Class<? extends Annotation>> annotationTypes = new ArrayList<>();
        annotationTypes.add(RestController.class);
        var result = mutualBeanScanner.mutualAnnotationCluster(annotationTypes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity signup(@RequestBody UserRegisterDto registerDto) {
        userService.saveUser(registerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

/*
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto loginDto) {
        return userService.signIn(loginDto);
    }
*/
}
