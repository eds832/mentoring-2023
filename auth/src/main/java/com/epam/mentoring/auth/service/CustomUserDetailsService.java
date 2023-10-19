package com.epam.mentoring.auth.service;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.mentoring.auth.model.UserEntity;
import com.epam.mentoring.auth.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("UserEntity not found!");
        } else {
            if(loginAttemptService.isBlocked(username)) {
                throw new LockedException("User is blocked.");
            }
        }
        String[] authorities = user.getUserAuthorities().split(";");
        return User.withUsername(user.getUserName()).password(user.getUserPassword()).authorities(authorities).build();
    }
}