package br.com.compassuol.pb.challenge.apigateway.filter;

import br.com.compassuol.pb.challenge.apigateway.exception.AccessDeniedException;
import br.com.compassuol.pb.challenge.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private RouteValidator validator;


    private JwtUtil jwtUtil;


    public AuthenticationFilter(RouteValidator validator, RestTemplate template, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator=validator;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AccessDeniedException(HttpStatus.UNAUTHORIZED,"missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {

                    jwtUtil.validateToken(authHeader);

                    request =exchange.getRequest()
                            .mutate()
                            .header("username", jwtUtil.getUsername(authHeader))
                            .build();
                    jwtUtil.getUsername(authHeader);
                } catch (Exception e) {
                    throw new AccessDeniedException(HttpStatus.UNAUTHORIZED,"un authorized access to application");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config{

    }

}
