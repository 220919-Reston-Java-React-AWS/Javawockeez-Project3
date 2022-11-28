package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Profile;
import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.services.*;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Flags as test
@AutoConfigureMockMvc // Gives us the ability to make mock fetch requests
public class AuthControllerTest {

    @Mock
    AuthService authService;
    @Mock
    UserService userService;
    @Mock
    ProfileService profileService;
    @Mock
    SampleQuestions1Service sampleQuestions1Service;
    @Mock
    SampleQuestions2Service sampleQuestions2Service;
    @Mock
    SampleQuestions3Service sampleQuestions3Service;
    @Mock
    SecurityQuestionService securityQuestionService;

    @InjectMocks
    AuthController ac;

    // -------------------------------------------------------------------------------------------------------------- //

    // Automatically inject the object that will be doing the fetch requests
    @Autowired
    MockMvc mvc;

    // Used to JSON-stringify our objects for input and expected-output
    ObjectMapper objectMapper = new ObjectMapper();

    // The url all requests use (and possibly add to)
    static String baseUrl = "http://localhost:8080/auth";

    // -------------------------------------------------------------------------------------------------------------- //

    // Dummy values for the test. Represents the postRepository in the PostService ps.
    // Will be set at the beginning of each test.
    User testUser;

    // Static Counter for all tests
    static int counter = 0;

    @BeforeEach
    void initTests(){

        mvc = MockMvcBuilders.standaloneSetup(this.ac).build();

        counter++; // In the before-each for curiosity's sake

        this.testUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
    }

    @AfterEach
    void exitTests(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\nTest " + counter + " Complete\n\n");
    }

    @BeforeAll
    static void setUp(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Starting Post Controller Tests -----\u001B[0m");
    }
    @AfterAll
    static void tearDown(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Post Controller Tests Complete ----\u001B[0m");
    }

    // -------------------------------------------------------------------------------------------------------------- //

    // Dummy test to get started.
    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(ac);
    }

    @Test
    public void logout_EXPECT_ok() throws Exception { // Should never be problematic
        mvc.perform(post(baseUrl + "/logout"))
                .andExpect( status().isOk() );
    }

    @Test
    public void login_INPUT_validCredentials_EXPECT_correctUser() throws Exception {
        LoginRequest login = new LoginRequest(testUser.getEmail(), testUser.getPassword());

        String inputJSON = objectMapper.writeValueAsString(login);
        String correctJsonString = objectMapper.writeValueAsString(testUser);

        when(authService.findByCredentials(login.getEmail(), login.getPassword())).thenReturn(testUser);

        mvc.perform(post(baseUrl + "/login").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andExpect( status().isOk() )
                .andExpect( content().json( correctJsonString ) );
    }

//    @Test
//    public void login_INPUT_badEmail_EXPECT_exception() throws Exception {
//        LoginRequest login = new LoginRequest("bad@test.com", testUser.getPassword());
//        InvalidInputException e = new InvalidInputException("Bad email");
//
//        String inputJSON = objectMapper.writeValueAsString(login);
//        String correctJsonString = objectMapper.writeValueAsString(e);
//
//        //when(authService.findByCredentials(login.getEmail(), login.getPassword())).thenThrow(e);
//        doThrow(e).when(authService).findByCredentials(login.getEmail(), login.getPassword());
//
//        mvc.perform(post(baseUrl + "/login").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect( status().isBadRequest() )
//                .andExpect( content().json( correctJsonString ) );
//    }

    @Test
    public void register_INPUT_validRegistration_EXPECT_newUser() throws Exception {
        SecurityQuestion q1 = new SecurityQuestion(1, "What is 2+2", "4", testUser);
        SecurityQuestion q2 = new SecurityQuestion(1, "What is 1+2", "3", testUser);
        SecurityQuestion q3 = new SecurityQuestion(1, "What is the answer", "42", testUser);
        RegisterRequest newUser = new RegisterRequest(testUser.getEmail(), testUser.getPassword(), testUser.getFirstName(), testUser.getLastName(), q1.getQuestion(), q1.getAnswer(), q2.getQuestion(), q2.getAnswer(), q3.getQuestion(), q3.getAnswer());

        Profile registerProfile = new Profile();
        registerProfile.setId(0);
        registerProfile.setUser(testUser);

        String inputJSON = objectMapper.writeValueAsString(newUser);
        String correctJsonString = objectMapper.writeValueAsString(testUser);

        when(authService.register(testUser)).thenReturn(testUser);
        when(profileService.registerProfile(registerProfile)).thenReturn(registerProfile);
        when(securityQuestionService.addSecurityQuestion(q1)).thenReturn(q1);
        when(securityQuestionService.addSecurityQuestion(q2)).thenReturn(q2);
        when(securityQuestionService.addSecurityQuestion(q3)).thenReturn(q3);

        mvc.perform(post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andExpect( status().isCreated() );

    }

}
