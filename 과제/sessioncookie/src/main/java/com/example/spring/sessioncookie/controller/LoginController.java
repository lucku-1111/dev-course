package com.example.spring.sessioncookie.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin(
            HttpSession session
    ) {
        if (session.getAttribute("username") != null) return "redirect:/dashboard";
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(
            @RequestParam String username,
            HttpSession session
    ) {
        session.setAttribute("username", username);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String getLogout(
            HttpSession session
    ) {
        session.invalidate();
        return "redirect:/login";
    }
}
