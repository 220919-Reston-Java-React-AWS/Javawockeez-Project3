package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.SampleQuestions2;
import com.revature.repositories.SampleQuestions2Repository;

@Service
public class SampleQuestions2Service {
    
    @Autowired
    SampleQuestions2Repository sampleQuestions2Repository;

    public SampleQuestions2Service(SampleQuestions2Repository sampleQuestions2Repository){
        this.sampleQuestions2Repository = sampleQuestions2Repository;
    }

    public List<SampleQuestions2> listQuestions(){
        return this.sampleQuestions2Repository.findAll();
    }

}
