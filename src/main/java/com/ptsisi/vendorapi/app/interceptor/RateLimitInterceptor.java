package com.ptsisi.vendorapi.app.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptsisi.vendorapi.common.dto.ApiResponse;
import com.ptsisi.vendorapi.common.dto.ErrorDto;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(RateLimitInterceptor.class);

  @Value("${app.rate-limit.max-requests}")
  private static int MAX_REQUESTS;
  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
  private final ObjectMapper mapper;

  private Bucket createNewBucket() {
    // initial capacity = 100, refill 100 tokens every minute
    Bandwidth limit = Bandwidth.classic(100,
        Refill.intervally(100, Duration.ofMinutes(1)));
    return Bucket.builder()
        .addLimit(limit)
        .build();
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication != null ? authentication.getName() : "anonymous";

    Bucket bucket = buckets.computeIfAbsent(username, k -> createNewBucket());
    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

    log.info("Rate limit check for user: {}", username);

    if (!probe.isConsumed()) {
      log.warn("Rate limit exceeded for user: {}", username);
      res.setStatus(429);
      res.setContentType(MediaType.APPLICATION_JSON_VALUE);
      res.setHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(Duration.ofSeconds(30).getSeconds()));

      ApiResponse<ErrorDto> payload = ApiResponse.fail(ErrorDto.of("too many requests"));
      mapper.writeValue(res.getWriter(), payload);
      return false;
    }

    log.info("Request allowed for user: {}. Remaining tokens: {}", username, probe.getRemainingTokens());
    res.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
    return true;
  }

}
