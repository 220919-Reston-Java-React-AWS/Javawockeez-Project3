package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.SampleQuestions1;
import com.revature.repositories.SampleQuestions1Repository;

import java.util.List;

@Service
public class SampleQuestions1Service {

    @Autowired
    private SampleQuestions1Repository sampleQuestions1Repository;

    public SampleQuestions1Service(SampleQuestions1Repository sampleQuestions1Repository){
        this.sampleQuestions1Repository = sampleQuestions1Repository;
    }

    public List<SampleQuestions1> listQuestions(){
        return sampleQuestions1Repository.findAll();
    }

}
