package br.com.quaz.store.services;

import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.repositories.AddressRepository;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.quaz.store.helper.UserHelper.setUserEntity;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AddressRepository addressRepository;

    public void createUser(final UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AlreadyExistsException("E-mail already exists");
        }

        setUserEntity(userRequest, rolesRepository, userRepository, addressRepository);
    }
}
