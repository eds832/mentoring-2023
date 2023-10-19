package com.epam.mentoring.rest.service;

import java.util.List;

import com.epam.mentoring.rest.entity.domain.Subscription;

/**
 * @author Eduard_Sardyka
 */
public interface SubscriptionService {

    Subscription createSubscription(Subscription subscription);

    Subscription updateSubscription(Subscription subscription);

    Subscription deleteSubscription(Long id);

    Subscription getSubscription(Long id);

    List<Subscription> getAllSubscription();

}