package com.epam.mentoring.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author Eduard_Sardyka
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String USER_IS_BLOCKED_MSG = "User is blocked.";

    private static final String BAD_CREDENTIALS_MSG = "Bad credentials.";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws
        IOException, ServletException {
        setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);

        String errorMessage;

        if (exception.getMessage().equalsIgnoreCase(USER_IS_BLOCKED_MSG)) {
            errorMessage = USER_IS_BLOCKED_MSG;
        } else {
            errorMessage = BAD_CREDENTIALS_MSG;
        }
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}