package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.advice.ExceptionLogger;
import com.revature.controllers.PostController;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;

import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest // Flags as test
@AutoConfigureMockMvc // Gives us the ability to make mock fetch requests
public class PostControlTest {

    // Mock the serivice layer, and ...
    @Mock
    PostService ps;

    // Inject it into the controller layer (so we don't use the actual service layer and make real requests)
    @InjectMocks
    PostController pc;

    // Automatically inject the object that will be doing the fetch requests
    @Autowired
    MockMvc mvc;

    // Used to JSON-stringify our objects for input and expected-output
    ObjectMapper objectMapper = new ObjectMapper();

    // The url all requests use (and possibly add to)
    static String baseUrl = "http://localhost:8080/post";

    // -------------------------------------------------------------------------------------------------------------- //

    // Dummy values for the test. Represents the postRepository in the PostService ps.
    // Will be set at the beginning of each test.
    User testUser;
    Post subPost;
    Post mainPost;

    List<Post> allPosts;

    // Static Counter for all tests
    static int counter = 0;

    @BeforeEach
    void initTests(){

        mvc = MockMvcBuilders.standaloneSetup(this.pc).setControllerAdvice(new ExceptionLogger()).build();

        counter++; // In the before-each for curiosity's sake

        this.testUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
        this.subPost = new Post(2, "Test", "", new LinkedList<Post>(), this.testUser, new Date());
        this.mainPost = new Post(1, "Hi", "", new LinkedList<Post>( Arrays.asList(this.subPost) ), this.testUser, new Date());

        this.allPosts = Arrays.asList(this.mainPost, this.subPost);
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
        Assertions.assertNotNull(pc);
    }

    @Test
    public void getAll_Expect_allPosts() throws Exception {

        String correctOutput = objectMapper.writeValueAsString(allPosts);

        when( ps.getAll() )
                .thenReturn( allPosts );

        mvc.perform(get(baseUrl + "/all"))
                .andDo( print() ) // Just prints the request and response, maybe take out?
                .andExpect( status().isOk() )
                .andExpect( content().json( correctOutput ) ); // We expect the response from the request to be a JSON object matching all posts (the list of all posts, as JSON)

    }

    @Test
    public void getParents_EXPECT_onlyMainPosts() throws Exception {

        List<Post> parents = Arrays.asList(mainPost);
        String correctJsonString = objectMapper.writeValueAsString(parents);

        when(ps.getParents()).thenReturn(parents);

        mvc.perform(get(baseUrl))
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( content().json( correctJsonString ) );
    }

    @Test
    public void upsertPost_EXPECT_samePostWithNewId() throws Exception{

        Post beforeUpsert = new Post(0, "Hi", "", new LinkedList<Post>( Arrays.asList(this.subPost) ), this.testUser, new Date());;

        String inputJSON = objectMapper.writeValueAsString(beforeUpsert);
        String correctJsonString = objectMapper.writeValueAsString(mainPost);

        when(ps.upsert(beforeUpsert)).thenReturn(mainPost);

        mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( content().json( correctJsonString ) );
    }

    @Test
    public void removePost_EXPECT_successMessage() throws Exception{

        String inputJSON = objectMapper.writeValueAsString(mainPost);

        doNothing().when(ps).remove(mainPost);

        mvc.perform(delete(baseUrl).contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( content().string( "Removed Successfully" ) );
    }
}
