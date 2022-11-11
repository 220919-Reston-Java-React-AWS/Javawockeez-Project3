package com.revature.serviceTests;

import com.revature.exceptions.InvalidInputException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

    @Test
    public void register_INPUT_User_Expect_SameUser(){
        try {
            User tmpUser = new User();
            when(userService.save(tmpUser)).thenReturn(tmpUser);

            Assertions.assertEquals(tmpUser, as.register(tmpUser));
        } catch (InvalidInputException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findByCredentials_INPUT_validCredentials_EXPECT_userOptional(){
        User tmpUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
        when(userService.findByCredentials(tmpUser.getEmail(), tmpUser.getPassword())).thenReturn( Optional.of( tmpUser ) );

        Assertions.assertEquals(Optional.of( tmpUser ), as.findByCredentials(tmpUser.getEmail(), tmpUser.getPassword()));
    }

    @Test
    public void findByCredentials_INPUT_invalidCredentials_EXPECT_emptyOptional(){
        User tmpUser = new User(1, "test@test.com", "password", "Testy", "McTestface");
        when(userService.findByCredentials("est@test.com", tmpUser.getPassword())).thenReturn( Optional.empty( ) );

        Assertions.assertEquals(Optional.empty( ), as.findByCredentials(tmpUser.getEmail(), tmpUser.getPassword()));
    }

}
