package com.epam.mentoring.customconfig.repository;


import org.springframework.data.repository.CrudRepository;

import com.epam.mentoring.customconfig.domain.User;

/**
 * @author Eduard_Sardyka
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
