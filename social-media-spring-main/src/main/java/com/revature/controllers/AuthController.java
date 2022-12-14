package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.QuestionsRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.*;
import com.revature.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://s3demo110322.s3-website-us-west-2.amazonaws.com/", "http://javawockeez-social-react.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;
    private final ProfileService profileService;

    private final SampleQuestions1Service sampleQuestions1Service;

    private final SampleQuestions2Service sampleQuestions2Service;

    private final SampleQuestions3Service sampleQuestions3Service;

    private final SecurityQuestionService securityQuestionService;


    public AuthController(AuthService authService, UserService userService,
          SampleQuestions1Service sampleQuestions1Service, SampleQuestions2Service sampleQuestions2Service,
          SampleQuestions3Service sampleQuestions3Service, SecurityQuestionService securityQuestionService,
          ProfileService profileService
    ) {
        this.authService = authService;
        this.userService = userService;
        this.sampleQuestions1Service = sampleQuestions1Service;
        this.sampleQuestions2Service = sampleQuestions2Service;
        this.sampleQuestions3Service = sampleQuestions3Service;
        this.securityQuestionService = securityQuestionService;
        this.profileService = profileService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws InvalidInputException {
        User newUser = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        session.setAttribute("user", newUser);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) throws InvalidInputException
    {
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName());

        // need to save register user object first, returns the instantiated user
        created = authService.register(created);

        // Now we can use the registered user for the other registers
        /*** register a new profile ***/
        Profile registerProfile = new Profile();
        registerProfile.setId(0);
        registerProfile.setUser(created);
        profileService.registerProfile(registerProfile);

        /*** register the security questions ***/
        SecurityQuestion secure1 = new SecurityQuestion(0, registerRequest.getQuestion1(), registerRequest.getAnswer1(), created);
        SecurityQuestion secure2 = new SecurityQuestion(0, registerRequest.getQuestion2(), registerRequest.getAnswer2(), created);
        SecurityQuestion secure3 = new SecurityQuestion(0, registerRequest.getQuestion3(), registerRequest.getAnswer3(), created);

        securityQuestionService.addSecurityQuestion(secure1);
        securityQuestionService.addSecurityQuestion(secure2);
        securityQuestionService.addSecurityQuestion(secure3);

        // return the user as it was originally
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

//    @GetMapping("/hello")
//    public String hello(){
//        return "Hello World!";
//    }

    //Beginning of password changing section
    @PostMapping("/forgot-password")
    public ResponseEntity checkQuestions(@RequestBody QuestionsRequest questionsRequest) throws InvalidInputException{

        Optional<User> user = userService.findByEmail(questionsRequest.getEmail());

        if (user.isEmpty() == false){

        Optional<SecurityQuestion> check1 = securityQuestionService.findByQuestion(questionsRequest.getQuestion1(), questionsRequest.getAnswer1());
        Optional<SecurityQuestion> check2 = securityQuestionService.findByQuestion(questionsRequest.getQuestion2(), questionsRequest.getAnswer2());
        Optional<SecurityQuestion> check3 = securityQuestionService.findByQuestion(questionsRequest.getQuestion3(), questionsRequest.getAnswer3());

        if(check1.isEmpty() == false && check2.isEmpty() == false && check3.isEmpty() == false){
            
            this.userService.updatePassword(questionsRequest.getEmail(), questionsRequest.getPassword());
            
            
            return ResponseEntity.ok("Password successfully updated");
        }
    }

        return ResponseEntity.badRequest().build();
     
    }

    //get first set of questions
    @GetMapping("/questions1")
    public ResponseEntity<List<SampleQuestions1>> getQuestions(){
        return ResponseEntity.ok(this.sampleQuestions1Service.listQuestions());
    }

    //get second set of questions
    @GetMapping("/questions2")
    public ResponseEntity<List<SampleQuestions2>> getQuestions2(){
        return ResponseEntity.ok(this.sampleQuestions2Service.listQuestions());
    }

    //get third set
    @GetMapping("/questions3")
    public ResponseEntity<List<SampleQuestions3>> getQuestions3(){
        return ResponseEntity.ok(this.sampleQuestions3Service.listQuestions());
    }

    //get questions to reset password
    @GetMapping("/security-questions/{email}")
    public ResponseEntity<List<SecurityQuestion>> userQuestions(@PathVariable String email){
        Optional<User> user = userService.findByEmail(email);
        return ResponseEntity.ok(this.securityQuestionService.findByCredentials(user));
    }
}