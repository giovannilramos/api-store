package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Roles, UUID> {
    Set<Roles> findAllByUuidIn(final Set<UUID> rolesUuid);
}
