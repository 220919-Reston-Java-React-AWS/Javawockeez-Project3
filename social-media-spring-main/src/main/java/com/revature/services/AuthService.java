package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User findByCredentials(String email, String password) throws InvalidInputException {
        //return userService.findByCredentials(email, password);
        Optional<User> optional = userService.findByEmail(email);

        if(!optional.isPresent()) {
            throw new InvalidInputException("That email was not recognized by our system");
        }

        User newUser = optional.get();
        if (!newUser.getPassword().equals(password)){
            throw new InvalidInputException("Your password was incorrect");
        }

        return newUser;
    }

    public User register(User user) throws InvalidInputException {
        return userService.save(user);
    }
}
