package com.revature.serviceTests;

import com.revature.models.SampleQuestions3;
import com.revature.repositories.SampleQuestions3Repository;

import com.revature.services.SampleQuestions3Service;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SampleQuestions3ServiceTest {
    @Mock
    SampleQuestions3Repository sampleQuestions3Repository;
    @InjectMocks
    SampleQuestions3Service sampleQuestions3Service;

    /*-------Arranged Values for Tests------*/
    SampleQuestions3 testQuestion1;
    SampleQuestions3 testQuestion2;
    List<SampleQuestions3> testQuestionList;

    @BeforeEach
    void initTestValues(){
        // set question
        this.testQuestion1 = new SampleQuestions3(1, "test question");
        this.testQuestion2 = new SampleQuestions3(2, "test question 2");
        this.testQuestionList = Arrays.asList(testQuestion1, testQuestion2);
    }


    /*------List<SampleQuestions3> listQuestions() Tests------*/
    @Test
    public void listQuestion_questionFound(){
        try{
            when(sampleQuestions3Service.listQuestions()).thenReturn(testQuestionList);

            Assertions.assertEquals(testQuestionList, sampleQuestions3Service.listQuestions());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}