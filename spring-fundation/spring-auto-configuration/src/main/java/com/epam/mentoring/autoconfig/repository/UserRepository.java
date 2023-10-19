package com.epam.mentoring.autoconfig.repository;


import org.springframework.data.repository.CrudRepository;

import com.epam.mentoring.autoconfig.domain.User;

/**
 * @author Eduard_Sardyka
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
