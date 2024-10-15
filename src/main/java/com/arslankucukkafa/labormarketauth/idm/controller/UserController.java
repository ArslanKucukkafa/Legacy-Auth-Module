package com.arslankucukkafa.labormarketauth.idm.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RestController

// arslan.kucukkafa : Burda kullanıcıya müdahale etmeye yönelik işlemler yerine daha çok kullanıcı bilgilerini getirmeye yönelik işlemler ve istatistiksel bilgiler getirme işlemleri yapılabilir
// arslan.kucukkafa: Daha çok Just reader not writer
public class UserController {

    @GetMapping
    public String getUser() {
        return "User";
    }

    @GetMapping("/all")
    public String getAllUsers() {
        return "All Users";
    }

    @GetMapping("/all/isactive")
    public String getAllActiveUsers(@RequestParam("isActive") boolean isActive){
        return "All Active Users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") String id) {
        return "User Deleted";
    }

    // todo: bir QueryFilter dto or generic class oluşturulabilir, çünkü herbir filtre için ayrı bir method oluşturmak yerine tek bir method ile tüm filtreleme işlemleri yapılabilir




}
