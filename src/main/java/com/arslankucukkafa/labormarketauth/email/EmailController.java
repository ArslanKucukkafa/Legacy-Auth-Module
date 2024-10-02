/*
package com.arslankucukkafa.labormarketauth.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // ARSLAN.KUCUKKAFA: Verify endpoint email adresini dogrular.
    @GetMapping("/verify/{token}")
    public void verifyEmail() {

    }

    // ARSLAN.KUCUKKAFA: forgot-password endpointi email adresine sifre sifirlama linki gonderir. Buna karşılık gelen endpoint user tarafından yazılmalı yada burası usera taşınmalı.
    // FIXME: Bu kısım biraz user ile iç içe geçti
    @GetMapping("/forgot-password/")
    public void forgotPassword(@RequestBody String email) {

    }


    // ARSLAN.KUCUKKAFA: authenticate endpointi dogrulanacak email adresine magic-link gonderir.
    @GetMapping("/authenticate/{token}")
    public void authenticate() {

    }


}
*/
