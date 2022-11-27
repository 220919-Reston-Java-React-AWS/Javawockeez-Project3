package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revature.controllers.AuthController;
import com.revature.models.User;
import com.revature.services.*;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

}
