package com.revature.services;


import com.revature.exceptions.InvalidInputException;
import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.repositories.SecurityQuestionRepository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityQuestionService {

    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    public SecurityQuestionService(SecurityQuestionRepository securityQuestionRepository, UserService userService){
        this.securityQuestionRepository = securityQuestionRepository;
    }

    public SecurityQuestion findbySecurityQuestion(SecurityQuestion check){
        System.out.println("made it to service");
        return securityQuestionRepository.findBySecurityQuestion(check);
    }

    public SecurityQuestion addSecurityQuestion(SecurityQuestion secure){
        return securityQuestionRepository.save(secure);
    }

    public List<SecurityQuestion> findByCredentials(User user){
        return this.securityQuestionRepository.findByUser(user);
    }

    public List<SecurityQuestion> findByEmail(User email){
        return this.securityQuestionRepository.findByUser(email);
    }

    @Transactional
    public void remove(User user){
        this.securityQuestionRepository.deleteByUser(user);
        return;
    }
}
