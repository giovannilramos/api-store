package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.UpdateUserRequest;
import br.com.quaz.store.controllers.request.UserRequest;
import br.com.quaz.store.controllers.response.UserResponse;
import br.com.quaz.store.services.CreateUserService;
import br.com.quaz.store.services.GetUserService;
import br.com.quaz.store.services.UpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final GetUserService getUserService;

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@RequestHeader(name = "Authorization") final String jwtToken) {
        return ResponseEntity.ok(getUserService.findLoggedUser(jwtToken));
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestHeader(name = "Authorization") final String jwtToken, @RequestBody final UpdateUserRequest updateUserRequest) {
        updateUserService.updateUser(jwtToken, updateUserRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody final UserRequest userRequest) {
        createUserService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
