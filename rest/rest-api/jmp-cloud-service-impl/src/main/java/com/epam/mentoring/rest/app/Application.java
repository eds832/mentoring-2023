package com.epam.mentoring.rest.app;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Eduard_Sardyka
 */
@EnableWebMvc
@EnableSwagger2
@EnableJpaRepositories(basePackageClasses = {
    com.epam.mentoring.rest.app.repository.SubscriptionRepository.class,
    com.epam.mentoring.rest.app.repository.UserRepository.class })
@ComponentScan(basePackageClasses = {
    com.epam.mentoring.rest.app.repository.SubscriptionRepository.class,
    com.epam.mentoring.rest.app.repository.UserRepository.class,
    com.epam.mentoring.rest.app.controller.impl.ServiceControllerImpl.class,
    com.epam.mentoring.rest.app.controller.impl.UserControllerImpl.class,
    com.epam.mentoring.rest.app.configuration.Config.class,
    com.epam.mentoring.rest.app.service.impl.SubscriptionServiceImpl.class,
    com.epam.mentoring.rest.app.service.impl.UserServiceImpl.class, })
@EntityScan(basePackageClasses = {
    com.epam.mentoring.rest.entity.domain.Subscription.class,
    com.epam.mentoring.rest.entity.domain.User.class })
@SpringBootApplication
public class Application {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}