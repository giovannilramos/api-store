package br.com.quaz.store.configs.security.service;

import br.com.quaz.store.enums.StatusCode;
import br.com.quaz.store.exceptions.UnauthorizedException;
import br.com.quaz.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        var userOptional = userRepository.findByEmail(login);
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByUsername(login);
            if (userOptional.isEmpty()) {
                throw new UnauthorizedException("Login not found", StatusCode.UNAUTHORIZED.getStatusCode());
            }
        }
        final var user = userOptional.get();

        return new User(user.getEmail(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }
}
