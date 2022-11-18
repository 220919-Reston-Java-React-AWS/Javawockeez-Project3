package com.revature.serviceTests;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    AuthService as;

    // -------------------------------------------------------------------------------------------------------------- //

    // Dummy values for the test. Represents the userRepository in userService.
    User testUser;

    static int counter;

    @BeforeEach
    void initTests(){
        counter++;
        testUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
    }

    @AfterEach
    void exitTests(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("Test " + counter + " Complete");
    }

    @BeforeAll
    static void setUp(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Starting Auth Service Tests -----\u001B[0m");
    }
    @AfterAll
    static void tearDown(){ // Kinda Pointless, mainly to practice and have a reference
        System.out.println("\n\n\t\t\u001B[40m\u001B[32m---- Auth Service Tests Complete ----\u001B[0m");
    }
    // -------------------------------------------------------------------------------------------------------------- //

    @Test
    public void register_INPUT_User_Expect_SameUser(){
        try {
            when(userService.save(testUser)).thenReturn(testUser);

            Assertions.assertEquals(testUser, as.register(testUser));
            
        } catch (InvalidInputException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findByCredentials_INPUT_validCredentials_EXPECT_userOptional(){

        when(userService.findByCredentials(testUser.getEmail(), testUser.getPassword())).thenReturn( Optional.of( testUser ) );

        Assertions.assertEquals(Optional.of( testUser ), as.findByCredentials(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    public void findByCredentials_INPUT_invalidCredentials_EXPECT_emptyOptional(){

        when(userService.findByCredentials("est@test.com", testUser.getPassword())).thenReturn( Optional.empty( ) );

        Assertions.assertEquals(Optional.empty( ), as.findByCredentials(testUser.getEmail(), testUser.getPassword()));
    }

}
