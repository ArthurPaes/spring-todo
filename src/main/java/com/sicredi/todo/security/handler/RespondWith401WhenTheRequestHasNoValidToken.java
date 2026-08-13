package com.sicredi.todo.security.handler;

import com.sicredi.todo.exception.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import tools.jackson.databind.ObjectMapper;

public class RespondWith401WhenTheRequestHasNoValidToken implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapperThatTurnsJavaObjectsIntoJson;

    public RespondWith401WhenTheRequestHasNoValidToken(ObjectMapper objectMapperThatTurnsJavaObjectsIntoJson) {
        this.objectMapperThatTurnsJavaObjectsIntoJson = objectMapperThatTurnsJavaObjectsIntoJson;
    }

    @Override
    public void commence(
            HttpServletRequest requestThatWasRejected,
            HttpServletResponse responseWeAreAboutToSend,
            AuthenticationException reasonSpringSecurityRejectedTheRequest) throws IOException {

        ApiError bodyExplainingWhyTheRequestWasRejected = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication required: send a valid 'Authorization: Bearer <token>' header",
                Instant.now());

        responseWeAreAboutToSend.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseWeAreAboutToSend.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapperThatTurnsJavaObjectsIntoJson.writeValue(
                responseWeAreAboutToSend.getOutputStream(),
                bodyExplainingWhyTheRequestWasRejected);
    }
}
