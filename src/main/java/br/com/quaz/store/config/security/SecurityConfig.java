package br.com.quaz.store.config.security;

import br.com.quaz.store.config.security.filters.JsonObjectAuthenticationFilter;
import br.com.quaz.store.config.security.filters.JwtAuthorizationFilter;
import br.com.quaz.store.config.security.service.UserDetailsServiceImpl;
import br.com.quaz.store.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private static final String ADMIN_TAG = "ADMIN";
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthSuccessHandler authSuccessHandler;
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(final HttpSecurity http) {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(auth -> {
                    try {
                        auth
                                .antMatchers(HttpMethod.POST, "/products").hasRole(ADMIN_TAG)
                                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole(ADMIN_TAG)
                                .antMatchers(HttpMethod.PUT, "/products/**").hasRole(ADMIN_TAG)
                                .antMatchers(HttpMethod.POST, "/category").hasRole(ADMIN_TAG)
                                .antMatchers(HttpMethod.DELETE, "/category/**").hasRole(ADMIN_TAG)
                                .antMatchers(HttpMethod.POST, "/user").permitAll()
                                .antMatchers("/user", "/wish-list", "/wish-list/**", "/purchase", "/paypal/**", "/address", "/address/**").hasRole("USER")
                                .antMatchers(HttpMethod.GET).permitAll()
                                .anyRequest().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService, secret))
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    } catch (final Exception e) {
                        throw new UnauthorizedException("Unauthorized");
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
