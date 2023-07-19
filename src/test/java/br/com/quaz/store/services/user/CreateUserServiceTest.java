package br.com.quaz.store.services.user;

import br.com.quaz.store.entities.Roles;
import br.com.quaz.store.enums.RoleName;
import br.com.quaz.store.exceptions.AlreadyExistsException;
import br.com.quaz.store.exceptions.NotFoundException;
import br.com.quaz.store.repositories.RolesRepository;
import br.com.quaz.store.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static br.com.quaz.store.mockHelper.MockHelper.userMock;
import static br.com.quaz.store.mockHelper.MockHelper.userRequestMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {
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
        final var userMock = userMock(UUID.randomUUID(), "Giovanni", "giovannilramos55@gmail.com", "giovanni.ramos");
        when(userRepository.existsByEmail("giovannilramos55@gmail.com")).thenReturn(Boolean.FALSE);
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.FALSE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));
        when(userRepository.save(any())).thenReturn(userMock);

        final var user = createUserService.createUser(userRequestMock());

        assertEquals(userMock.getUuid(), user.getUuid());
        assertEquals(userMock.getName(), user.getName());
        assertEquals(userMock.getBirthDate(), user.getBirthDate());
        assertEquals(userMock.getUsername(), user.getUsername());
        assertEquals(userMock.getTaxId(), user.getTaxId());
        assertEquals(userMock.getEmail(), user.getEmail());
    }

    @Test
    void shouldThrowRolesNotFoundExceptionWhenSetRoleIsEmpty() {
        final var userRequest = userRequestMock();
        assertThrows(NotFoundException.class, () -> createUserService.createUser(userRequest), "Roles not found");
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        final var userRequest = userRequestMock();
        when(userRepository.existsByEmail("giovannilramos55@gmail.com")).thenReturn(Boolean.TRUE);
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.FALSE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));

        assertThrows(AlreadyExistsException.class, () -> createUserService.createUser(userRequest), "E-mail already exists");
    }

    @Test
    void shouldThrowUsernameAlreadyExistsException() {
        final var userRequest = userRequestMock();
        when(userRepository.existsByUsername("giovanni.ramos")).thenReturn(Boolean.TRUE);
        when(rolesRepository.findAllByUuidIn(any())).thenReturn(Set.of(this.roles));

        assertThrows(AlreadyExistsException.class, () -> createUserService.createUser(userRequest), "Username already exists");
    }
}
