package com.example.catalog.controller;

import com.example.catalog.model.Album;

import com.example.catalog.services.JSONDataSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController { private final JSONDataSourceService jsonDataSourceService;

    public AlbumController(JSONDataSourceService jsonDataSourceService) {
        this.jsonDataSourceService = jsonDataSourceService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() throws IOException {
        List<Album> albums = jsonDataSourceService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable String id) throws IOException {
        Optional<Album> album = Optional.ofNullable(jsonDataSourceService.getAlbumById(id));
        return album.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) throws IOException {
        Album createdAlbum = jsonDataSourceService.createAlbum(album);
        return ResponseEntity.ok(createdAlbum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable String id, @RequestBody Album album) throws IOException {
        boolean updated = jsonDataSourceService.updateAlbumById(id, album);
        if (updated) {
            return ResponseEntity.ok().body("Album updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Album not found");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable String id) throws IOException {
        boolean deleted = jsonDataSourceService.deleteAlbumById(id);
        if (deleted) {
            return ResponseEntity.ok().body("Album deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Album not found");
        }
    }
}
