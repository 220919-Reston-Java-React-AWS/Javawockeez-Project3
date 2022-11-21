package com.revature.serviceTests;


import com.revature.exceptions.InvalidInputException;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.repositories.PostRepository;
import com.revature.services.PostService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService ps;

    // -------------------------------------------------------------------------------------------------------------- //

    // Dummy values for the test. Represents the postRepository.
    User testUser;
    Post subPost;
    Post mainPost;

    List<Post> allPosts;

    // Static Counter for all tests
    static int counter = 0;

    @BeforeEach
    void initTests(){
        counter++; // In the before-each for curiosity's sake

        this.testUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
        this.subPost = new Post(2, "Test", "", new LinkedList<Post>(), this.testUser, new Date(2022, 11, 11));
        this.mainPost = new Post(1, "Hi", "", new LinkedList<Post>( Arrays.asList(this.subPost) ), this.testUser, new Date(2022, 11, 12));

        this.allPosts = Arrays.asList(this.mainPost, this.subPost);
    }

    @AfterEach
    void exitTests(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("Test " + counter + " Complete");
    }

    @BeforeAll
    static void setUp(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Starting Post Service Tests -----\u001B[0m");
    }
    @AfterAll
    static void tearDown(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Post Service Tests Complete ----\u001B[0m");
    }

    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void getAll_INSERT_none_EXPECT_allPosts(){

        when(postRepository.findAll()).thenReturn( allPosts );

        Assertions.assertEquals( allPosts, ps.getAll() );
    }

    @Test
    public void getAll_INSERT_noneWithEmptyDatabase_EXPECT_none(){
        when(postRepository.findAll()).thenReturn( Arrays.asList() );

        Assertions.assertEquals( Arrays.asList(), ps.getAll() );
    }

    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void getAllByAuthor_INSERT_testUser_EXPECT_testUsersPost(){
        Post testPost = new Post(3, "Test", "", new ArrayList<>(), testUser, new Date(2022, 12, 1));
        when(postRepository.findAllByAuthor(testUser)).thenReturn( Arrays.asList(testPost) );

        Assertions.assertEquals( Arrays.asList(testPost), ps.getAllByAuthor(testUser) );
    }

    @Test
    public void getAllByAuthor_INSERT_testUserWithNoPosts_EXPECT_none(){
        when(postRepository.findAllByAuthor(testUser)).thenReturn( Arrays.asList() );

        Assertions.assertEquals( Arrays.asList(), ps.getAllByAuthor(testUser) );
    }

    // -------------------------------------------------------------------------------------------------------------- //
    //Also tests private method ignoreChildren (in fact that's the main thing it tests)
    @Test
    public void getParents_INPUT_none_Expect_onlyParent(){

        when(postRepository.findAll()).thenReturn(allPosts);

        Assertions.assertEquals( Arrays.asList(mainPost), ps.getParents() );

    }

    @Test
    public void getParents_INPUT_noneWithEmptyDatabase_Expect_emptyList(){

        List<Post> allPosts = Arrays.asList();

        when(postRepository.findAll()).thenReturn(allPosts);

        Assertions.assertEquals( Arrays.asList(), ps.getParents() );

    }

    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void upsert_INSERT_post_EXPECT_post(){
        Post tmpPost = new Post();
        when(postRepository.save(tmpPost)).thenReturn(tmpPost);

        Assertions.assertEquals(tmpPost, ps.upsert(tmpPost));
    }

    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void findParent_INPUT_subPost_EXPECT_mainPost(){

        when(postRepository.findAll()).thenReturn(allPosts);

        try {
            Assertions.assertEquals(mainPost, ps.findParent(subPost));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findParent_INPUT_mainPost_EXPECT_InvalidInputException(){

        when(postRepository.findAll()).thenReturn(allPosts);

        Assertions.assertThrows(InvalidInputException.class, ()->{ps.findParent(mainPost);} );
    }

    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void remove_INPUT_mainPost_Expect_none(){

        when(postRepository.findAll()).thenReturn(allPosts);
        when(postRepository.save(mainPost)).thenReturn(mainPost);
        doNothing().when(postRepository).delete(mainPost);

        ps.remove(mainPost);

        verify(postRepository, times(1)).delete(mainPost);
    }

    @Test
    public void remove_INPUT_subPost_Expect_none(){

        when(postRepository.findAll()).thenReturn(allPosts);
        when(postRepository.save(mainPost)).thenReturn(mainPost);
        doNothing().when(postRepository).delete(subPost);

        ps.remove(subPost);

        verify(postRepository, times(1)).delete(subPost);
    }
}
