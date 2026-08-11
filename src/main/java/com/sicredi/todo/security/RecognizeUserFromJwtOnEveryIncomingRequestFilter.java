package com.sicredi.todo.security;

import com.sicredi.todo.entity.User;
import com.sicredi.todo.repository.UserRepository;
import com.sicredi.todo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class RecognizeUserFromJwtOnEveryIncomingRequestFilter extends OncePerRequestFilter {

    private static final String NAME_OF_THE_HEADER_THAT_CARRIES_THE_TOKEN = "Authorization";
    private static final String TEXT_THAT_MUST_APPEAR_BEFORE_THE_TOKEN = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RecognizeUserFromJwtOnEveryIncomingRequestFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest incomingRequest,
            HttpServletResponse outgoingResponse,
            FilterChain everythingThatShouldRunAfterThisFilter) throws ServletException, IOException {

        String tokenSentByTheClient = readTokenOutOfTheAuthorizationHeaderOrReturnNull(incomingRequest);

        if (tokenSentByTheClient != null) {
            rememberWhoIsMakingThisRequestIfTheTokenIsGenuine(tokenSentByTheClient);
        }

        everythingThatShouldRunAfterThisFilter.doFilter(incomingRequest, outgoingResponse);
    }

    private String readTokenOutOfTheAuthorizationHeaderOrReturnNull(HttpServletRequest incomingRequest) {

        String wholeAuthorizationHeaderValue = incomingRequest.getHeader(NAME_OF_THE_HEADER_THAT_CARRIES_THE_TOKEN);

        boolean clientSentNoTokenAtAll = wholeAuthorizationHeaderValue == null
                || !wholeAuthorizationHeaderValue.startsWith(TEXT_THAT_MUST_APPEAR_BEFORE_THE_TOKEN);

        if (clientSentNoTokenAtAll) {
            return null;
        }

        return wholeAuthorizationHeaderValue.substring(TEXT_THAT_MUST_APPEAR_BEFORE_THE_TOKEN.length());
    }

    private void rememberWhoIsMakingThisRequestIfTheTokenIsGenuine(String tokenSentByTheClient) {

        boolean tokenWasReallySignedByUsAndHasNotExpired = jwtService.isTokenValid(tokenSentByTheClient);

        if (!tokenWasReallySignedByUsAndHasNotExpired) {
            return;
        }

        String emailWeOurselvesPutInsideTheTokenAtLoginTime = jwtService.extractEmail(tokenSentByTheClient);

        userRepository.findByEmail(emailWeOurselvesPutInsideTheTokenAtLoginTime)
                .ifPresent(this::markThisUserAsTheOneMakingTheCurrentRequest);
    }

    private void markThisUserAsTheOneMakingTheCurrentRequest(User userTheTokenBelongsTo) {

        UsernamePasswordAuthenticationToken proofThatThisRequestBelongsToThisUser =
                new UsernamePasswordAuthenticationToken(userTheTokenBelongsTo, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(proofThatThisRequestBelongsToThisUser);
    }
}
