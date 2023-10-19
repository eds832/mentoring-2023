package com.epam.mentoring.rest.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.epam.mentoring.rest.entity.domain.Subscription;

/**
 * @author Eduard_Sardyka
 */
@RepositoryRestResource(exported = false)
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findAll();

}