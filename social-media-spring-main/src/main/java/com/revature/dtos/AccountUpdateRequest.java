package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequest {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
