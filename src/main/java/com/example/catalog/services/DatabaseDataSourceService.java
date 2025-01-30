package com.example.catalog.services;// src/main/java/com/example/catalog/services/DatabaseDataSourceService.java


import com.example.catalog.model.Artist;
import org.springframework.stereotype.Service;

@Service
public class DatabaseDataSourceService implements DataSourceService {

    @Override
    public Artist getArtistById(String id) {
        return db.findById(id).orElse(null);
    }
}