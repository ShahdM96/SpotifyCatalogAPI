package com.example.catalog.model;

import java.util.List;

public class Album {
    private String id;
    private String name;
    private String uri;
    private String release_date;
    private int total_tracks;
    private List<Image> images;
    private List<Track> tracks;


//    public Album(String id, String name, String uri, String releaseDate, int totalTracks, List<Image> images, List<Track> tracks) {
//        this.id = id;
//        this.name = name;
//        this.uri = uri;
//        this.release_date = releaseDate;
//        this.total_tracks = totalTracks;
//        this.images = images;
//        this.tracks = tracks;
//    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public int getTotalTracks() {
        return total_tracks;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Track> getTracks() {
        return tracks;
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

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public void setTotalTracks(int totalTracks) {
        this.total_tracks = totalTracks;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
