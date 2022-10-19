package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Address;
import br.com.quaz.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findAllByUser(final User user);
}
