package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.controllers.AuthController;
import com.revature.models.Profile;
import com.revature.models.SampleQuestions1;
import com.revature.models.SampleQuestions2;
import com.revature.models.SampleQuestions3;
import com.revature.models.SecurityQuestion;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    Optional<User> testFindByCredentialsUser;
    SampleQuestions1 sample1Question1;
    SampleQuestions1 sample1Question2;
    List<SampleQuestions1> sampleQuestions1List;
    SampleQuestions2 sample2Question1;
    SampleQuestions2 sample2Question2;
    List<SampleQuestions2> sampleQuestions2List;
    SampleQuestions3 sample3Question1;
    SampleQuestions3 sample3Question2;
    List<SampleQuestions3> sample3QuestionsList;
    SecurityQuestion testQuestion1;
    SecurityQuestion testQuestion2;
    SecurityQuestion testQuestion3;
    SecurityQuestion addQuestion;
    SecurityQuestion updateQuestion1;
    SecurityQuestion updateQuestion2;
    SecurityQuestion updateQuestion3;
    Optional<SecurityQuestion> optionalQuestion;
    Optional<SecurityQuestion> optionalQuestionNotFound;
    List<SecurityQuestion> user1Questions;
    List<SecurityQuestion> updatedList;
    User user1;
    User newPasswordUser;
    Optional<User> optionalUser;

    // Static Counter for all tests
    static int counter = 0;

    @BeforeEach
    void initTests(){

        mvc = MockMvcBuilders.standaloneSetup(this.ac).build();

        counter++; // In the before-each for curiosity's sake

        this.testUser = new User(1, "test@test.com", "password", "Testy", "McTestface");

        // set users
        this.user1 = new User(1, "testy@gmail.com", "password", "testy", "testers");
        this.optionalUser = Optional.of(user1);
        this.newPasswordUser = new User(1, "testy@gmail.com", "password1", "testy", "testers");

        // set questions
        this.testQuestion1 = new SecurityQuestion(1, "test question1", "answer", user1);
        this.testQuestion2 = new SecurityQuestion(2, "test question2", "answer", user1);
        this.testQuestion3 = new SecurityQuestion(3, "test question3", "answer", user1);
        this.updateQuestion1 = new SecurityQuestion(7, "update1", "answer", user1);
        this.updateQuestion2 = new SecurityQuestion(8, "update2", "answer", user1);
        this.updateQuestion3 = new SecurityQuestion(9, "update3", "answer", user1);
        this.optionalQuestion = Optional.of(testQuestion1);
        this.sample1Question1 = new SampleQuestions1(1, "test question");
        this.sample1Question2 = new SampleQuestions1(2, "test question 2");
        this.sample2Question1 = new SampleQuestions2(1, "test question");
        this.sample2Question2 = new SampleQuestions2(2, "test question 2");
        this.sample3Question1= new SampleQuestions3(1, "test question");
        this.sample3Question2 = new SampleQuestions3(2, "test question 2");

        // set lists
        this.user1Questions = Arrays.asList(testQuestion1, testQuestion2, testQuestion3);
        this.updatedList = Arrays.asList(updateQuestion1, updateQuestion2, updateQuestion3);
        this.sample3QuestionsList = Arrays.asList(sample3Question1, sample3Question2);
        this.sampleQuestions1List = Arrays.asList(sample1Question1, sample1Question2);
        this.sampleQuestions2List = Arrays.asList(sample2Question1, sample2Question2);

        // set users
        this.user1 = new User(1, "testy@gmail.com", "password", "testy", "testers");
        this.optionalUser = Optional.of(user1);
        this.newPasswordUser = new User(1, "testy@gmail.com", "password1", "testy", "testers");

        // set questions
        this.testQuestion1 = new SecurityQuestion(1, "test question1", "answer", user1);
        this.testQuestion2 = new SecurityQuestion(2, "test question2", "answer", user1);
        this.testQuestion3 = new SecurityQuestion(3, "test question3", "answer", user1);
        this.updateQuestion1 = new SecurityQuestion(7, "update1", "answer", user1);
        this.updateQuestion2 = new SecurityQuestion(8, "update2", "answer", user1);
        this.updateQuestion3 = new SecurityQuestion(9, "update3", "answer", user1);
        this.optionalQuestion = Optional.of(testQuestion1);
        this.sample1Question1 = new SampleQuestions1(1, "test question");
        this.sample1Question2 = new SampleQuestions1(2, "test question 2");
        this.sample2Question1 = new SampleQuestions2(1, "test question");
        this.sample2Question2 = new SampleQuestions2(2, "test question 2");
        this.sample3Question1= new SampleQuestions3(1, "test question");
        this.sample3Question2 = new SampleQuestions3(2, "test question 2");

        // set lists
        this.user1Questions = Arrays.asList(testQuestion1, testQuestion2, testQuestion3);
        this.updatedList = Arrays.asList(updateQuestion1, updateQuestion2, updateQuestion3);
        this.sample3QuestionsList = Arrays.asList(sample3Question1, sample3Question2);
        this.sampleQuestions1List = Arrays.asList(sample1Question1, sample1Question2);
        this.sampleQuestions2List = Arrays.asList(sample2Question1, sample2Question2);
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
        RegisterRequest newUser = new RegisterRequest(testUser.getEmail(), testUser.getPassword(), testUser.getFirstName(), testUser.getLastName(), testQuestion1.getQuestion(), testQuestion1.getAnswer(), testQuestion2.getQuestion(), testQuestion2.getAnswer(), testQuestion3.getQuestion(), testQuestion3.getAnswer());

        Profile registerProfile = new Profile();
        registerProfile.setId(0);
        registerProfile.setUser(testUser);

        String inputJSON = objectMapper.writeValueAsString(newUser);
        String correctJsonString = objectMapper.writeValueAsString(testUser);

        when(authService.register(testUser)).thenReturn(testUser);
        when(profileService.registerProfile(registerProfile)).thenReturn(registerProfile);
        when(securityQuestionService.addSecurityQuestion(testQuestion1)).thenReturn(testQuestion1);
        when(securityQuestionService.addSecurityQuestion(testQuestion2)).thenReturn(testQuestion2);
        when(securityQuestionService.addSecurityQuestion(testQuestion3)).thenReturn(testQuestion3);

        mvc.perform(post(baseUrl + "/register").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andExpect( status().isCreated() );

    }

    // Tests for sample questions

    // get sample questions 1

    @Test
    public void getSampleQuestions1() throws Exception{
       // create string to be returned by json
       String expectedResults = objectMapper.writeValueAsString(sampleQuestions1List);

       //test methods
       when(sampleQuestions1Service.listQuestions()).thenReturn(sampleQuestions1List);

       //execute test
       mvc.perform(get(baseUrl + "/questions1").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResults));
    }

    // get sample questions 2

    @Test
    public void getSampleQuestions2() throws Exception{
       // create string to be returned by json
       String expectedResults = objectMapper.writeValueAsString(sampleQuestions2List);

       //test methods
       when(sampleQuestions2Service.listQuestions()).thenReturn(sampleQuestions2List);

       //execute test
       mvc.perform(get(baseUrl + "/questions2").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResults));
    }

    // get sample questions 3

    @Test
    public void getSampleQuestions3() throws Exception{
       // create string to be returned by json
       String expectedResults = objectMapper.writeValueAsString(sample3QuestionsList);

       //test methods
       when(sampleQuestions3Service.listQuestions()).thenReturn(sample3QuestionsList);

       //execute test
       mvc.perform(get(baseUrl + "/questions3").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedResults));
    }

    // test for getting security questions
    // get questions by email

    @Test
    public void securityQuestionByEmail() throws Exception{
         // creating the string returned from json
         String expectedResults = objectMapper.writeValueAsString(user1Questions);

         // use methods to test
         when(userService.findByEmail("testy@gmail.com")).thenReturn(optionalUser);
         when(securityQuestionService.findByCredentials(optionalUser)).thenReturn(user1Questions);

         // execute the test
         mvc.perform(get(baseUrl + "/security-questions/testy@gmail.com").contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().json(expectedResults));
     }

}
