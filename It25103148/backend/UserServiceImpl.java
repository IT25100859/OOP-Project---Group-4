package com.users.userprofile.service;

import com.users.userprofile.bean.Admin;
import com.users.userprofile.bean.Customer;
import com.users.userprofile.bean.User;
import com.users.userprofile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //  CREATE

    @Override
    public boolean registerCustomer(Customer customer) {
        if (isUsernameTaken(customer.getUsername())) return false;
        if (isEmailTaken(customer.getEmail()))       return false;

        // Auto-generate ID
        customer.setUserId(userRepository.generateNextId("CUSTOMER"));
        customer.setRole("CUSTOMER");
        customer.setStatus("ACTIVE");
        customer.setTotalBookings(0);

        // Normalise security answer to lowercase for case-insensitive matching
        customer.setSecurityAnswer(customer.getSecurityAnswer().toLowerCase().trim());

        userRepository.save(customer);
        return true;
    }


    // READ

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.readAll();
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId);
    }


    //UPDATE

    @Override
    public boolean updateProfile(User user) {
        return userRepository.update(user);
    }


    @Override
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId);
        if (user == null) return false;

        // Verify the old password matches what is stored
        if (!user.getPassword().equals(oldPassword)) return false;

        user.setPassword(newPassword);
        return userRepository.update(user);
    }


    // DELETE

    @Override
    public boolean deactivateUser(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return false;
        user.setStatus("DEACTIVATED");
        return userRepository.update(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        return userRepository.delete(userId);
    }


    // AUTHENTICATION

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null)             return null; // username not found
        if (!user.isActive())         return null; // account deactivated
        if (!user.getPassword().equals(password)) return null; // wrong password
        return user; // all checks passed
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email) != null;
    }


    // FORGOT PASSWORD (3-Step Flow)

    @Override
    public String getSecurityQuestion(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return user.getSecurityQuestion();
    }


    @Override
    public boolean verifySecurityAnswer(String username, String answer) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;
        return user.getSecurityAnswer().equals(answer.toLowerCase().trim());
    }


    @Override
    public boolean resetPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;
        user.setPassword(newPassword);
        return userRepository.update(user);
    }
}
