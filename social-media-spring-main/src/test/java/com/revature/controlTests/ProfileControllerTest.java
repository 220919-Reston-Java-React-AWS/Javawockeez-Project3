package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ProfileController;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.models.Profile;
import com.revature.models.SampleQuestions1;
import com.revature.models.SampleQuestions2;
import com.revature.models.SampleQuestions3;
import com.revature.models.SecurityQuestion;
import com.revature.services.ProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc // Gives us the ability to make mock fetch requests
public class ProfileControllerTest {
    @Mock
    ProfileService profileService;
    @InjectMocks
    ProfileController profileController;

    // Automatically inject the object that will be doing the fetch requests
    @Autowired
    MockMvc mvc;

    // Used to JSON-stringify our objects for input and expected-output
    ObjectMapper objectMapper = new ObjectMapper();

    // The url all requests use (and possibly add to)
    static String baseUrl = "http://localhost:8080/profile";

    /*-------Arranged Values for Tests------*/
    List<Post> testAllPostsByUser;
    User testAllPostsUser;
    Optional<User> testFindByCredentialsUser;
    User testUserProfile;
    Profile testUpdateProfile;
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
    SecurityQuestion testQuestion4;
    SecurityQuestion testQuestion5;
    SecurityQuestion testQuestion6;
    SecurityQuestion addQuestion;
    SecurityQuestion updateQuestion1;
    SecurityQuestion updateQuestion2;
    SecurityQuestion updateQuestion3;
    Optional<SecurityQuestion> optionalQuestion;
    Optional<SecurityQuestion> optionalQuestionNotFound;
    List<SecurityQuestion> testQuestionList;
    List<SecurityQuestion> user1Questions;
    List<SecurityQuestion> user2Questions;
    List<SecurityQuestion> updatedList;
    User user1;
    User user2; 
    Optional<User> optionalUser;
    String question;
    String answer;
    String email;
    String newPassword;

    @BeforeEach
    void initTests(){
        mvc = MockMvcBuilders.standaloneSetup(this.profileController).build();

        // a list of posts for tests
        this.testAllPostsByUser = Arrays.asList(
                new Post(1, "Hello", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user"), new Date()
        ),
                new Post(2, "World", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user"), new Date()
                )
        );

        // the User used for getting their posts
        this.testAllPostsUser = new User(1, "test@test.com", "password", "test", "user");

        // set users
        this.user1 = new User(1, "testy@gmail.com", "password", "testy", "testers");
        this.user2 = new User(2, "tester@yahoo.com", "password2", "tester", "security");
        this.optionalUser = Optional.of(user1);

        // set strings
        this.question = new String("optional");
        this.answer = new String("question");
        this.email = new String("testuser@test.com");
        this.newPassword = new String("password");

        // set questions
        this.testQuestion1 = new SecurityQuestion(1, "test question1", "answer", user1);
        this.testQuestion2 = new SecurityQuestion(2, "test question2", "answer", user1);
        this.testQuestion3 = new SecurityQuestion(3, "test question3", "answer", user1);
        this.testQuestion4 = new SecurityQuestion(4, "test question1", "answer1", user2);
        this.testQuestion5 = new SecurityQuestion(5, "test question2", "answer2", user2);
        this.testQuestion6 = new SecurityQuestion(6, "test question3", "answer3", user2);
        this.addQuestion = new SecurityQuestion(10, "test question 10", "answer", user2);
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
        this.testQuestionList = Arrays.asList(testQuestion1, testQuestion2, testQuestion3, testQuestion4, testQuestion5, testQuestion6);
        this.user1Questions = Arrays.asList(testQuestion1, testQuestion2, testQuestion3);
        this.user2Questions = Arrays.asList(testQuestion4, testQuestion5, testQuestion6);
        this.updatedList = Arrays.asList(testQuestion1, testQuestion2, testQuestion3, updateQuestion1, updateQuestion2, updateQuestion3);
        this.sample3QuestionsList = Arrays.asList(sample3Question1, sample3Question2);
        this.sampleQuestions1List = Arrays.asList(sample1Question1, sample1Question2);
        this.sampleQuestions2List = Arrays.asList(sample2Question1, sample2Question2);
        }


    /*------Testing Tests------*/

    @Test
    public void contextLoads(){
        Assertions.assertNotNull(profileController);
    }

    /*------ResponseEntity<User> getUserProfileName(@PathVariable int id) Tests------*/
    @Test
    public void getAllPostsByAuthorId_foundUserPosts() throws Exception {
//        System.out.println(testAllPostsByUser);
        String expectedResults = objectMapper.writeValueAsString(testAllPostsByUser);
//        System.out.println(expectedResults);
        User testing = new User();
        testing.setId(1);

        when(profileService.getAllByAuthor(testing)).thenReturn(testAllPostsByUser);

        mvc.perform(get(baseUrl + "/posts/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResults));

    }





}
