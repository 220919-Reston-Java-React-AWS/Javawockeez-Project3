package com.revature.serviceTests;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.repositories.SecurityQuestionRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.SecurityQuestionService;
import com.revature.services.UserService;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SecurityQuestionServiceTest {
    @Mock
    SecurityQuestionRepository securityQuestionRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    SecurityQuestionService securityQuestionService;
    @InjectMocks
    UserService userService;

    /*-------Arranged Values for Tests------*/
    SecurityQuestion testQuestion1;
    SecurityQuestion testQuestion2;
    SecurityQuestion testQuestion3;
    SecurityQuestion testQuestion4;
    SecurityQuestion testQuestion5;
    SecurityQuestion testQuestion6;
    SecurityQuestion addQuestion;
    SecurityQuestion updateQuestion1;
    SecurityQuestion updateQuestion2;
    SecurityQuestion updateQuestion3;
    Optional<SecurityQuestion> optionalQuestion;
    Optional<SecurityQuestion> optionalQuestionNotFound;
    List<SecurityQuestion> testQuestionList;
    List<SecurityQuestion> user1Questions;
    List<SecurityQuestion> user2Questions;
    List<SecurityQuestion> updatedList;
    User user1;
    User user2; 
    Optional<User> optionalUser;
    String question;
    String answer;

    @BeforeEach
    void initTestValues(){

        // set users
        this.user1 = new User(1, "testy@gmail.com", "password", "testy", "testers");
        this.user2 = new User(2, "tester@yahoo.com", "password2", "tester", "security");
        this.optionalUser = Optional.of(user1);

        // set strings
        this.question = new String("optional");
        this.answer = new String("question");

        // set questions
        this.testQuestion1 = new SecurityQuestion(1, "test question1", "answer", user1);
        this.testQuestion2 = new SecurityQuestion(2, "test question2", "answer", user1);
        this.testQuestion3 = new SecurityQuestion(3, "test question3", "answer", user1);
        this.testQuestion4 = new SecurityQuestion(4, "test question1", "answer1", user2);
        this.testQuestion5 = new SecurityQuestion(5, "test question2", "answer2", user2);
        this.testQuestion6 = new SecurityQuestion(6, "test question3", "answer3", user2);
        this.addQuestion = new SecurityQuestion(10, "test question 10", "answer", user2);
        this.updateQuestion1 = new SecurityQuestion(7, "update1", "answer", user1);
        this.updateQuestion2 = new SecurityQuestion(8, "update2", "answer", user1);
        this.updateQuestion3 = new SecurityQuestion(9, "update3", "answer", user1);
        this.optionalQuestion = Optional.of(testQuestion1);

        // set lists
        this.testQuestionList = Arrays.asList(testQuestion1, testQuestion2, testQuestion3, testQuestion4, testQuestion5, testQuestion6);
        this.user1Questions = Arrays.asList(testQuestion1, testQuestion2, testQuestion3);
        this.user2Questions = Arrays.asList(testQuestion4, testQuestion5, testQuestion6);
        this.updatedList = Arrays.asList(testQuestion1, testQuestion2, testQuestion3, updateQuestion1, updateQuestion2, updateQuestion3);
    }


    /*------List<SampleQuestions3> listQuestions() Tests------*/

    //search for question by question and answer
    @Test
    public void SecurityQuestionByAnswerQuestion_found(){
        try{
            when(securityQuestionService.findByQuestion(question, answer)).thenReturn(optionalQuestion);

            Assertions.assertTrue(optionalQuestion.isPresent());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //optional should not be found test
    @Test
    public void SecurityQuestionByAnswerQuestion_notFound(){
        try{
            when(securityQuestionService.findByQuestion(question, "no")).thenReturn(optionalQuestionNotFound);

            Assertions.assertFalse(optionalQuestionNotFound.isEmpty());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //save valid security question
    @Test
    public void SecurityQuestionAddValid(){
        try{
            when(securityQuestionService.addSecurityQuestion(addQuestion)).thenReturn(addQuestion);

            Assertions.assertEquals(addQuestion, securityQuestionService.addSecurityQuestion(addQuestion));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //find questions by credentials
    @Test
    public void SecurityQuestionsByCredentials(){
        try{
            when(securityQuestionService.findByCredentials(optionalUser)).thenReturn(user1Questions);

            Assertions.assertEquals(user1Questions, securityQuestionService.findByCredentials(optionalUser));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //delete by user
    @Test
    public void DeleteSecurityQuestionsByUser(){
        try{
            doNothing().when(securityQuestionService).remove(user1);

            securityQuestionService.remove(user1);

            verify(securityQuestionRepository, times(1)).deleteByUser(user1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
