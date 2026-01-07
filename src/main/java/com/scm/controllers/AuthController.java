package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	  // verify email

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user == null) {
            session.setAttribute("message", Message.builder()
                    .type(MessageType.red)
                    .content("Invalid or expired verification link.")
                    .build());

            return "redirect:/login";
        }

        if (!token.equals(user.getEmailToken())) {
            session.setAttribute("message", Message.builder()
                    .type(MessageType.red)
                    .content("Invalid verification token.")
                    .build());

            return "redirect:/login";
        }

        // ✅ success
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setEmailToken(null);  // IMPORTANT: one-time use
        userRepo.save(user);

        session.setAttribute("message", Message.builder()
                .type(MessageType.green)
                .content("Email verified successfully. Please login.")
                .build());

        return "redirect:/login";
    }

}
