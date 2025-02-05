package com.example.catalog.model;

import java.util.List;

public class Song {

    private String id;
    private String name;
    private String uri;
    private boolean explicit;
    private int duration_ms;
    private int popularity;
    private Album album;
    private List<Artist> artists;

//    public Song(String id, String name, String uri, int durationMs, int popularity, Album album, List<Artist> artists) {
//        this.id = id;
//        this.name = name;
//        this.uri = uri;
//        this.duration_ms = durationMs;
//        this.popularity = popularity;
//        this.album = album;
//        this.artists = artists;
//    }

    public boolean isExplicit() { return explicit; }
    public void setExplicit(boolean explicit) { this.explicit = explicit; }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public int getDurationMs() {
        return duration_ms;
    }

    public int getPopularity() {
        return popularity;
    }

    public Album getAlbum() {
        return album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setDurationMs(int durationMs) {
        this.duration_ms = durationMs;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
