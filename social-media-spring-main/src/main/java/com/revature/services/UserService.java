package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByCredentials(int id) {
//        System.out.println(userRepository.findById(id));
        return userRepository.findById(id);
    }

    public User save(User user) throws InvalidInputException {

        if ( !validFirstname(user.getFirstName()) ) {
            throw new InvalidInputException("First Name was invalid. Your name should be 30 characters or less and contain no special characters.");
        }
        if ( !validLastname(user.getLastName()) ) {
            throw new InvalidInputException("Last Name was invalid. Your name should be 30 characters or less and contain no special characters.");
        }
        if ( !validEmail(user.getEmail()) ) {
            throw new InvalidInputException("That email address is invalid.");
        }
        if ( !validPassword(user.getPassword()) ) {
            throw new InvalidInputException("Your password must be between 6 and 20 characters in length.");
        }

        return userRepository.save(user);
    }

    /*update password
    public void updatePassword(String email, String password){
        userRepository.updatePassword(email, password);
    }*/

    // -----------------------------------------         VALIDATORS         ----------------------------------------- //

    // The methods below are very changeable if the database changes

    // Validate Password
    public boolean validPassword(String pwd){ //Only checking length for now (6 to 20)
        return (pwd.length()>5 && pwd.length()<21);
    }

    // Validate Username (formatting, not existence)
    public boolean validEmail(String email) throws InvalidInputException { //Only checking length for now, less than 256, the 'official' longest possible length

        // Check whether the user exists
        Optional<User> realUser = userRepository.findByEmail(email);
        if ( !realUser.isEmpty() ){
            throw new InvalidInputException("There is already an account registered with this email.");
        }

        // Check Length
        return (email.length()<256);
    }

    // Validate Firstname
    public boolean validFirstname(String frst){ //Checks for length (30 or less), and ensures all characters are letters, SPACE, or - (No number's, sorry Elon Musk's kid)
        return ( frst.matches("[-a-z. A-Z]+")  &&  frst.length()<31 );
    }

    // Validate Lastname
    public boolean validLastname(String last){ //Checks for length (30 or less), and ensures all characters are letters, SPACE, or -
        return ( last.matches("[-a-z. A-Z]+")  &&  last.length()<31 );
    }

    // ** NOTE:  "[-a-z. A-Z]+" is regex. It looks for a dash, letters a-z, periods, spaces, and letters A-Z. Order matters. [ ]+ checks existence

}
