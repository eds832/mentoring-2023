package com.epam.mentoring.rest.entity.dto.response;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author Eduard_Sardyka
 */
public class UserResponseDto extends RepresentationModel<UserResponseDto> {

    private Long id;

    private String name;

    private String surname;

    private String birthday;

    public UserResponseDto(Long id, String name, String surname, String birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}