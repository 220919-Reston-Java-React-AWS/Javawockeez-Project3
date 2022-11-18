package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public User patchAccountData(User user) {
        return userService.patchAccountData(user);
    }

}
