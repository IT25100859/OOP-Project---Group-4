package com.users.userprofile.controller;

import com.users.userprofile.bean.Customer;
import com.users.userprofile.bean.User;
import com.users.userprofile.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //  LOGIN

   //Show the login form
    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttrs) {

        User user = userService.login(username, password);

        if (user == null) {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Invalid username or password. Please try again.");
            return "redirect:/user/login";
        }

        // Store user in HTTP session so other pages know who is logged in
        session.setAttribute("loggedInUser", user);
        session.setAttribute("userId",   user.getUserId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role",     user.getRole());

        // Redirect based on role
        if (user.isAdmin()) {
            return "redirect:/showtime/scheduler"; // Admin lands on scheduler
        } else {
            return "redirect:/showtime/schedule";  // Customer lands on daily schedule
        }
    }


    //LOGOUT

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate();
        redirectAttrs.addFlashAttribute("successMsg", "You have been logged out.");
        return "redirect:/user/login";
    }

    // REGISTER

    //Show the registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        // Pre-defined security questions for the dropdown
        model.addAttribute("securityQuestions", getSecurityQuestions());
        return "user/register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Customer customer,
                                  RedirectAttributes redirectAttrs) {

        boolean success = userService.registerCustomer(customer);

        if (success) {
            redirectAttrs.addFlashAttribute("successMsg",
                    "Account created successfully! Please log in.");
            return "redirect:/user/login";
        } else {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Username or email is already taken. Please try again.");
            return "redirect:/user/register";
        }
    }

    // Reading the profile

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model,
                              RedirectAttributes redirectAttrs) {

        User loggedIn = (User) session.getAttribute("loggedInUser");
        if (loggedIn == null) {
            return "redirect:/user/login"; // not logged in
        }

        // Refresh from file to get latest data
        User freshUser = userService.findById(loggedIn.getUserId());
        model.addAttribute("user", freshUser);
        model.addAttribute("pageTitle", "My Profile");
        return "user/profile";
    }

    //  UPDATE PROFILE

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String userId,
                                @RequestParam String fullName,
                                @RequestParam String phone,
                                @RequestParam String email,
                                HttpSession session,
                                RedirectAttributes redirectAttrs) {

        User user = userService.findById(userId);
        if (user == null) return "redirect:/user/login";

        user.setFullName(fullName);
        user.setPhone(phone);
        user.setEmail(email);

        boolean success = userService.updateProfile(user);

        if (success) {
            // Update session with new details
            session.setAttribute("loggedInUser", user);
            redirectAttrs.addFlashAttribute("successMsg", "Profile updated successfully!");
        } else {
            redirectAttrs.addFlashAttribute("errorMsg", "Update failed. Please try again.");
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/profile/password")
    public String changePassword(@RequestParam String userId,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttrs) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "New passwords do not match!");
            return "redirect:/user/profile";
        }

        boolean success = userService.changePassword(userId, oldPassword, newPassword);

        if (success) {
            redirectAttrs.addFlashAttribute("successMsg",
                    "Password changed successfully!");
        } else {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Current password is incorrect!");
        }
        return "redirect:/user/profile";
    }


    // DELETE / DEACTIVATE

    @GetMapping("/deactivate")
    public String deactivateAccount(HttpSession session,
                                    RedirectAttributes redirectAttrs) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/user/login";

        userService.deactivateUser(user.getUserId());
        session.invalidate();

        redirectAttrs.addFlashAttribute("successMsg",
                "Your account has been deactivated.");
        return "redirect:/user/login";
    }

    //              FORGOT PASSWORD  (3-step flow)

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "user/forgot-password";
    }


    @PostMapping("/forgot-password")
    public String fetchSecurityQuestion(@RequestParam String username,
                                        Model model) {

        String question = userService.getSecurityQuestion(username);

        if (question == null) {
            model.addAttribute("errorMsg", "Username not found.");
            return "user/forgot-password";
        }

        // Pass the question and username to the JSP for step 2
        model.addAttribute("username", username);
        model.addAttribute("securityQuestion", question);
        return "user/forgot-password"; // same JSP, different section visible
    }


    @PostMapping("/verify-answer")
    public String verifyAnswer(@RequestParam String username,
                               @RequestParam String securityAnswer,
                               Model model) {

        boolean correct = userService.verifySecurityAnswer(username, securityAnswer);

        if (!correct) {
            // Re-fetch question to show it again
            String question = userService.getSecurityQuestion(username);
            model.addAttribute("username", username);
            model.addAttribute("securityQuestion", question);
            model.addAttribute("errorMsg", "Incorrect answer. Please try again.");
            return "user/forgot-password";
        }

        // Answer is correct – show new password form
        model.addAttribute("username", username);
        model.addAttribute("showResetForm", true);
        return "user/forgot-password";
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                RedirectAttributes redirectAttrs) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Passwords do not match!");
            return "redirect:/user/forgot-password";
        }

        boolean success = userService.resetPassword(username, newPassword);

        if (success) {
            redirectAttrs.addFlashAttribute("successMsg",
                    "Password reset successfully! Please log in with your new password.");
        } else {
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Password reset failed. Please try again.");
        }
        return "redirect:/user/login";
    }


    // HELPER


    //Pre-defined list of security questions shown in the register form
    private String[] getSecurityQuestions() {
        return new String[]{
            "What is the name of your first pet?",
            "What is your mother's maiden name?",
            "What was the name of your first school?",
            "What is your favourite movie?",
            "What city were you born in?"
        };
    }
}
