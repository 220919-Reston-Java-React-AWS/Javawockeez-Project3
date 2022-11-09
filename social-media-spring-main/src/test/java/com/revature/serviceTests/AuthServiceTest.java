package com.revature.serviceTests;

import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    AuthService as;

    @Test
    public void register_INPUT_User_Expect_SameUser(){
        User tmpUser = new User();
        when(userService.save(tmpUser)).thenReturn(tmpUser);

        Assertions.assertEquals(tmpUser, as.register(tmpUser));
    }

    @Test
    public void findByCredentials_INPUT_validCredentials_EXPECT_User(){
        User tmpUser = new User();
        when(userService.save(tmpUser)).thenReturn(tmpUser);

        Assertions.assertEquals(tmpUser, as.register(tmpUser));
    }

}
