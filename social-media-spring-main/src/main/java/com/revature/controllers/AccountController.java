package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.AccountUpdateRequest;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://s3demo110322.s3-website-us-west-2.amazonaws.com/"}, allowCredentials = "true")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
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

        // see if password is set
        if(!accountUpdateRequest.getPassword().equals("")){
            updateAccount.setPassword(accountUpdateRequest.getPassword());
        }
        else{
            Optional<User> user = userService.findByCredentials(accountUpdateRequest.getId());
            updateAccount.setPassword(user.get().getPassword());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.accountService.patchAccountData(updateAccount));
    }
}
