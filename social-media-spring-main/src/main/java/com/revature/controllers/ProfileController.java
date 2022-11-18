package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.Response;
import com.revature.exceptions.ExceptionLogger;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.User;
import com.revature.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // get all posts from user with id
    @GetMapping("/posts/{id}")
    public ResponseEntity<List<Post>> getAllPostsByAuthorId(@PathVariable int id) {
        // if you look at Posts models, the author_id column in the database is represented using a User model
        User author = new User();
        author.setId(id);

        return ResponseEntity.ok(this.profileService.getAllByAuthor(author));
    }


    // get user information with id
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserProfileName(@PathVariable int id){
        Optional<User> optional = this.profileService.findByCredentials(id);

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(optional.get());
    }

    // get profile of user with id
    @GetMapping("/page/{id}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable int id) {
        // if you look at Posts models, the author_id column in the database is represented using a User model
        User user = new User();
        user.setId(id);

        Optional<Profile> optional = this.profileService.findByUser(user);

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(optional.get());
    }

    // Update user profile
    @PatchMapping("/update-profile")
    public ResponseEntity patchProfileData(@RequestBody Profile update){
        return ResponseEntity.ok(this.profileService.patchProfileData(update));
    }
}
