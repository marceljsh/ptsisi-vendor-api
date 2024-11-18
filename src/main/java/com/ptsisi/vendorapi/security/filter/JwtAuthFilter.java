package com.ptsisi.vendorapi.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import com.ptsisi.vendorapi.security.service.JwtService;
import com.ptsisi.vendorapi.security.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

  private final JwtService jwtService;
  private final TokenBlacklistService tokenService;
  private final UserDetailsService userDetailsService;
  private final ObjectMapper mapper;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain chain) throws ServletException, IOException {

    log.info("Processing request: {}", req.getRequestURI());
    String authHeader = req.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.info("No token found in request");
      chain.doFilter(req, res);
      return;
    }

    String token = authHeader.substring(7);
    if (tokenService.isBlacklisted(token)) {
      log.info("Token is blacklisted");
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.setContentType(MediaType.APPLICATION_JSON_VALUE);

      ApiResponse<ErrorDto> response = ApiResponse.fail(ErrorDto.of("token is expired"));
      mapper.writeValue(res.getWriter(), response);
      return;
    }

    String username = jwtService.extractUsername(token);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      log.info("Authenticating user: {}", username);
      UserDetails user = userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(token, user)) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
            user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    chain.doFilter(req, res);
  }

}
