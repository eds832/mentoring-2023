package com.epam.mentoring.rest.app.converter;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;

import com.epam.mentoring.rest.entity.domain.Subscription;
import com.epam.mentoring.rest.entity.dto.request.SubscriptionRequestDto;

/**
 * @author Eduard_Sardyka
 */
public class SubscriptionRequestDtoToSubscriptionConverter
    implements Converter<SubscriptionRequestDto, Subscription> {

    @Override
    public Subscription convert(SubscriptionRequestDto subscriptionRequestDto) {
        return new Subscription(subscriptionRequestDto.getId(), null, LocalDate.now());
    }

}