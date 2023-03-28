package com.epam.mentoring.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.mentoring.auth.model.UserEntity;
import com.epam.mentoring.auth.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * @author Eduard_Sardyka
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}