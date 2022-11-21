package com.revature.serviceTests;

import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest {
    @Mock
    UserService userService;
    @InjectMocks
    AccountService accountService;

    /*-------Arranged Values for Tests------*/
    User testPatchAccountData;

    @BeforeEach
    void initTestValues(){
        // the default User representation not wrapped by Optional<>
        this.testPatchAccountData = new User(1, "updated@test.com", "password123", "test", "user");
    }

    /*------User patchAccountData(User user) Tests------*/

    @Test
    public void patchAccountData_accountDataUpdated(){
        try{
            when(accountService.patchAccountData(testPatchAccountData)).thenReturn(testPatchAccountData);

            Assertions.assertEquals(testPatchAccountData, accountService.patchAccountData(testPatchAccountData));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
