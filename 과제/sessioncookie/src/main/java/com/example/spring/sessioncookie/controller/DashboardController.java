package com.example.spring.sessioncookie.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class DashboardController {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/dashboard")
    public String getDashboard(
            HttpSession session,
            @CookieValue(value = "lastVisit", required = false) String lastVisit,
            @CookieValue(value = "theme", defaultValue = "light") String theme,
            HttpServletResponse response,
            Model model
    ) {
        String username = (String) session.getAttribute("username");
        if (username == null || username.isBlank()) return "redirect:/login";
        model.addAttribute("username", username);

        if (lastVisit != null) {
            long millis = Long.parseLong(lastVisit);
            String readable = Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .format(FMT);
            model.addAttribute("lastVisit", readable);
        }

        Cookie visit = new Cookie("lastVisit", String.valueOf(System.currentTimeMillis()));
        visit.setMaxAge(30 * 24 * 3600);
        visit.setPath("/");
        visit.setHttpOnly(true);
        response.addCookie(visit);

        model.addAttribute("theme", theme);

        return "dashboard";
    }

    @GetMapping("/theme")
    public String getTheme(
            @RequestParam String mode,
            HttpServletResponse response
    ) {
        String value = "dark".equals(mode) ? "dark" : "light";
        Cookie theme = new Cookie("theme", value);
        theme.setMaxAge(30 * 24 * 3600);
        theme.setPath("/");
        response.addCookie(theme);

        return "redirect:/dashboard";
    }
}
