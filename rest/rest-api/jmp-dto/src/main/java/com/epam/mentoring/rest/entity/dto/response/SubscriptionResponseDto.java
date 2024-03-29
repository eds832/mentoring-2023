package com.epam.mentoring.rest.entity.dto.response;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author Eduard_Sardyka
 */
public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto> {

    private  Long id;

    private Long userId;

    private String startDate;

    public SubscriptionResponseDto(Long id, Long userId, String startDate) {
        this.id = id;
        this.userId = userId;
        this.startDate = startDate;
    }

    public SubscriptionResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}