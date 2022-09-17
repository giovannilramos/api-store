package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UUID> {
}
