package br.com.quaz.store;

import br.com.quaz.store.controllers.request.UserRequest;
import br.com.quaz.store.entities.Roles;
import br.com.quaz.store.enums.RoleName;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import br.com.quaz.store.services.CreateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {
    private final UserRequest userRequest = UserRequest.builder()
            .name("Giovanni")
            .birthDate(LocalDate.now())
            .email("giovannilramos55@gmail.com")
            .password("123")
            .rolesUuid(Set.of(UUID.randomUUID()))
            .username("giovanni.ramos")
            .build();
    private final Roles roles = Roles.builder()
            .uuid(UUID.randomUUID())
            .roleName(RoleName.ROLE_USER)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    @Mock
    private UserRepository userRepository;
    @Mock
    private RolesRepository rolesRepository;
    @InjectMocks
    private CreateUserService createUserService;

    @Test
    void shouldCreateNewUser() {
        when(userRepository.existsByEmail("giovannilramos55@gmail.com")).thenReturn(Boolean.FALSE);
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.FALSE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));

        createUserService.createUser(this.userRequest);

        verify(userRepository, times(1)).save(any());
        assertDoesNotThrow(() -> createUserService.createUser(this.userRequest));
    }

    @Test
    void shouldThrowRolesNotFoundExceptionWhenSetRoleIsEmpty() {
        assertThrows(NotFoundException.class, () -> createUserService.createUser(this.userRequest), "Roles not found");
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        when(userRepository.existsByEmail("giovannilramos55@gmail.com")).thenReturn(Boolean.TRUE);
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.FALSE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));

        assertThrows(AlreadyExistsException.class, () -> createUserService.createUser(this.userRequest), "E-mail already exists");
    }

    @Test
    void shouldThrowUsernameAlreadyExistsException() {
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.TRUE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));

        assertThrows(AlreadyExistsException.class, () -> createUserService.createUser(this.userRequest), "Username already exists");
    }
}
