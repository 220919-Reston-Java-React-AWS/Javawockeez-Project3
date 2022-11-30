package com.revature.services;

import com.revature.models.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    // Updates the user's account information
    public User patchAccountData(User user) {
        return userService.patchAccountData(user);
    }

}
