package com.epam.mentoring.auth.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.mentoring.auth.model.UserEntity;
import com.epam.mentoring.auth.service.LoginAttemptService;
import com.epam.mentoring.auth.service.UserService;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Controller
@AllArgsConstructor
public class MainController {

    private UserService userService;

    private LoginAttemptService loginAttemptService;

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login(final ModelMap model, @RequestParam("error") Optional<String> error) {
        error.ifPresent(e -> model.addAttribute("error", e));
        return "login";
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccessPage() {
        return "logout";
    }

    @GetMapping("/blocked")
    public String blocked(Model model) {
        List<UserEntity> userEntities = userService.findAll();
        Map<String, LocalDateTime> blockedUsers = userEntities.stream()
            .map(UserEntity::getUserName).filter(userName -> loginAttemptService.isBlocked(userName))
            .collect(Collectors.toMap(userName -> userName,
                userName -> loginAttemptService.getCachedValue(userName).getBlockedTimestamp()));

        if (!blockedUsers.isEmpty()) {
            model.addAttribute("blockedUsers", blockedUsers);
        }
        return "blocked";
    }

}
