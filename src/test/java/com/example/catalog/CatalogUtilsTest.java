package com.example.catalog;

import com.example.catalog.utils.CatalogUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CatalogUtilsTest {

    private CatalogUtils catalogUtils;
    private List<JsonNode> songs;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        catalogUtils = new CatalogUtils();
        objectMapper = new ObjectMapper();

        // Sample song data for testing. TODO - Add more songs
        String jsonData = """
                    [
                        {
                          "duration_ms": 200040,
                          "name": "Blinding Lights",
                          "popularity": 87,
                          "album": {
                            "name": "After Hours",
                            "release_date": "2020-03-20",
                            "total_tracks": 14
                          },
                          "artists": [
                            {
                              "name": "The Weeknd"
                            }
                          ]
                        }
                    ]
                """;
        songs = new ArrayList<>();
        objectMapper.readTree(jsonData).forEach(songs::add);
    }
}