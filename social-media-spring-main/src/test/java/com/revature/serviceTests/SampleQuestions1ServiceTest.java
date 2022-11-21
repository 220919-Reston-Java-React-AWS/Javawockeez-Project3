package com.revature.serviceTests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.models.SampleQuestions1;
import com.revature.repositories.SampleQuestions1Repository;
import com.revature.services.SampleQuestions1Service;



@SpringBootTest
public class SampleQuestions1ServiceTest {

    @Mock
    SampleQuestions1Repository s1;

    @InjectMocks
    SampleQuestions1Service s1Service;

    SampleQuestions1 question1;

    SampleQuestions1 question2;

    List<SampleQuestions1> allQuestions;
    
    @BeforeEach
    public void initTests(){
        this.s1Service = new SampleQuestions1Service(this.s1);
        this.question1 = new SampleQuestions1(1, "Am I the first test question??");
        this.allQuestions = Arrays.asList(this.question1, this.question2);
    }

    @Test
    public void listAllQuestions(){
        System.out.println("Test 1");
        
        when(this.s1Service.listQuestions()).thenReturn( allQuestions );

        Assertions.assertEquals( allQuestions, s1Service.listQuestions() );
    }
}
