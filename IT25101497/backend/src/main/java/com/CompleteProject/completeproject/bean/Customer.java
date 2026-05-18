package com.CompleteProject.completeproject.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Customer extends User {


    private int totalBookings;

    public Customer(String userId, String username, String email,
                    String password, String fullName, String phone,
                    String securityQuestion, String securityAnswer,
                    String status, int totalBookings) {


        super(userId, username, email, password, fullName, phone,
              "CUSTOMER", securityQuestion, securityAnswer, status);

        this.totalBookings = totalBookings;
    }

    @Override
    public String getAccountType() {
        return "Customer Account";
    }

    public String toFileString() {
        return baseToFileString() + "|" + totalBookings;
    }

    public static Customer fromFileString(String line) {
        String[] p = line.split("\\|");
        return new Customer(
                p[0],                        // userId
                p[1],                        // username
                p[2],                        // email
                p[3],                        // password
                p[4],                        // fullName
                p[5],                        // phone
                p[7],                        // securityQuestion
                p[8],                        // securityAnswer
                p[9],                        // status
                p.length > 10 ? Integer.parseInt(p[10]) : 0  // totalBookings
        );
    }
}
