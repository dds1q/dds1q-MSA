package com.example.ecommercecatalogservice.repository;

import com.example.ecommercecatalogservice.domain.Catalog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByProductId(String productId);
}
