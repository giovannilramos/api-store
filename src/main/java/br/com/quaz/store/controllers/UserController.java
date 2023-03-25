package br.com.quaz.store.controllers;

import br.com.quaz.store.controllers.request.UpdateUserRequest;
import br.com.quaz.store.controllers.request.UserRequest;
import br.com.quaz.store.controllers.response.UserResponse;
import br.com.quaz.store.services.user.CreateUserService;
import br.com.quaz.store.services.user.GetUserService;
import br.com.quaz.store.services.user.UpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final GetUserService getUserService;

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@Valid @RequestHeader(name = "Authorization") final String jwtToken) {
        return ResponseEntity.ok(getUserService.findLoggedUser(jwtToken));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestHeader(name = "Authorization") final String jwtToken, @Valid @RequestBody final UpdateUserRequest updateUserRequest) {
        final var userResponse = updateUserService.updateUser(jwtToken, updateUserRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid  final UserRequest userRequest) {
        final var userResponse = createUserService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
