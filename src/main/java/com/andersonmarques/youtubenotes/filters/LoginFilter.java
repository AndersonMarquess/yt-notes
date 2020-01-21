package com.andersonmarques.youtubenotes.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersonmarques.youtubenotes.models.AccountCredentials;
import com.andersonmarques.youtubenotes.services.JwtService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private JwtService jwtService;

    public LoginFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super("/login");
        this.jwtService = jwtService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials = extractCredentials(request);
        return getAuthenticationManager().authenticate(credentials.generateUsernamePasswordAuthToken());
    }

    private AccountCredentials extractCredentials(HttpServletRequest request)
            throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        this.jwtService.addAuthenticationToResponse(response, authResult.getName());
    }
}
