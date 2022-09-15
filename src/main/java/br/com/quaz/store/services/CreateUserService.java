package br.com.quaz.store.services;

import br.com.quaz.store.entities.User;
import br.com.quaz.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(final UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        ((User) user).setId(UUID.randomUUID().toString());

        userRepository.save((User) user);
    }

    @Override
    public void updateUser(final UserDetails user) {

    }

    @Override
    public void deleteUser(final String username) {

    }

    @Override
    public void changePassword(final String oldPassword, final String newPassword) {

    }

    @Override
    public boolean userExists(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("Username {0} not found", username)));
    }

//    public void createUser(final UserRequest userRequest) {
//        final var user = new User();
//
//        user.setName(userRequest.getName());
//        user.setBirthDate(userRequest.getBirthDate());
//        user.setUsername(userRequest.getUsername());
//        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
//        user.setRoles(userRequest.getRoles());
//
//        userRepository.save(user);
//    }
}
