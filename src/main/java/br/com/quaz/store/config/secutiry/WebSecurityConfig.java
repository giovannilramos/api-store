package br.com.quaz.store.config.secutiry;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtToUserConverter jwtToUserConverter;
    private final KeyUtils keyUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorize) -> authorize.antMatchers("/auth/*").permitAll()
                                .antMatchers(HttpMethod.GET).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .oauth2ResourceServer(
                        (oauth2) -> oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtToUserConverter))
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );

        return http.build();
    }

    @Bean
    @Primary
    JwtDecoder jwtAccessDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
    }

    @Bean
    @Primary
    JwtEncoder jwtAccessEncoder() {
        final var jwk = new RSAKey
                .Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        final var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    @Qualifier("jwtRefreshDecoder")
    JwtDecoder jwtRefreshDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtils.getRefreshTokenPublicKey()).build();
    }

    @Bean
    @Qualifier("jwtRefreshEncoder")
    JwtEncoder jwtRefreshEncoder() {
        final var jwk = new RSAKey
                .Builder(keyUtils.getRefreshTokenPublicKey())
                .privateKey(keyUtils.getRefreshTokenPrivateKey())
                .build();
        final var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    @Qualifier("jwtAuthenticationProvider")
    JwtAuthenticationProvider jwtAuthenticationProvider() {
        final var provider = new JwtAuthenticationProvider(jwtRefreshDecoder());
        provider.setJwtAuthenticationConverter(jwtToUserConverter);

        return provider;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        final var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsManager);

        return provider;
    }
}
