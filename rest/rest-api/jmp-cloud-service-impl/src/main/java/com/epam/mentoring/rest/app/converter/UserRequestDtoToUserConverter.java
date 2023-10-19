package com.epam.mentoring.rest.app.converter;

import static com.epam.mentoring.rest.app.Application.FORMATTER;

import org.springframework.core.convert.converter.Converter;

import com.epam.mentoring.rest.entity.domain.User;
import com.epam.mentoring.rest.entity.dto.request.UserRequestDto;

import java.time.LocalDate;

/**
 * @author Eduard_Sardyka
 */
public class UserRequestDtoToUserConverter implements Converter<UserRequestDto, User> {

    @Override
    public User convert(UserRequestDto userRequestDto) {
        return new User(userRequestDto.getId(), userRequestDto.getName(), userRequestDto.getSurname(),
            userRequestDto.getBirthday() == null ? null : LocalDate.parse(userRequestDto.getBirthday(), FORMATTER));
    }

}