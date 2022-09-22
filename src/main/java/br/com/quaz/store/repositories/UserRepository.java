package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(final String email);
    Optional<User> findByUsername(final String username);
    Boolean existsByUsername(final String username);
    Boolean existsByEmail(final String email);

}
