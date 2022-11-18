package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsRequest {
    private String email;
    private String question1;
    private String answer1;
    private String question2;
    private String answer2;
    private String question3;
    private String answer3;
    private String password;
}
