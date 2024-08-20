package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.action.Permissionable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Permissionable
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
}
