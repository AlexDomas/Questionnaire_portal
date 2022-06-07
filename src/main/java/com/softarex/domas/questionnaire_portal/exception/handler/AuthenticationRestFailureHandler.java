package com.softarex.domas.questionnaire_portal.exception.handler;

import com.softarex.domas.questionnaire_portal.exception.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationRestFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = HttpStatus.UNAUTHORIZED.getReasonPhrase();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (exception instanceof JwtAuthenticationException) {
            message = exception.getMessage();
        }
        if (exception instanceof BadCredentialsException) {
            message = exception.getMessage();
        }

        response.getOutputStream().println(message);
    }
}
