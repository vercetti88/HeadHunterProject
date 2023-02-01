package com.abdulaziz.HeadHunterFinalProject.security;

import com.abdulaziz.HeadHunterFinalProject.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JwtUserContext userContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtils.parseAuthorizationToken(request.getHeader("Authorization"));
            if (token != null && jwtUtils.validateToken(token)) {

                Authentication auth = jwtUtils.getAuthentication(token);

                UserDTO context = (UserDTO) auth.getPrincipal();

                if (!context.getIsActive()) {
                    throw new GeneralSecurityException();
                }

                userContext.setId(context.getId());
                userContext.setEmail(context.getEmail());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
