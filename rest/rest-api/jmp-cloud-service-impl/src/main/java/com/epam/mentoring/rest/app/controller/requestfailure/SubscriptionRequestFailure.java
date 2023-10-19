package com.epam.mentoring.rest.app.controller.requestfailure;

import com.epam.mentoring.rest.entity.dto.response.SubscriptionResponseDto;

/**
 * @author Eduard_Sardyka
 */
public class SubscriptionRequestFailure extends SubscriptionResponseDto {

    private String errorMessage;

    public SubscriptionRequestFailure(Long id, Long userId, String startDate, String errorMessage) {
        super(id, userId, startDate);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}