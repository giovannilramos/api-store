package br.com.quaz.store.controllers;

import br.com.quaz.store.request.UpdateUserRequest;
import br.com.quaz.store.request.UserRequest;
import br.com.quaz.store.response.UserResponse;
import br.com.quaz.store.services.CreateUserService;
import br.com.quaz.store.services.GetUserService;
import br.com.quaz.store.services.UpdateUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final GetUserService getUserService;

    @GetMapping
    public ResponseEntity<UserResponse> findUserById(@RequestParam(name = "identification") final String identification) {
        return ResponseEntity.status(HttpStatus.OK).body(getUserService.findLoggedUser(identification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(name = "id") final UUID id, @RequestBody final UpdateUserRequest updateUserRequest) {
        updateUserService.updateUser(id, updateUserRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody final UserRequest userRequest) {
        createUserService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
