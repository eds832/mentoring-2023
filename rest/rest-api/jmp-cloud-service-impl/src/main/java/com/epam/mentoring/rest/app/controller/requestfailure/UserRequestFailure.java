package com.epam.mentoring.rest.app.controller.requestfailure;

import com.epam.mentoring.rest.entity.dto.response.UserResponseDto;

/**
 * @author Eduard_Sardyka
 */
public class UserRequestFailure extends UserResponseDto {

    private String errorMessage;

    public UserRequestFailure(Long id, String name, String surname, String birthday, String errorMessage) {
        super(id, name, surname, birthday);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}