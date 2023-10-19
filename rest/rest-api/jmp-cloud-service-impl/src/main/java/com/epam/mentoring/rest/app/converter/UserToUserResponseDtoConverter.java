package com.epam.mentoring.rest.app.converter;

import static com.epam.mentoring.rest.app.Application.FORMATTER;

import org.springframework.core.convert.converter.Converter;

import com.epam.mentoring.rest.entity.domain.User;
import com.epam.mentoring.rest.entity.dto.response.UserResponseDto;

/**
 * @author Eduard_Sardyka
 */
public class UserToUserResponseDtoConverter implements Converter<User, UserResponseDto> {

    @Override
    public UserResponseDto convert(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getSurname(),
            user.getBirthday() == null ? null : user.getBirthday().format(FORMATTER));
    }

}