package com.epam.mentoring.rest.app.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epam.mentoring.rest.controller.UserController;
import com.epam.mentoring.rest.service.UserService;
import com.epam.mentoring.rest.app.controller.requestfailure.UserRequestFailure;
import com.epam.mentoring.rest.entity.domain.User;
import com.epam.mentoring.rest.entity.dto.request.UserRequestDto;
import com.epam.mentoring.rest.entity.dto.response.UserResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Eduard_Sardyka
 */
@RestController
@RequestMapping(path = "/users")
@Api(value = "user-service")
public class UserControllerImpl implements UserController {

    private UserService userService;

    private ConversionService conversionService;

    @Autowired
    public UserControllerImpl(UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = UserResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Create new User.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User created.", response = UserResponseDto.class),
        @ApiResponse(code = 400, message = "User details are not provided."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto.getName() != null && userRequestDto.getSurname() != null
            && userRequestDto.getBirthday() != null) {
            User user = conversionService.convert(userRequestDto, User.class);
            user = userService.createUser(user);
            UserResponseDto dto = conversionService.convert(user, UserResponseDto.class);
            dto = addLinkToDto.apply(dto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
            return ResponseEntity.created(location).body(dto);
        } else {
            return ResponseEntity.badRequest()
                .body(new UserRequestFailure(userRequestDto.getId(), userRequestDto.getName(),
                    userRequestDto.getSurname(), userRequestDto.getBirthday(),
                    "User details are not provided."));
        }
    }

    @Override
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = UserResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Update User.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User updated.", response = UserResponseDto.class),
        @ApiResponse(code = 400, message = "User details are not provided."),
        @ApiResponse(code = 404, message = "User not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<UserResponseDto> updateUser(
        // Path variable "id" was included into arguments of the method following Anastasiya Tolstsik recommendations
        // https://medium.com/@nadinCodeHat/rest-api-naming-conventions-and-best-practices-1c4e781eb6a5
        // https://josipmisko.com/posts/rest-api-best-practices
        @ApiParam("Id of the User to be updated. Cannot be empty.") @PathVariable(value = "id") Long id,
        @RequestBody UserRequestDto userRequestDto) {
        userRequestDto.setId(id);
        if (userRequestDto.getId() != null && userRequestDto.getName() != null && userRequestDto.getSurname() != null
            && userRequestDto.getBirthday() != null) {
            User user = conversionService.convert(userRequestDto, User.class);
            user = userService.updateUser(user);
            if (user != null) {
                return convertUserToResponseEntity(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest()
                .body(new UserRequestFailure(userRequestDto.getId(), userRequestDto.getName(),
                    userRequestDto.getSurname(), userRequestDto.getBirthday(),
                    "User details are not provided."));
        }
    }

    @Override
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = UserResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Deletes a specific user by id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User deleted.", response = UserResponseDto.class),
        @ApiResponse(code = 404, message = "User not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<UserResponseDto> deleteUser(
        @ApiParam("Id of the User to be deleted. Cannot be empty.") @PathVariable(value = "id") Long id) {
        if (id != null) {
            User user = userService.deleteUser(id);
            if (user != null) {
                return convertUserToResponseEntity(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = UserResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Returns a user by id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User Details Retrieved.", response = UserResponseDto.class),
        @ApiResponse(code = 404, message = "User not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<UserResponseDto> getUser(
        @ApiParam("Id of the User to be deleted. Cannot be empty.") @PathVariable(value = "id") Long id) {
        if (id != null) {
            User user = userService.getUser(id);
            if (user != null) {
                return convertUserToResponseEntity(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = List.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Returns list of all users.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Users Details Retrieved.", response = List.class),
        @ApiResponse(code = 204, message = "No Users Found.") })
    public @ResponseBody ResponseEntity<List<UserResponseDto>> getAllUser() {
        ResponseEntity<List<UserResponseDto>> responseEntity;
        List<User> users = userService.getAllUser();
        if (users != null && !users.isEmpty()) {
            List<UserResponseDto> userDtos = users.stream()
                .map(us -> conversionService.convert(us, UserResponseDto.class))
                .map(addLinkToDto).collect(Collectors.toList());
            responseEntity = ResponseEntity.ok(userDtos);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    private ResponseEntity<UserResponseDto> convertUserToResponseEntity(User user) {
        UserResponseDto dto = conversionService.convert(user, UserResponseDto.class);
        dto = addLinkToDto.apply(dto);
        return ResponseEntity.ok(dto);
    }

    private UnaryOperator<UserResponseDto> addLinkToDto = dto -> {
        Link selfLink = linkTo(UserControllerImpl.class).slash(dto.getId()).withSelfRel();
        dto.add(selfLink);
        return dto;
    };

}