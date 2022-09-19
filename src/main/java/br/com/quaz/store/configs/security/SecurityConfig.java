package br.com.quaz.store.configs.security;

import br.com.quaz.store.configs.security.filters.JsonObjectAuthenticationFilter;
import br.com.quaz.store.configs.security.filters.JwtAuthorizationFilter;
import br.com.quaz.store.configs.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthSuccessHandler authSuccessHandler;
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth
                                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.POST, "/category").hasRole("ADMIN")
                                .antMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN")
                                .antMatchers(HttpMethod.POST, "/user").permitAll()
                                .antMatchers("/user").hasRole("USER")
                                .antMatchers(HttpMethod.GET).permitAll()
                                .anyRequest().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService, secret))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() {
        final var filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
