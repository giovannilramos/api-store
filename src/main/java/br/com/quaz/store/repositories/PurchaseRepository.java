package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Purchase;
import br.com.quaz.store.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findAllByUser(final User user, final Pageable pageable);
    Optional<Purchase> findByPurchaseNumber(final String purchaseNumber);
}
