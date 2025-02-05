package com.example.catalog.model;

public class Track {

    private String id;
    private String name;
    private String uri;
    private int durationMs;
    private boolean explicit;

//    public Track(String id, String name, String uri, int durationMs, boolean explicit) {
//        this.id = id;
//        this.name = name;
//        this.uri = uri;
//        this.durationMs = durationMs;
//        this.explicit = explicit;
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

    public int getDurationMs() {
        return durationMs;
    }

    public boolean isExplicit() {
        return explicit;
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
        this.durationMs = durationMs;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }
}
