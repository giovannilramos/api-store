package br.com.quaz.store.repositories;

import br.com.quaz.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByNameIgnoreCaseAndBrand(final String name, final String brand);
}
