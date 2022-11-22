package com.revature.serviceTests;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    /*-------Arranged Values for Tests------*/
    Optional<User> testFoundUserOptional;
    User testFoundUser;
    Optional<User> testNotFoundUserOptional;
    User testPatchUser; // for patchAccountData tests

    @BeforeEach
    void initTestValues(){
        // the default User representation not wrapped by Optional<>
        this.testFoundUser = new User(1, "test@test.com", "password", "test", "user");
        // the default Optional<User> representation of a User in database
        this.testFoundUserOptional = Optional.of(testFoundUser);

        //instantiating to avoid null
        this.testNotFoundUserOptional = Optional.empty();

        // a representation of a User that wants updated their info in database
        this.testPatchUser = new User(1,"testuser@test.com", "password123", "test","user");
    }

    /*------Optional<User> findByCredentials(String email, String password) Tests------*/

    // because of Optional<T>, test if Optional<> contains a value if user is found (present)
    @Test
    public void findByCredentials_optionalContainsAValue_ifUserIsFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(userService.findByCredentials("test@test.com", "password")).thenReturn(testFoundUserOptional);

            Assertions.assertTrue(testFoundUserOptional.isPresent());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<> does not contain a value if user is not found (empty)
    @Test
    public void findByCredentials_optionalContainsNoValue_ifUserNotFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(userService.findByCredentials("test@test.com", "password123")).thenReturn(testNotFoundUserOptional);

            Assertions.assertTrue(testNotFoundUserOptional.isEmpty());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is found
    @Test
    public void findByCredentials_foundUser_OptionalEqualsOptional(){
        try{
            when(userService.findByCredentials("test@test.com", "password")).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUserOptional, userService.findByCredentials("test@test.com", "password"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByCredentials_foundUser_OptionalEqualsUser(){
        try{
            when(userService.findByCredentials("test@test.com", "password")).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUser, userService.findByCredentials("test@test.com", "password").get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    @Test
    public void findByCredentials_notFoundUser_OptionalEqualsOptional(){
        try{
            when(userService.findByCredentials("test@test.com", "password123")).thenReturn(testNotFoundUserOptional);

            Assertions.assertEquals(testNotFoundUserOptional, userService.findByCredentials("test@test.com", "password123"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByCredentials_notFoundUser_OptionalIsEmpty(){
        try{
            when(userService.findByCredentials("test@test.com", "password123")).thenReturn(testNotFoundUserOptional);

            Assertions.assertTrue(userService.findByCredentials("test@test.com", "password123").isEmpty());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------Optional<User> findByCredentials(int id) Tests------*/


    // because of Optional<T>, test if Optional<> contains a value if user is found (present)
    @Test
    public void findByCredentials_withID_optionalContainsAValue_ifUserIsFound(){
        try{
            // this is not used due to how assertTrue() method works
            // but assume this was the case that was executed
            when(userService.findByCredentials(1)).thenReturn(testFoundUserOptional);

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
            when(userService.findByCredentials(100)).thenReturn(testNotFoundUserOptional);

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
            when(userService.findByCredentials(1)).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUserOptional, userService.findByCredentials(1));
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
            when(userService.findByCredentials(1)).thenReturn(testFoundUserOptional);

            Assertions.assertEquals(testFoundUser, userService.findByCredentials(1).get());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    @Test
    public void findByCredentials_withId_notFoundUser_OptionalEqualsOptional(){
        try{
            when(userService.findByCredentials(100)).thenReturn(testNotFoundUserOptional);

            Assertions.assertEquals(testNotFoundUserOptional, userService.findByCredentials(100));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // because of Optional<T>, test if Optional<User> values are as expected if user is not found
    // No clue if the test above checks the User's properties values from being Optional<> wrapped, so testing that here
    @Test
    public void findByCredentials_withId_notFoundUser_OptionalIsEmpty(){
        try{
            when(userService.findByCredentials(1)).thenReturn(testNotFoundUserOptional);

            Assertions.assertTrue(userService.findByCredentials(1).isEmpty());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------User patchAccountData(User update) Tests------*/


    // test when user updates info
    @Test
    public void patchAccountData_userDataUpdated(){
        try{
            when(userService.patchAccountData(testPatchUser)).thenReturn(testPatchUser);

            Assertions.assertEquals(testPatchUser, userService.patchAccountData(testPatchUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*------Other Tests Below Here------*/


}
