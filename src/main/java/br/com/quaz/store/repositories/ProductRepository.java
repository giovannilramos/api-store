package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByUuidIn(final List<UUID> uuidList);
    Boolean existsByNameIgnoreCaseAndBrand(final String name, final String brand);

    @Query(value = "select * from tb_product " +
            "inner join tb_wish_list twl on tb_product.uuid = twl.product_uuid " +
            "inner join tb_user tu on twl.user_uuid = tu.uuid " +
            "where email like ?1 order by twl.created_at",
            countQuery = "select count(*) from tb_product " +
                    "inner join tb_wish_list twl on tb_product.uuid = twl.product_uuid " +
                    "inner join tb_user tu on twl.user_uuid = tu.uuid " +
                    "where email like ?1",
            nativeQuery = true)
    Page<Product> findAllByLoggedUser(final String email, final Pageable pageable);
}
