package com.example.ecommercecatalogservice.repository;

import com.example.ecommercecatalogservice.domain.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
