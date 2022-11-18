package com.revature.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "samples2")
public class SampleQuestions2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
}

