package com.epam.mentoring.rest.app.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.epam.mentoring.rest.controller.ServiceController;
import com.epam.mentoring.rest.service.UserService;
import com.epam.mentoring.rest.app.controller.requestfailure.SubscriptionRequestFailure;
import com.epam.mentoring.rest.entity.domain.Subscription;
import com.epam.mentoring.rest.entity.domain.User;
import com.epam.mentoring.rest.entity.dto.request.SubscriptionRequestDto;
import com.epam.mentoring.rest.entity.dto.response.SubscriptionResponseDto;
import com.epam.mentoring.rest.service.SubscriptionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Eduard_Sardyka
 */
@RestController
@RequestMapping(path = "/subscriptions")
@Api(value = "subscription-service")
public class ServiceControllerImpl implements ServiceController {

    private ConversionService conversionService;

    private SubscriptionService subscriptionService;

    private UserService userService;

    @Autowired
    public ServiceControllerImpl(ConversionService conversionService, SubscriptionService subscriptionService,
                                 UserService userService) {
        this.conversionService = conversionService;
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = SubscriptionResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Create new Subscription.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Subscription created.", response = SubscriptionResponseDto.class),
        @ApiResponse(code = 400, message = "User Id not provided."),
        @ApiResponse(code = 404, message = "User for subscription not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> createSubscription(
        @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRequestDto.getUserId() != null) {
            Subscription subscription = convertRequestDtoToSubscription(subscriptionRequestDto);
            if (subscription != null) {
                subscription = subscriptionService.createSubscription(subscription);
                SubscriptionResponseDto dto = conversionService.convert(subscription, SubscriptionResponseDto.class);
                dto = addLinksToDto.apply(dto);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(dto.getId()).toUri();
                return ResponseEntity.created(location).body(dto);
            }
        } else {
            return ResponseEntity.badRequest()
                .body(new SubscriptionRequestFailure(subscriptionRequestDto.getId(), subscriptionRequestDto.getUserId(),
                    null, "User Id not provided."));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = SubscriptionResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Update Subscription.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Subscription updated.", response = SubscriptionResponseDto.class),
        @ApiResponse(code = 400, message = "Subscription details are not provided."),
        @ApiResponse(code = 404, message = "Subscription not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> updateSubscription(
        // Path variable "id" was included into arguments of the method following Anastasiya Tolstsik recommendations
        // https://medium.com/@nadinCodeHat/rest-api-naming-conventions-and-best-practices-1c4e781eb6a5
        // https://josipmisko.com/posts/rest-api-best-practices
        @ApiParam("Id of the Subscription to be updated. Cannot be empty.") @PathVariable(value = "id") Long id,
        @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        subscriptionRequestDto.setId(id);
        if (subscriptionRequestDto.getId() != null && subscriptionRequestDto.getUserId() != null) {
            Subscription subscription = convertRequestDtoToSubscription(subscriptionRequestDto);
            if (subscription != null) {
                subscription = subscriptionService.updateSubscription(subscription);
                if (subscription != null) {
                    return convertSubscriptionToResponseEntity(subscription);
                }
            }
        } else {
            return ResponseEntity.badRequest()
                .body(new SubscriptionRequestFailure(subscriptionRequestDto.getId(), subscriptionRequestDto.getUserId(),
                    null,"Subscription details are not provided."));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = SubscriptionResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Deletes a specific subscription by its id.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Subscription deleted.", response = SubscriptionResponseDto.class),
        @ApiResponse(code = 404, message = "Subscription not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> deleteSubscription(
        @ApiParam("Id of the Subscription to be deleted. Cannot be empty.")
        @PathVariable(value = "id") Long id) {
        if (id != null) {
            Subscription subscription = subscriptionService.deleteSubscription(id);
            if (subscription != null) {
                return convertSubscriptionToResponseEntity(subscription);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = SubscriptionResponseDto.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Returns a subscription by its id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Subscription Details Retrieved.", response = SubscriptionResponseDto.class),
        @ApiResponse(code = 404, message = "Subscription not found."),
        @ApiResponse(code = 500, message = "Internal Server Error.") })
    public @ResponseBody ResponseEntity<SubscriptionResponseDto> getSubscription(
        @ApiParam("Id of the Subscription to be obtained. Cannot be empty.") @PathVariable(value = "id") Long id) {
        if (id != null) {
            Subscription subscription = subscriptionService.getSubscription(id);
            if (subscription != null) {
                return convertSubscriptionToResponseEntity(subscription);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(response = List.class, produces = MediaType.APPLICATION_JSON_VALUE, value = "Returns list of all subscriptions.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Subscriptions Details Retrieved.", response = List.class),
        @ApiResponse(code = 204, message = "No Subscriptions Found.") })
    public @ResponseBody ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription() {
        ResponseEntity<List<SubscriptionResponseDto>> responseEntity;
        List<Subscription> subscriptions = subscriptionService.getAllSubscription();
        if (subscriptions != null && !subscriptions.isEmpty()) {
            List<SubscriptionResponseDto> subscriptionDtos = subscriptions.stream()
                .map(sub -> conversionService.convert(sub, SubscriptionResponseDto.class))
                .map(addLinksToDto).collect(Collectors.toList());
            responseEntity = ResponseEntity.ok(subscriptionDtos);
        } else {
            responseEntity = ResponseEntity.noContent().build();
        }
        return responseEntity;
    }

    private ResponseEntity<SubscriptionResponseDto> convertSubscriptionToResponseEntity(Subscription subscription) {
        SubscriptionResponseDto dto = conversionService.convert(subscription, SubscriptionResponseDto.class);
        dto = addLinksToDto.apply(dto);
        return ResponseEntity.ok(dto);
    }

    private UnaryOperator<SubscriptionResponseDto> addLinksToDto = dto -> {
        Link selfLink = linkTo(ServiceControllerImpl.class).slash(dto.getId()).withSelfRel();
        dto.add(selfLink);
        Link userLink = linkTo(methodOn(UserControllerImpl.class).getUser(dto.getUserId())).withRel("user");
        dto.add(userLink);
        return dto;
    };

    private Subscription convertRequestDtoToSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        User user = userService.getUser(subscriptionRequestDto.getUserId());
        if (user != null) {
            Subscription subscription = conversionService.convert(subscriptionRequestDto, Subscription.class);
            subscription.setUser(user);
            return subscription;
        } else {
            return null;
        }
    }

}