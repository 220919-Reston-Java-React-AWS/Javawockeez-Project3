package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ProfileController;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.models.Profile;

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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @BeforeEach
    void initTests(){
        mvc = MockMvcBuilders.standaloneSetup(this.profileController).build();

        // a list of posts for tests
        this.testAllPostsByUser = Arrays.asList(
                new Post(1, "Hello", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user")),
                new Post(2, "World", "", new LinkedList<Post>(), new User(1, "test@test.com", "password", "test", "user"))
        );

        // the User used for getting their posts
        this.testAllPostsUser = new User(1, "test@test.com", "password", "test", "user");
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
