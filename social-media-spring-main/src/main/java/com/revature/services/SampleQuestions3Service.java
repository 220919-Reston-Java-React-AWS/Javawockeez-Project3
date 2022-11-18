package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.SampleQuestions3;
import com.revature.repositories.SampleQuestions3Repository;

@Service
public class SampleQuestions3Service {

    @Autowired
    SampleQuestions3Repository sampleQuestions3Repository;

    public SampleQuestions3Service(SampleQuestions3Repository sampleQuestions3Repository){
        this.sampleQuestions3Repository = sampleQuestions3Repository;
    }

    public List<SampleQuestions3> listQuestions(){
        return this.sampleQuestions3Repository.findAll();
    }
    
}
