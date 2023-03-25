package br.com.quaz.store.config.security;

import br.com.quaz.store.config.security.filters.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String ADMIN_TAG = "ADMIN";
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(final HttpSecurity http) {
        http
                .cors()
                .and()
                .csrf()
                .disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/products").hasRole(ADMIN_TAG)
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole(ADMIN_TAG)
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole(ADMIN_TAG)
                .requestMatchers(HttpMethod.POST, "/category").hasRole(ADMIN_TAG)
                .requestMatchers(HttpMethod.DELETE, "/category/**").hasRole(ADMIN_TAG)
                .requestMatchers(HttpMethod.POST, "/user", "/login").permitAll()
                .requestMatchers("/user", "/wish-list", "/wish-list/**", "/purchase", "/paypal/**", "/address", "/address/**").hasAnyRole("USER", ADMIN_TAG)
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
