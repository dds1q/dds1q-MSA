package com.example.ecommercecatalogservice.controller;

import com.example.ecommercecatalogservice.domain.Catalog;
import com.example.ecommercecatalogservice.dto.CatalogResponse;
import com.example.ecommercecatalogservice.service.CatalogService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")    // 상태체크, 포트확인
    public String status(){
        return String.format( "It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port") );
    }

    @GetMapping("/catalogs")   // 모든 물품( 카탈로그 ) 조회
    public ResponseEntity<List<CatalogResponse>> getUsers(){

        Iterable<Catalog> catalogs = catalogService.getAllCatalogs();
        List<CatalogResponse> results = new ArrayList<>();

        catalogs.forEach( v -> {
            results.add( new ModelMapper().map( v , CatalogResponse.class ) );
        });

        return ResponseEntity.status( HttpStatus.OK ).body( results );
    }



}
