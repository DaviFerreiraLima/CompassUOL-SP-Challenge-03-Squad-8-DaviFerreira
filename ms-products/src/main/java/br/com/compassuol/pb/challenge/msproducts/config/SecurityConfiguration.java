package br.com.compassuol.pb.challenge.msproducts.config;


import br.com.compassuol.pb.challenge.msproducts.security.JwtAuthenticationEntryPoint;
import br.com.compassuol.pb.challenge.msproducts.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private  JwtAuthenticationEntryPoint authenticationEntryPoint;

    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService =userDetailsService;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setExposedHeaders(Arrays.asList("Authorization"));
                            config.setMaxAge(3600L);
                            return config;
                        }
                    })).csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests((authorize) ->
                            authorize.anyRequest().authenticated()
                    ).exceptionHandling(exception ->
                            exception.authenticationEntryPoint(authenticationEntryPoint))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    );
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
