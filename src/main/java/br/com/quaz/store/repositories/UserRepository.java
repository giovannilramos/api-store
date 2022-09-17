package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(final String email);
    Optional<User> findByUsername(final String username);

}
