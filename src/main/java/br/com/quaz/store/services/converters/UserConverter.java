package br.com.quaz.store.services.converters;

import br.com.quaz.store.entities.Roles;
import br.com.quaz.store.entities.User;
import br.com.quaz.store.controllers.request.UpdateUserRequest;
import br.com.quaz.store.controllers.request.UserRequest;
import br.com.quaz.store.controllers.response.UserResponse;
import br.com.quaz.store.services.dto.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {
    public static User convertUserDTOToEntity(final UserDTO userDTO) {
        return User.builder()
                .uuid(userDTO.getUuid())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .taxId(userDTO.getTaxId())
                .birthDate(userDTO.getBirthDate())
                .roles(userDTO.getRoles()).build();
    }

    public static UserDTO convertUserRequestToDTO(final UserRequest userRequest, final Set<Roles> roles) {
        return UserDTO.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .taxId(userRequest.getTaxId())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .birthDate(userRequest.getBirthDate())
                .roles(roles).build();
    }

    public static UserDTO convertUserRequestToDTO(final UpdateUserRequest updateUserRequest, final UserDTO userDTO) {
        return userDTO.toBuilder()
                .name(updateUserRequest.getName())
                .email(updateUserRequest.getEmail())
                .username(updateUserRequest.getUsername())
                .birthDate(updateUserRequest.getBirthDate()).build();
    }

    public static UserDTO convertUserEntityToDTO(final User user) {
        return UserDTO.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .taxId(user.getTaxId())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .roles(user.getRoles()).build();
    }

    public static UserResponse convertUserDTOToResponse(final UserDTO userDTO) {
        return UserResponse.builder()
                .uuid(userDTO.getUuid())
                .name(userDTO.getName())
                .taxId(userDTO.getTaxId())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .birthDate(userDTO.getBirthDate()).build();
    }
}
