package br.com.quaz.store.helper;

import br.com.quaz.store.entities.User;
import br.com.quaz.store.request.UserRequest;

public class UserHelper {
    public static void setUserEntity(final User user, final UserRequest userRequest) {
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setBirthDate(userRequest.getBirthDate());
        user.setRoles(userRequest.getRoles());
    }
}
