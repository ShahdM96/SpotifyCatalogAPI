package com.example.catalog.controller;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.services.DataSourceService;
import com.example.catalog.services.JSONDataSourceService;
import com.example.catalog.utils.SpotifyUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final JSONDataSourceService jsonDataSourceService;

    public ArtistController(JSONDataSourceService jsonDataSourceService) {
        this.jsonDataSourceService = jsonDataSourceService;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() throws IOException {
        List<Artist> artists = jsonDataSourceService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable String id) throws IOException {
        Optional<Artist> artist = Optional.ofNullable(jsonDataSourceService.getArtistById(id));
        return artist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) throws IOException {
        Artist createdArtist = jsonDataSourceService.createArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable String id, @RequestBody Artist artist) throws IOException {
        Artist updatedArtist = jsonDataSourceService.updateArtistById(id, artist);
        if (updatedArtist != null) {
            return ResponseEntity.ok(updatedArtist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artist not found");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable String id) throws IOException {
        boolean deleted = jsonDataSourceService.deleteArtistById(id);
        if (deleted) {
            return ResponseEntity.ok().body("Artist deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artist not found");
        }
    }
}