package com.epam.mentoring.rest.service;

import java.util.List;

import com.epam.mentoring.rest.entity.domain.User;

/**
 * @author Eduard_Sardyka
 */
public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);

    User getUser(Long id);

    List<User> getAllUser();

}