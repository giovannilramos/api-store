package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Purchase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    @Query(value = "select * from tb_purchase tp " +
            "inner join tb_user tu on tp.user_uuid = tu.uuid " +
            "where email = ?1 order by tp.created_at",
            countQuery = "select count(*) from tb_purchase " +
                    "inner join tb_user tu on tb_purchase.user_uuid = tu.uuid " +
                    "where email like ?1",
            nativeQuery = true)
    List<Purchase> findAllByLoggedUser(final String email, final Pageable pageable);
    Optional<Purchase> findByPurchaseNumber(final String purchaseNumber);
}
