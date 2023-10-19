package com.epam.mentoring.rest.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.mentoring.rest.entity.domain.Subscription;
import com.epam.mentoring.rest.app.repository.SubscriptionRepository;
import com.epam.mentoring.rest.service.SubscriptionService;

/**
 * @author Eduard_Sardyka
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        subscription.setId(null);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        if (subscriptionRepository.findById(subscription.getId()).isPresent()) {
            return subscriptionRepository.save(subscription);
        } else {
            return null;
        }
    }

    @Override
    public Subscription deleteSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            Subscription subscription = optionalSubscription.get();
            subscriptionRepository.delete(subscription);
            return subscription;
        } else {
            return null;
        }
    }

    @Override
    public Subscription getSubscription(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            return optionalSubscription.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Subscription> getAllSubscription() {
        return subscriptionRepository.findAll();
    }

}