package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class SecurityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    private String answer;
    @ManyToOne
    private User user;

}
