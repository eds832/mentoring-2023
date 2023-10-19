package com.epam.mentoring.rest.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import com.epam.mentoring.rest.app.converter.SubscriptionRequestDtoToSubscriptionConverter;
import com.epam.mentoring.rest.app.converter.SubscriptionToSubscriptionResponseDtoConverter;
import com.epam.mentoring.rest.app.converter.UserRequestDtoToUserConverter;
import com.epam.mentoring.rest.app.converter.UserToUserResponseDtoConverter;

/**
 * @author Eduard_Sardyka
 */
@Configuration
public class Config {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new SubscriptionToSubscriptionResponseDtoConverter());
        service.addConverter(new SubscriptionRequestDtoToSubscriptionConverter());
        service.addConverter(new UserRequestDtoToUserConverter());
        service.addConverter(new UserToUserResponseDtoConverter());
        return service;
    }

}