package com.andersonmarques.youtubenotes.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andersonmarques.youtubenotes.services.JwtService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public AuthorizationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Optional<Authentication> authentication = jwtService.getAuthenticationFromRequest(request);
        authentication.ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        chain.doFilter(request, response);
    }
}
