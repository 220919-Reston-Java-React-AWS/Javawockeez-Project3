package com.revature.serviceTests;

import com.revature.models.SampleQuestions1;
import com.revature.repositories.SampleQuestions1Repository;

import com.revature.services.SampleQuestions1Service;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SampleQuestions1ServiceTest {
    @Mock
    SampleQuestions1Repository sampleQuestions1Repository;
    @InjectMocks
    SampleQuestions1Service sampleQuestions1Service;

    /*-------Arranged Values for Tests------*/
    SampleQuestions1 testQuestion1;
    List<SampleQuestions1> testQuestionList;

    @BeforeEach
    void initTestValues(){
        // set question
        this.testQuestion1 = new SampleQuestions1(1, "test question");
        this.testQuestionList = Arrays.asList(testQuestion1);
    }


    /*------List<SampleQuestions1> listQuestions() Tests------*/
    @Test
    public void listQuestion_questionFound(){
        try{
            when(sampleQuestions1Service.listQuestions()).thenReturn(testQuestionList);

            Assertions.assertEquals(testQuestionList, sampleQuestions1Service.listQuestions());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
