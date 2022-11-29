package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Service layer for login/registration.
@Service
public class AuthService {

    // Goes through the user service rather than accessing the repository directly.
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    // Find a user using their email (primarily) and password. Throws an InvalidInputException if not found saying why the search failed.
    // Used for logging in.
    public User findByCredentials(String email, String password) throws InvalidInputException {
        // Search for the user using their email, going through user-service first.
        Optional<User> optional = userService.findByEmail(email);

        // If the user was not found, throw an exception saying so.
        if(!optional.isPresent()) {
            throw new InvalidInputException("That email was not recognized by our system");
        }

        // Get the user out of the optional now that we know it exists (or the above error would be thrown)
        User newUser = optional.get();

        // If the password doesn't match, throw an exception saying so.
        if (!newUser.getPassword().equals(password)){
            throw new InvalidInputException("Your password was incorrect");
        }

        // Send back the user that was found.
        return newUser;
    }

    // Register the new user. User service does all the heavy lifting here.
    public User register(User user) throws InvalidInputException {
        return userService.save(user);
    }
}
