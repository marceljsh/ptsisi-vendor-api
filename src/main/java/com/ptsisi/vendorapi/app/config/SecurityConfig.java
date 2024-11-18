package com.ptsisi.vendorapi.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import com.ptsisi.vendorapi.security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
    "/api/v1/auth/sign-up",
    "/api/v1/auth/sign-in",
  };

  private static final String[] DOC_WHITELIST = {
    "/",
    "/api/v1",
    "/api/v1/docs",
  };

  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authProvider;
  private final ObjectMapper mapper;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(AbstractHttpConfigurer::disable)
      .authenticationProvider(authProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .formLogin(withDefaults())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll()
        .requestMatchers(HttpMethod.GET, DOC_WHITELIST).permitAll()
        .anyRequest().authenticated())
      .exceptionHandling(exception -> exception
        .authenticationEntryPoint((req, res, e) -> {
          res.setContentType(MediaType.APPLICATION_JSON_VALUE);
          res.setStatus(HttpStatus.UNAUTHORIZED.value());

          ApiResponse<ErrorDto> payload = ApiResponse.fail(ErrorDto.of(e.getMessage()));
          mapper.writeValue(res.getWriter(), payload);
        }))
      .build();
  }

}
