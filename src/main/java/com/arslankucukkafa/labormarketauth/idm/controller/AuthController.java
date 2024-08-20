package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.action.MutualBeanScanner;
import com.arslankucukkafa.labormarketauth.action.PermissionType;
import com.arslankucukkafa.labormarketauth.action.Permissionable;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.LoginDto;
import com.arslankucukkafa.labormarketauth.idm.model.DTO.RegisterDto;
import com.arslankucukkafa.labormarketauth.idm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@Permissionable(permissionType = PermissionType.FOR_EVERYONE)
public class AuthController {
    private final MutualBeanScanner mutualBeanScanner;
    private final UserService userService;

    public AuthController(MutualBeanScanner mutualBeanScanner, UserService userService) {
        this.mutualBeanScanner = mutualBeanScanner;
        this.userService = userService;
    }


    @GetMapping("/hello")
    public ResponseEntity<Set> deıfjnk() {
        List<Class<? extends Annotation>> annotationTypes = new ArrayList<>();
        annotationTypes.add(RestController.class);
        annotationTypes.add(Permissionable.class);
        var result = mutualBeanScanner.mutualAnnotationCluster(annotationTypes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterDto registerDto) {
        userService.saveUser(registerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        return userService.signIn(loginDto);
    }
}
