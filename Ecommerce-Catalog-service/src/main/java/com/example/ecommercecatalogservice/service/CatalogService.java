package com.example.ecommercecatalogservice.service;

import com.example.ecommercecatalogservice.domain.Catalog;

public interface CatalogService {

    Iterable<Catalog> getAllCatalogs();

}
