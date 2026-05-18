package com.CompleteProject.completeproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

//concept Enacapsulation is practised here through the automatically created getters and setters

//Inheritance : Admin and Customer are child classes of User class

//Abstraction concepts - User is an abstract class
public abstract class User {


    private String userId;

    private String username;

    private String email;

    private String password;

    private String fullName;

    private String phone;

    private String role;

    private String securityQuestion;

    private String securityAnswer;

    private String status;

    //abstract method
    public abstract String getAccountType();

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }


    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    protected String baseToFileString() {
        return String.join("|",
                userId,
                username,
                email,
                password,
                fullName,
                phone,
                role,
                securityQuestion,
                securityAnswer,
                status
        );
    }
}
