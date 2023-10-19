package com.epam.mentoring.profiles.repository;


import org.springframework.data.repository.CrudRepository;

import com.epam.mentoring.profiles.domain.User;

/**
 * @author Eduard_Sardyka
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
