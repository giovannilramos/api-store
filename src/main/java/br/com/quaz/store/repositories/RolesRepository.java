package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {
    Set<Roles> findAllByUuidIn(final Set<UUID> rolesUuid);
}
