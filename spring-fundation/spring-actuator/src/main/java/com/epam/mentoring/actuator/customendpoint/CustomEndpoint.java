package com.epam.mentoring.actuator.customendpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Eduard_Sardyka
 */
@Component
@Endpoint(id="custom-endpoint")
public class CustomEndpoint {

    @ReadOperation
    @Bean
    public String sendResponse() {
        return "Custom endpoint response.";
    }

}