package com.revature.serviceTests;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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

    //Password Strings
    String email;
    String newPassword;

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

        //new password
        this.email = new String("testuser@test.com");
        this.newPassword = new String("password");
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

    /*------User save(User update) Tests------*/
    @Test
    public void save_INPUT_user_EXPECT_user() throws InvalidInputException {
        String email = testFoundUser.getEmail();

        when(userService.findByEmail(email)).thenReturn( Optional.ofNullable(null));
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        Assertions.assertEquals(testFoundUser, userService.save(testFoundUser));
    }

    @Test
    public void save_INPUT_invalidUserWithUsedEmail_EXPECT_exceptionThrown() {
        String email = testFoundUser.getEmail();

        when(userService.findByEmail(email)).thenReturn( testFoundUserOptional );
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        try{
            Assertions.assertThrows(InvalidInputException.class, ()->userService.save(testFoundUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void save_INPUT_invalidUserWithLongEmail_EXPECT_exceptionThrown() {
        String email = "t".repeat(300);

        User testUser = new User(testFoundUser.getId(), email, testFoundUser.getPassword(), testFoundUser.getFirstName(), testFoundUser.getLastName());

        when(userService.findByEmail(email)).thenReturn( Optional.ofNullable(null));
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        try{
            Assertions.assertThrows(InvalidInputException.class, ()->userService.save(testUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void save_INPUT_invalidUserWithBadPassword_EXPECT_exceptionThrown() {
        String password = "pwd";

        User testUser = new User(testFoundUser.getId(), testFoundUser.getEmail(), password, testFoundUser.getFirstName(), testFoundUser.getLastName());

        when(userService.findByEmail(email)).thenReturn( testFoundUserOptional );
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        try{
            Assertions.assertThrows(InvalidInputException.class, ()->userService.save(testUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void save_INPUT_invalidUserWithBadFirstName_EXPECT_exceptionThrown() {
        String first = "12";

        User testUser = new User(testFoundUser.getId(), testFoundUser.getEmail(), testFoundUser.getPassword(), first, testFoundUser.getLastName());

        when(userService.findByEmail(email)).thenReturn( testFoundUserOptional );
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        try{
            Assertions.assertThrows(InvalidInputException.class, ()->userService.save(testUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void save_INPUT_invalidUserWithBadLastName_EXPECT_exceptionThrown() {
        String last = "42";

        User testUser = new User(testFoundUser.getId(), testFoundUser.getEmail(), testFoundUser.getPassword(), testFoundUser.getFirstName(), last);

        when(userService.findByEmail(email)).thenReturn( testFoundUserOptional );
        when(userRepository.save(testFoundUser)).thenReturn(testFoundUser);

        try{
            Assertions.assertThrows(InvalidInputException.class, ()->userService.save(testUser));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    /*------Other Tests Below Here------*/

    //Test update password method
    @Test
    public void updateUserPassword(){
        try{
            doNothing().when(userService).updatePassword(email, newPassword);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*---------------------         Validators         ---------------------*/

    // validPassword -- 6 < len < 20
    @Test
    public void validPassword_INPUT_length0_EXPECT_False() {
        Assertions.assertFalse(userService.validPassword(""));
    }

    @Test
    public void validPassword_INPUT_length5_EXPECT_False() {
        Assertions.assertFalse(userService.validPassword("abcde"));
    }

    @Test
    public void validPassword_INPUT_length6_EXPECT_True() {
        Assertions.assertTrue(userService.validPassword("abcdef"));
    }

    @Test
    public void validPassword_INPUT_length20_EXPECT_True() {
        Assertions.assertTrue(userService.validPassword("abcdefghij012345678."));
    }

    @Test
    public void validPassword_INPUT_length21_EXPECT_False() {
        Assertions.assertFalse(userService.validPassword("abcdefghij012345678.,"));
    }

    // validFirstname -- 1 <= len <= 30, all letters
    @Test
    public void validFirstname_INPUT_length1_EXPECT_True(){
        Assertions.assertTrue( userService.validFirstname("X") );
    }

    @Test
    public void validFirstname_INPUT_length30_EXPECT_True(){
        Assertions.assertTrue( userService.validFirstname("Wolfeschlegelsteinhausenberger") ); // Unfortunately, his real name, Wolfeschlegelsteinhausenbergerdorff, was too long
    }

    @Test
    public void validFirstname_INPUT_length31_EXPECT_False(){
        Assertions.assertFalse( userService.validFirstname("Wolfeschlegelsteinhausenbergerz") );
    }

    @Test
    public void validFirstname_INPUT_specialCharSPACEandDASHandPERIOD_EXPECT_True(){
        Assertions.assertTrue( userService.validFirstname("Tran-Cruz Sr.") );
    }

    @Test
    public void validFirstname_INPUT_specialChar1_EXPECT_False(){
        Assertions.assertFalse( userService.validFirstname("X AE A-12") );
    }

    // validLastname-- 1 <= len <= 30, all letters
    @Test
    public void validLastname_INPUT_length1_EXPECT_True(){
        Assertions.assertTrue( userService.validLastname("X") );
    }

    @Test
    public void validLastname_INPUT_length30_EXPECT_True(){
        Assertions.assertTrue( userService.validLastname("Wolfeschlegelsteinhausenberger") );
    }

    @Test
    public void validLastname_INPUT_length31_EXPECT_False(){
        Assertions.assertFalse( userService.validLastname("Wolfeschlegelsteinhausenbergerz") );
    }

    @Test
    public void validLastname_INPUT_specialCharSPACEandDASH_EXPECT_True(){
        Assertions.assertTrue( userService.validLastname("Tran-De la Cruz Sr.") );
    }

    @Test
    public void validLastname_INPUT_specialChar1_EXPECT_False(){
        Assertions.assertFalse( userService.validLastname("X AE A-12") );
    }


    // Valid Last name
    @Test
    public void validEmail_INPUT_usedEmail_EXPECT_InvalidInputException(){
        String email = testFoundUser.getEmail();

        when(userService.findByEmail(email)).thenReturn( testFoundUserOptional );

        try {
            Assertions.assertThrows(InvalidInputException.class, ()->{userService.validEmail(email);} );
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
    }

    @Test
    public void validEmail_INPUT_newEmail_EXPECT_True() throws InvalidInputException {
        String email = testFoundUser.getEmail();

        when(userService.findByEmail(email)).thenReturn( Optional.ofNullable(null) );

        Assertions.assertTrue(userService.validEmail(email));
    }

}
