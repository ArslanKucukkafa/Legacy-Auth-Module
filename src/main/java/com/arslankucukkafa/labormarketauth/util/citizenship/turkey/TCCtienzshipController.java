package com.arslankucukkafa.labormarketauth.util.citizenship.turkey;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/citizenship/turkey")
public class TCCtienzshipController {

    private final TCKimlikNoService tcKimlikNoService;

    public TCCtienzshipController(TCKimlikNoService tcKimlikNoService) {
        this.tcKimlikNoService = tcKimlikNoService;
    }

    @PostMapping("/verify")
    public boolean verifyCtizenship(@RequestBody TCCtizenshipRequestModel requestModel) {
        return tcKimlikNoService.verifyCtizenship(requestModel.getCtizenshipId(), requestModel.getFirstName(), requestModel.getLastName(), requestModel.getBirthYear());
    }
}
