package com.revature.repositories;

import com.revature.models.SecurityQuestion;
import com.revature.models.User;

import org.hibernate.annotations.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Integer> {

    List<SecurityQuestion> findByUser(User user);

    void deleteByUser(User user);

    SecurityQuestion findBySecurityQuestion(SecurityQuestion check);

}
