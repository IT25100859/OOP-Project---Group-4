package com.users.userprofile.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
