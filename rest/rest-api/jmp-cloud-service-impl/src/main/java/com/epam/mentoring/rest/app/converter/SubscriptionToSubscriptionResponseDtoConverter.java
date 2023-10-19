package com.epam.mentoring.rest.app.converter;

import static com.epam.mentoring.rest.app.Application.FORMATTER;

import org.springframework.core.convert.converter.Converter;

import com.epam.mentoring.rest.entity.domain.Subscription;
import com.epam.mentoring.rest.entity.dto.response.SubscriptionResponseDto;

/**
 * @author Eduard_Sardyka
 */
public class SubscriptionToSubscriptionResponseDtoConverter
    implements Converter<Subscription, SubscriptionResponseDto> {

    @Override
    public SubscriptionResponseDto convert(Subscription subscription) {
        return new SubscriptionResponseDto(subscription.getId(), subscription.getUser().getId(),
            subscription.getStartDate() == null ? null : subscription.getStartDate().format(FORMATTER));
    }

}