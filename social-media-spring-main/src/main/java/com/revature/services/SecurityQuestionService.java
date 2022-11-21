package com.revature.services;


import com.revature.exceptions.InvalidInputException;
import com.revature.models.SecurityQuestion;
import com.revature.models.User;
import com.revature.repositories.SecurityQuestionRepository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityQuestionService {

    @Autowired
    private SecurityQuestionRepository securityQuestionRepository;

    public SecurityQuestionService(SecurityQuestionRepository securityQuestionRepository){
        this.securityQuestionRepository = securityQuestionRepository;
    }

    public Optional<SecurityQuestion> findByQuestion(String question, String answer){
        return securityQuestionRepository.findByQuestionAndAnswer(question, answer);
    }

    public SecurityQuestion addSecurityQuestion(SecurityQuestion secure){
        return securityQuestionRepository.save(secure);
    }

    public List<SecurityQuestion> findByCredentials(Optional<User> user){
        return this.securityQuestionRepository.findByUser(user);
    }

    @Transactional
    public void remove(User user){
        this.securityQuestionRepository.deleteByUser(user);
        return;
    }
}
