package com.paranmanzang.gatewayserver.Filter;

import com.paranmanzang.gatewayserver.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtFilter implements WebFilter {

    private final JWTUtil jwtUtil;

    public JwtFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwtToken = resolveToken(exchange.getRequest());

        if (jwtToken != null && !jwtUtil.isExpired(jwtToken)) {
            String nickname = jwtUtil.getNickNameFromToken(jwtToken);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(nickname, null, null);

            // ReactiveSecurityContextHolder에 인증 정보 설정
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                    .doOnSuccess(unused -> {
                        log.info("User '{}' has been authenticated", nickname);
                    })
                    .doOnError(error -> {
                        log.error("Failed to authenticate user '{}': {}", nickname, error.getMessage());
                    });
        }

        // JWT 토큰이 없거나 유효하지 않은 경우 필터 체인 계속 진행
        return chain.filter(exchange);
    }

    // Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    private String resolveToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7).trim(); // Bearer 이후의 토큰만 반환
    }
}