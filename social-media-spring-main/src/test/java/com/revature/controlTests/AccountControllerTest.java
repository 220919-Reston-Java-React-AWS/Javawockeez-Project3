package com.revature.controlTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AccountController;
import com.revature.dtos.AccountUpdateRequest;
import com.revature.models.User;

import com.revature.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc // Gives us the ability to make mock fetch requests
public class AccountControllerTest {
    @Mock
    AccountService accountService;
    @InjectMocks
    AccountController accountController;

    // Automatically inject the object that will be doing the fetch requests
    @Autowired
    MockMvc mvc;

    // Used to JSON-stringify our objects for input and expected-output
    ObjectMapper objectMapper = new ObjectMapper();

    // The url all requests use (and possibly add to)
    static String baseUrl = "http://localhost:8080/account";

    /*-------Arranged Values for Tests------*/
    // for testing ResponseEntity patchAccountData(@RequestBody AccountUpdateRequest accountUpdateRequest)
    AccountUpdateRequest accountUpdateRequest;
    User patchAccountData_User;

    @BeforeEach
    void initTests(){
        mvc = MockMvcBuilders.standaloneSetup(this.accountController).build();

        /*** set up for patchAccountData(@RequestBody AccountUpdateRequest accountUpdateRequest) ***/
        this.accountUpdateRequest = new AccountUpdateRequest(1,"updatedEmail@test.com", "updatedPassword", "Jerry", "user");
        this.patchAccountData_User = new User(1,"test@test.com", "updatedPassword", "test", "user");
    }

    /*------ResponseEntity patchAccountData(@RequestBody AccountUpdateRequest accountUpdateRequest) Tests------*/

    @Test
    public void patchAccountData_UpdateAccountData() {
        try {
            // json string of object containing update data, part of request body
            String inputJSON = objectMapper.writeValueAsString(accountUpdateRequest);
            String expectedResults = "{\"id\":1,\"email\":\"updatedEmail@test.com\"," +
                    "\"firstName\":\"Jerry\",\"lastName\":\"user\"}";

            // note: the user must be the same as in the controller call to work
            when(accountService.patchAccountData(patchAccountData_User)).thenReturn(patchAccountData_User);

            // execute the test
            mvc.perform(patch(baseUrl + "/update-account").contentType(MediaType.APPLICATION_JSON).content(inputJSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().json(expectedResults));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
