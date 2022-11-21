package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revature.services.SampleQuestions1Service;
import com.revature.services.SampleQuestions2Service;
import com.revature.services.SampleQuestions3Service;
import com.revature.services.SecurityQuestionService;
import com.revature.services.UserService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final SampleQuestions1Service sampleQuestions1Service;

    private final SampleQuestions2Service sampleQuestions2Service;

    private final SampleQuestions3Service sampleQuestions3Service;

    private final SecurityQuestionService securityQuestionService;


    public AuthController(AuthService authService, UserService userService, SampleQuestions1Service sampleQuestions1Service, SampleQuestions2Service sampleQuestions2Service, SampleQuestions3Service sampleQuestions3Service, SecurityQuestionService securityQuestionService) {
        this.authService = authService;
        this.userService = userService;
        this.sampleQuestions1Service = sampleQuestions1Service;
        this.sampleQuestions2Service = sampleQuestions2Service;
        this.sampleQuestions3Service = sampleQuestions3Service;
        this.securityQuestionService = securityQuestionService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
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
//
//        System.out.println(created);
//        System.out.println(registerRequest.getQuestion1());
//
//        SecurityQuestion secure1 = new SecurityQuestion(0, registerRequest.getQuestion1(), registerRequest.getAnswer1(), created);
//        SecurityQuestion secure2 = new SecurityQuestion(0, registerRequest.getQuestion2(), registerRequest.getAnswer2(), created);
//        SecurityQuestion secure3 = new SecurityQuestion(0, registerRequest.getQuestion3(), registerRequest.getAnswer3(), created);
//
//        System.out.println(secure1);
//        System.out.println(secure2);
//        System.out.println(secure3);
//
//        securityQuestionService.addSecurityQuestion(secure1);
//        securityQuestionService.addSecurityQuestion(secure2);
//        securityQuestionService.addSecurityQuestion(secure3);
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    //Beginning of password changing section
    /*@PostMapping("/forgot-password")
    public ResponseEntity checkQuestions(@RequestBody QuestionsRequest questionsRequest, HttpSession session){
        Optional<User> optional = authService.findByCredentials(questionsRequest.getEmail(), questionsRequest.getPassword());

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setEmail(questionsRequest.getEmail());

        SecurityQuestion check1 = new SecurityQuestion(0, questionsRequest.getQuestion1(), questionsRequest.getAnswer1(), user);
        SecurityQuestion check2 = new SecurityQuestion(0, questionsRequest.getQuestion2(), questionsRequest.getAnswer2(), user);
        SecurityQuestion check3 = new SecurityQuestion(0, questionsRequest.getQuestion3(), questionsRequest.getAnswer3(), user);

        System.out.println(check1);
        System.out.println(check2);
        System.out.println(check3);

        if(securityQuestionService.findbySecurityQuestion(check1) != null && securityQuestionService.findbySecurityQuestion(check2) != null && securityQuestionService.findbySecurityQuestion(check3) != null){
            
            this.userService.updatePassword(questionsRequest.getEmail(), questionsRequest.getPassword());
            
            
            return ResponseEntity.ok("Password successfully updated");
        }

        return ResponseEntity.badRequest().build();
     
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

    @GetMapping("/security-questions/{email}")
    public ResponseEntity<List<SecurityQuestion>> userQuestions(@PathVariable String email){
        User user = new User();
        user.setEmail(email);
        return ResponseEntity.ok(this.securityQuestionService.findByEmail(user));
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<List<SecurityQuestion>> userQuestions(@PathVariable int id){
        User user = new User();
        user.setId(id);

        return ResponseEntity.ok(this.securityQuestionService.findByCredentials(user));
    }

    @DeleteMapping("/delete-questions/{id}")
    public ResponseEntity replaceQuestions(@PathVariable int id){
        User user = new User();
        user.setId(id);
        this.securityQuestionService.remove(user);
        return ResponseEntity.ok("Replaced questions successfully.");
    }
 */
}