package com.epam.mentoring.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.epam.mentoring.rest.entity.dto.request.SubscriptionRequestDto;
import com.epam.mentoring.rest.entity.dto.response.SubscriptionResponseDto;

/**
 * @author Eduard_Sardyka
 */
public interface ServiceController {

    ResponseEntity<SubscriptionResponseDto> createSubscription(SubscriptionRequestDto subscriptionRequestDto);

    // Path variable "id" was included into arguments of the method following Anastasiya Tolstsik recommendations
    // https://medium.com/@nadinCodeHat/rest-api-naming-conventions-and-best-practices-1c4e781eb6a5
    // https://josipmisko.com/posts/rest-api-best-practices
    ResponseEntity<SubscriptionResponseDto> updateSubscription(Long id, SubscriptionRequestDto subscriptionRequestDto);

    ResponseEntity<SubscriptionResponseDto> deleteSubscription(Long id);

    ResponseEntity<SubscriptionResponseDto> getSubscription(Long id);

    ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription();

}