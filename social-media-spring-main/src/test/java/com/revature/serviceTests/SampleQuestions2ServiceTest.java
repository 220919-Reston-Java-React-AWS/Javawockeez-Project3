package com.revature.serviceTests;

import com.revature.models.SampleQuestions2;
import com.revature.repositories.SampleQuestions2Repository;

import com.revature.services.SampleQuestions2Service;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SampleQuestions2ServiceTest {
    @Mock
    SampleQuestions2Repository sampleQuestions2Repository;
    @InjectMocks
    SampleQuestions2Service sampleQuestions2Service;

    /*-------Arranged Values for Tests------*/
    SampleQuestions2 testQuestion1;
    SampleQuestions2 testQuestion2;
    List<SampleQuestions2> testQuestionList;

    @BeforeEach
    void initTestValues(){
        // set question
        this.testQuestion1 = new SampleQuestions2(1, "test question");
        this.testQuestion2 = new SampleQuestions2(2, "test question 2");
        this.testQuestionList = Arrays.asList(testQuestion1, testQuestion2);
    }


    /*------List<SampleQuestions3> listQuestions() Tests------*/
    @Test
    public void listQuestion_questionFound(){
        try{
            when(sampleQuestions2Service.listQuestions()).thenReturn(testQuestionList);

            Assertions.assertEquals(testQuestionList, sampleQuestions2Service.listQuestions());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
