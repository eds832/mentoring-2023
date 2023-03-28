package com.epam.mentoring.auth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.epam.mentoring.auth.repository.UserRepository;
import com.epam.mentoring.auth.service.LoginAttemptService;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Component
@AllArgsConstructor
public class AuthenticationFailureListener implements
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private LoginAttemptService loginAttemptService;

    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        Object principal = e.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) principal;
            if(userRepository.findByUserName(username) != null) {
                loginAttemptService.loginFailed(username);
            }
        }
    }

}