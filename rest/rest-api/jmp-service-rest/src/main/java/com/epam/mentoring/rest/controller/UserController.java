package com.epam.mentoring.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.epam.mentoring.rest.entity.dto.request.UserRequestDto;
import com.epam.mentoring.rest.entity.dto.response.UserResponseDto;

/**
 * @author Eduard_Sardyka
 */
public interface UserController {

    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto);

    // Path variable "id" was included into arguments of the method following Anastasiya Tolstsik recommendations
    // https://medium.com/@nadinCodeHat/rest-api-naming-conventions-and-best-practices-1c4e781eb6a5
    // https://josipmisko.com/posts/rest-api-best-practices
    ResponseEntity<UserResponseDto> updateUser(Long id, UserRequestDto userRequestDto);

    ResponseEntity<UserResponseDto> deleteUser(Long id);

    ResponseEntity<UserResponseDto> getUser(Long id);

    ResponseEntity<List<UserResponseDto>> getAllUser();

}