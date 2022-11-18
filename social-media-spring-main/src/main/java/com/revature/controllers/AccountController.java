package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.AccountUpdateRequest;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import com.revature.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Update user profile
    @Authorized
    @PatchMapping("/update-account")
    public ResponseEntity patchAccountData(@RequestBody AccountUpdateRequest accountUpdateRequest) {
        User updateAccount = new User();
        updateAccount.setId(accountUpdateRequest.getId());
        updateAccount.setEmail(accountUpdateRequest.getEmail());
        updateAccount.setFirstName(accountUpdateRequest.getFirstName());
        updateAccount.setLastName(accountUpdateRequest.getLastName());

        if(!accountUpdateRequest.getPassword().equals("")){
            updateAccount.setPassword(accountUpdateRequest.getPassword());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.accountService.patchAccountData(updateAccount));
//
//        return ResponseEntity.ok(this.accountService.patchAccountData(updateAccount));
    }
}
