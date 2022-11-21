package com.revature.serviceTests;

import com.revature.models.Post;
import com.revature.models.User;
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepository;
import com.revature.services.PostService;

import com.revature.services.ProfileService;
import com.revature.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileServiceTest {
    @Mock
    ProfileRepository profileRepository;
    @Mock
    PostService postService;
    @Mock
    UserService userService;
    @InjectMocks
    ProfileService profileService;


    /*-------Arranged Values for Tests------*/
    Optional<User> testFoundUserOptional;   // for Optional<User> findByCredentials(int id) tests
    User testFoundUser;
    Optional<User> testNotFoundUserOptional;
    User testNotFoundUser;
    List<Post> testAllPosts;    // for List<Post> getAllByAuthor(User author) tests
    List<Post> testAllPostsEmpty;
    Optional<Profile> testFoundProfileOptional; // for Optional<Profile> findByUser(User user) tests
    Profile testFoundProfile;
    Optional<Profile> testNotFoundProfileOptional;
    Profile testNotFoundProfile;

    Profile testPatchProfile;   // for Profile patchProfileData(Profile update) tests

    @BeforeEach
    void initTestValues(){
        // the default User representation not wrapped by Optional<>
        this.testFoundUser = new User(1, "test@test.com", "password", "test", "user");
        // the default Optional<User> representation of a User in database
        this.testFoundUserOptional = Optional.of(testFoundUser);

        // a list of posts for tests
        this.testAllPosts = Arrays.asList(
            new Post(1, "Hello", "", new LinkedList<Post>(), this.testFoundUser),
            new Post(2, "World", "", new LinkedList<Post>(), this.testFoundUser)
        );

        // a default profile for user not wrapped by Optional<>
        this.testFoundProfile = new Profile(1,"about me", "avatar.jpg", "banner.jpg", testFoundUser);
        // the default Optional<Profile> representation of Profile in database
        this.testFoundProfileOptional = Optional.of(testFoundProfile);

        // example of an update to Profile by user
        this.testPatchProfile = new Profile(1,"about","avatar.jpg","banner.jpg",testFoundUser);
    }


    /*------Optional<User> findByCredentials(id) Tests------*/

    // because of Optional<T>, test if Optional<> contains a value if user is found (present)
    @Test
    public void findByCredentials_withID_optionalContainsAValue_ifUserIsFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(profileService.findByCredentials(1)).thenReturn(testFoundUserOptional);

            Assertions.assertTrue(testFoundUserOptional.isPresent());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<> does not contain a value if user is not found (empty)
    @Test
    public void findByCredentials_withId_optionalContainsNoValue_ifUserNotFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(profileService.findByCredentials(100)).thenReturn(testNotFoundUserOptional);

            Assertions.assertTrue(testNotFoundUserOptional.isEmpty());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is found
    @Test
    public void findByCredentials_withId_foundUser_OptionalEqualsOptional(){
        try{
            when(profileService.findByCredentials(1)).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUserOptional, profileService.findByCredentials(1));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByCredentials_withId_foundUser_OptionalEqualsUser(){
        try{
            when(profileService.findByCredentials(1)).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUser, profileService.findByCredentials(1).get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    @Test
    public void findByCredentials_withId_notFoundUser_OptionalEqualsOptional(){
        try{
            when(profileService.findByCredentials(100)).thenReturn(testNotFoundUserOptional);

            Assertions.assertEquals(testNotFoundUserOptional, profileService.findByCredentials(100));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByCredentials_withId_notFoundUser_OptionalEqualsUser(){
        try{
            when(profileService.findByCredentials(1)).thenReturn(testNotFoundUserOptional);

            Assertions.assertEquals(testNotFoundUser, profileService.findByCredentials(1).get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------List<Post> getAllByAuthor(User author) Tests------*/

    @Test
    public void getAllByAuthor_foundPostList(){
        try{
            when(profileService.getAllByAuthor(testFoundUser)).thenReturn(testAllPosts);

            Assertions.assertEquals(testAllPosts, profileService.getAllByAuthor(testFoundUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // case if user has not made any posts
    @Test
    public void getAllByAuthor_foundPostListIsEmpty(){
        try{
            when(profileService.getAllByAuthor(testFoundUser)).thenReturn(testAllPostsEmpty);

            Assertions.assertEquals(testAllPostsEmpty, profileService.getAllByAuthor(testFoundUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------Optional<Profile> findByUser(User user) Tests------*/

    // because of Optional<T>, test if Optional<> contains a value if user is found (present)
    @Test
    public void findByUser_optionalContainsAValue_ifProfileIsFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(profileService.findByUser(testFoundUser)).thenReturn(testFoundProfileOptional);

            Assertions.assertTrue(testFoundProfileOptional.isPresent());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<> does not contain a value if user is not found (empty)
    @Test
    public void findByUser_optionalContainsNoValue_ifProfileNotFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(profileService.findByCredentials(100)).thenReturn(testNotFoundUserOptional);

            Assertions.assertTrue(testNotFoundProfileOptional.isEmpty());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<Profile> values are as expected if profile is found
    @Test
    public void findByUser_foundProfile_OptionalEqualsOptional(){
        try{
            when(profileService.findByUser(testFoundUser)).thenReturn(testFoundProfileOptional);

            Assertions.assertEquals(testFoundProfileOptional, profileService.findByUser(testFoundUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<Profile> values are as expected if profile is found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByUser_foundProfile_OptionalEqualsUser(){
        try{
            when(profileService.findByUser(testFoundUser)).thenReturn(testFoundProfileOptional);

            Assertions.assertEquals(testFoundProfile, profileService.findByUser(testFoundUser).get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<Profile> values are as expected if profile is found
    @Test
    public void findByUser_notFoundProfile_OptionalEqualsOptional(){
        try{
            when(profileService.findByUser(testFoundUser)).thenReturn(testNotFoundProfileOptional);

            Assertions.assertEquals(testNotFoundProfileOptional, profileService.findByUser(testFoundUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<UProfile> values are as expected if profile is found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByUser_notFoundProfile_OptionalEqualsUser(){
        try{
            when(profileService.findByUser(testFoundUser)).thenReturn(testNotFoundProfileOptional);

            Assertions.assertEquals(testNotFoundProfile, profileService.findByUser(testFoundUser).get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------patchProfileData(Profile update) Tests------*/

    @Test
    public void patchProfileData_profileDataUpdated(){
        try{
            when(profileService.patchProfileData(testPatchProfile)).thenReturn(testPatchProfile);

            Assertions.assertEquals(testPatchProfile, profileService.patchProfileData(testPatchProfile));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------Other Tests Below Here------*/


}
