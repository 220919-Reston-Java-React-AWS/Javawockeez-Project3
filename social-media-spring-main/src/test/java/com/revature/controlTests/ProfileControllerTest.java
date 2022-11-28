package com.revature.controlTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ProfileController;
import com.revature.dtos.UpdateQuestions;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.SecurityQuestionRepository;
import com.revature.models.Profile;
import com.revature.models.SampleQuestions1;
import com.revature.models.SampleQuestions2;
import com.revature.models.SampleQuestions3;
import com.revature.models.SecurityQuestion;
import com.revature.services.ProfileService;
import com.revature.services.SampleQuestions1Service;
import com.revature.services.SampleQuestions2Service;
import com.revature.services.SampleQuestions3Service;
import com.revature.services.SecurityQuestionService;
import com.revature.services.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc // Gives us the ability to make mock fetch requests
public class ProfileControllerTest {
    @Mock
    ProfileService profileService;
    @Mock
    SecurityQuestionService securityQuestionService;
    @Mock
    SecurityQuestionRepository securityQuestionRepository;
    @Mock
    UserService userService;
    @Mock
    SampleQuestions1Service sampleQuestions1Service;
    @Mock
    SampleQuestions2Service sampleQuestions2Service;
    @Mock
    SampleQuestions3Service sampleQuestions3Service;
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
    List<Post> testAllPostsByUser;  // for testing ResponseEntity<User> getUserProfileName(@PathVariable int id)
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
    String question;
    String answer;
    String email;
    String newPassword;

    Optional<User> testFindByCredentials_UserOptional; // for testing ResponseEntity<User> getUserProfileName(@PathVariable int id)
    User testFindByCredentials_User;

    User testGetProfileByUserId_User;   // for testing ResponseEntity<Profile> getProfileByUserId(@PathVariable int id)
    Profile testProfileByUserId_Profile;
    Optional<Profile> testProfileByUserId_ProfileOptional;
    Optional<Profile> testProfileByUserId_ProfileOptionalEmpty;

    Profile testPatchProfileData_Update; // for testing ResponseEntity patchProfileData(@RequestBody Profile update)

    @BeforeEach
    void initTests(){
        mvc = MockMvcBuilders.standaloneSetup(this.profileController).build();

        /*** set up for getAllPostsByAuthorId(@PathVariable int id) ***/
        // a list of posts for tests
        this.testAllPostsByUser = Arrays.asList(
                new Post(1, "Hello", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user"), new Date()
        ),
                new Post(2, "World", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user"), new Date()
                )
        );
        // the User used for getting their posts
        this.testAllPostsUser = new User(1, "test@test.com", "password", "test", "user");

        // set strings
        this.question = new String("optional");
        this.answer = new String("question");
        this.email = new String("testuser@test.com");
        this.newPassword = new String("password1");

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
        
        // test posts
        this.testAllPostsUser = new User();
        this.testAllPostsUser.setId(1);

        /*** set up for getUserProfileName(@PathVariable int id) ***/
        // creating default user and its optional form
        this.testFindByCredentials_User = new User(1,"test@test.com", "password", "test", "user");
        this.testFindByCredentials_UserOptional = Optional.of(this.testFindByCredentials_User);

        /*** set up for getProfileByUserId(@PathVariable int id) ***/
        this.testGetProfileByUserId_User = this.testAllPostsUser;
        this.testProfileByUserId_Profile = new Profile(1,"about","avatar.jpg","banner.jpg", this.testFindByCredentials_User);
        this.testProfileByUserId_ProfileOptional = Optional.of(this.testProfileByUserId_Profile);
        this.testProfileByUserId_ProfileOptionalEmpty = Optional.empty();

        /*** set up for patchProfileData(@RequestBody Profile update) ***/
        this.testPatchProfileData_Update = new Profile(1,"new about","new_avatar.jpg","banner.jpg",this.testFindByCredentials_User);
    }


    /*------ResponseEntity<List<Post>> getAllPostsByAuthorId(@PathVariable int id) Tests------*/
    @Test
    public void getAllPostsByAuthorId_foundUserPosts() throws Exception {
        // creating the string returned from json
        String expectedResults = objectMapper.writeValueAsString(testAllPostsByUser);

        // note: the user must be the same as in the controller call to work
        when(profileService.getAllByAuthor(testAllPostsUser)).thenReturn(testAllPostsByUser);

        // execute the test
        mvc.perform(get(baseUrl + "/posts/1").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResults));
    }


    /*------ResponseEntity<User> getUserProfileName(@PathVariable int id) Tests------*/
    @Test
    public void getUserProfileName_foundUserPosts() throws Exception {
        // creating the string returned from json
        // had to remove the password segment because of @JsonIgnore
        String expectedResults = "{\"id\":1,\"email\":\"test@test.com\",\"firstName\":\"test\",\"lastName\":\"user\"}";

        // note: the user must be the same as in the controller call to work
        when(profileService.findByCredentials(1)).thenReturn(testFindByCredentials_UserOptional);

        // execute the test
        mvc.perform(get(baseUrl + "/user/1").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResults));
    }


    /*------ResponseEntity<Profile> getProfileByUserId(@PathVariable int id) Tests------*/

    // test when request is successful
    @Test
    public void getProfileByUserId_foundUserProfile() throws Exception {
        // creating the string returned from json
        // had to remove the password segment in User because of @JsonIgnore
        String expectedResults = "{\"id\":1,\"about\":\"about\"," +
                "\"avatarImageUrl\":\"avatar.jpg\",\"bannerImageUrl\":\"banner.jpg\"," +
                "\"user\":{\"id\":1,\"email\":\"test@test.com\",\"firstName\":\"test\",\"lastName\":\"user\"}}";

        // note: the user must be the same as in the controller call to work
        when(profileService.findByUser(testGetProfileByUserId_User)).thenReturn(testProfileByUserId_ProfileOptional);

        // execute the test
        mvc.perform(get(baseUrl + "/page/1").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResults));
    }

    // test when request is not successful because no profile is found
    @Test
    public void getProfileByUserId_notFoundUserProfile() throws Exception {
        // note: the user must be the same as in the controller call to work
        // assume a profile with this user id does not exist
        when(profileService.findByUser(testGetProfileByUserId_User)).thenReturn(testProfileByUserId_ProfileOptionalEmpty);

        // execute the test
        mvc.perform(get(baseUrl + "/page/1").contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    /*------ResponseEntity patchProfileData(@RequestBody Profile update) Tests------*/

    @Test
    public void patchProfileData_updateProfile() throws Exception {
        // json string of object containing update data, part of request body
        String inputJSON = objectMapper.writeValueAsString(testPatchProfileData_Update);

        // note: the user must be the same as in the controller call to work
        when(profileService.patchProfileData(testPatchProfileData_Update)).thenReturn(testPatchProfileData_Update);

        // execute the test
        mvc.perform(patch(baseUrl + "/update-profile").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
                .andExpect(status().isOk());
    }


    /*------Other Tests Here------*/


    // get questions by id

    @Test
    public void securityQuestionById() throws Exception{
         // creating the string returned from json
         String expectedResults = objectMapper.writeValueAsString(user1Questions);

         // use methods to test
         when(userService.findByCredentials(1)).thenReturn(optionalUser);
         when(securityQuestionService.findByCredentials(optionalUser)).thenReturn(user1Questions);
         
         // execute the test
         mvc.perform(get(baseUrl + "/security-questions/1").contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(content().json(expectedResults));
     }

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

     // update security questions

     /*@Test
     public void updateSecurityQuestions() throws Exception{
        // create string to be returned by json
        String inputJSON = "\"question\":\"update1\"," +
        "\"answer\":\"answer\",{\"id\":1,\"email\":\"testy@test.com\",\"firstName\":\"testy\",\"lastName\":\"testers\"}," +
        "\"question\":\"update2\"," +
        "\"answer\":\"answer\",{\"id\":1,\"email\":\"testy@test.com\",\"firstName\":\"testy\",\"lastName\":\"testers\"}," +
        "\"question\":\"update3\"," +
        "\"answer\":\"answer\",{\"id\":1,\"email\":\"testy@test.com\",\"firstName\":\"testy\",\"lastName\":\"testers\"}";

        


        this.updateQuestion1 = new SecurityQuestion(7, "update1", "answer", user1);


        //test methods
        doNothing().when(securityQuestionService).remove(testAllPostsUser);
        when(securityQuestionService.addSecurityQuestion(updateQuestion1)).thenReturn(updateQuestion1);
        when(securityQuestionService.addSecurityQuestion(updateQuestion2)).thenReturn(updateQuestion2);
        when(securityQuestionService.addSecurityQuestion(updateQuestion3)).thenReturn(updateQuestion3);

        
        //execute test
        mvc.perform(post(baseUrl + "/update-questions/1").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect( status().isOk() )
        .andExpect( content().string( "Questions successfully updated" ) );
     }*/

}
