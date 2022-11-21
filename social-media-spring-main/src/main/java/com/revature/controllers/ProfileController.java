package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.Response;
import com.revature.dtos.UpdatePassword;
import com.revature.dtos.UpdateQuestions;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.models.SampleQuestions1;
import com.revature.models.SampleQuestions2;
import com.revature.models.SampleQuestions3;
import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.services.ProfileService;
import com.revature.services.SampleQuestions1Service;
import com.revature.services.SampleQuestions2Service;
import com.revature.services.SampleQuestions3Service;
import com.revature.services.SecurityQuestionService;
import com.revature.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final SecurityQuestionService securityQuestionService;
    private final SampleQuestions1Service sampleQuestions1Service;
    private final SampleQuestions2Service  sampleQuestions2Service;
    private final SampleQuestions3Service  sampleQuestions3Service;

    public ProfileController(ProfileService profileService, UserService userService, SecurityQuestionService securityQuestionService, SampleQuestions1Service sampleQuestions1Service, SampleQuestions2Service sampleQuestions2Service, SampleQuestions3Service sampleQuestions3Service) {
        this.profileService = profileService;
        this.userService = userService;
        this.securityQuestionService = securityQuestionService;
        this.sampleQuestions1Service = sampleQuestions1Service;
        this.sampleQuestions2Service = sampleQuestions2Service;
        this.sampleQuestions3Service = sampleQuestions3Service;
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

    //gets questions to update questions in profile page
    @GetMapping("/security-questions/{email}")
    public ResponseEntity<List<SecurityQuestion>> userQuestions(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        return ResponseEntity.ok(this.securityQuestionService.findByCredentials(user));
    }

    @GetMapping("/questions1")
    public ResponseEntity<List<SampleQuestions1>> getQuestions(){
        return ResponseEntity.ok(this.sampleQuestions1Service.listQuestions());
    }

    @GetMapping("/questions2")
    public ResponseEntity<List<SampleQuestions2>> getQuestions2(){
        return ResponseEntity.ok(this.sampleQuestions2Service.listQuestions());
    }

    @GetMapping("/questions3")
    public ResponseEntity<List<SampleQuestions3>> getQuestions3(){
        return ResponseEntity.ok(this.sampleQuestions3Service.listQuestions());
    }

    //update security questions

    @PostMapping("/update-questions/{id}")
    public ResponseEntity updateQuestions(@RequestBody UpdateQuestions updateQuestions, @PathVariable int id){

        User user = new User();
        user.setId(id);

        SecurityQuestion secure1 = new SecurityQuestion(0, updateQuestions.getQuestion1(), updateQuestions.getAnswer1(), user);
        SecurityQuestion secure2 = new SecurityQuestion(0, updateQuestions.getQuestion2(), updateQuestions.getAnswer2(), user);
        SecurityQuestion secure3 = new SecurityQuestion(0, updateQuestions.getQuestion3(), updateQuestions.getAnswer3(), user);


        securityQuestionService.remove(user);

        securityQuestionService.addSecurityQuestion(secure1);
        securityQuestionService.addSecurityQuestion(secure2);
        securityQuestionService.addSecurityQuestion(secure3);

        return ResponseEntity.ok("Questions successfully updated");
    }
    
    //update password
    @PostMapping("/update-password/{email}")
    public ResponseEntity updatePassword(@RequestBody UpdatePassword updatePassword, @PathVariable String email){
        this.userService.updatePassword(email, updatePassword.getPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }
}
