package com.example.catalog.services;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class SpotifyAPIDataSources implements DataSourceService {

    private static final String CLIENT_ID = "e94c97dc0c0f42fea5921f4287ac8baa"; // Replace with your Client ID
    private static final String CLIENT_SECRET = "d0bce3b8f4d64e4a91da053c48c7627b"; // Replace with your Client Secret
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String BASE_URL = "https://api.spotify.com/v1/albums/";


    private String accessToken;

    public SpotifyAPIDataSources() throws IOException {
        this.accessToken = getAccessToken();
    }
    private static String getAccessToken() throws IOException {
        URL url = new URL(TOKEN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encodeCredentials());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        String requestBody = "grant_type=client_credentials";
        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to retrieve access token: " + responseCode);
        }

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String token = jsonNode.get("access_token").asText();

        return token;
    }


    // Encode Client Credentials
    private static String encodeCredentials() {
        String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    // Helper method for making GET requests
    private JsonNode makeGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch data: HTTP " + responseCode);
        }

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseBody);
        }
    }


    @Override
    public List<Album> getAllAlbums() throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }
    @Override
    public Album getAlbumById(String albumId) throws IOException {
        JsonNode albumNode = makeGetRequest(BASE_URL + albumId);

        Album album = new Album();
        album.setId(albumNode.get("id").asText());
        album.setName(albumNode.get("name").asText());
        album.setReleaseDate(albumNode.get("release_date").asText());
        album.setTotalTracks(albumNode.get("total_tracks").asInt());

        return album;
    }



    // Save Album
    @Override
    public Album createAlbum(Album album) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public boolean updateAlbumById(String id, Album updatedAlbum) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public boolean deleteAlbumById(String id) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }
    @Override
    public List<Track> getTracksByAlbumId(String albumId) throws IOException {
        JsonNode tracksNode = makeGetRequest(BASE_URL + albumId + "/tracks").get("items");

        List<Track> tracks = new ArrayList<>();
        for (JsonNode trackNode : tracksNode) {
            Track track = new Track();
            track.setId(trackNode.get("id").asText());
            track.setName(trackNode.get("name").asText());
            tracks.add(track);
        }

        return tracks;
    }

    @Override
    public Album addTrack(String id, Track track) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public Album updateTrack(String id, String trackId, Track updatedTrack) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public boolean deleteTrack(String id, String trackId) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public List<Artist> getAllArtists() throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }


    @Override
    public Artist getArtistById(String artistId) throws IOException {
        JsonNode artistNode = makeGetRequest("https://api.spotify.com/v1/artists/" + artistId);

        Artist artist = new Artist();
        artist.setId(artistNode.get("id").asText());
        artist.setName(artistNode.get("name").asText());
        artist.setGenres(new ArrayList<>(List.of(artistNode.get("genres").asText().split(","))));
        artist.setPopularity(artistNode.get("popularity").asInt());
        artist.setFollowers(artistNode.get("followers").get("total").asInt());

        return artist;
    }


    @Override
    public Artist createArtist(Artist artist) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public Artist updateArtistById(String id, Artist updatedArtist) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public boolean deleteArtistById(String id) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }


    @Override
    public List<Album> getAlbumsByArtistId(String artistId) throws IOException {
        JsonNode albumsNode = makeGetRequest("https://api.spotify.com/v1/artists/" + artistId + "/albums").get("items");

        List<Album> albums = new ArrayList<>();
        for (JsonNode albumNode : albumsNode) {
            Album album = new Album();
            album.setId(albumNode.get("id").asText());
            album.setName(albumNode.get("name").asText());
            album.setReleaseDate(albumNode.get("release_date").asText());
            album.setTotalTracks(albumNode.get("total_tracks").asInt());
            albums.add(album);
        }

        return albums;
    }

    private Scanner getScanner(String artistId) throws IOException {
        URL url = new URL("https://api.spotify.com/v1/artists/" + artistId + "/albums");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch albums: HTTP " + responseCode);
        }

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        return scanner;
    }

    @Override
    public List<Song> getTopSongsByArtistId(String artistId, String market) throws IOException {
        JsonNode songsNode = makeGetRequest("https://api.spotify.com/v1/artists/" + artistId + "/top-tracks?market=" + market).get("tracks");

        List<Song> topSongs = new ArrayList<>();
        for (JsonNode songNode : songsNode) {
            Song song = new Song();
            song.setId(songNode.get("id").asText());
            song.setName(songNode.get("name").asText());
            song.setDurationMs(songNode.get("duration_ms").asInt());
            song.setPopularity(songNode.get("popularity").asInt());
            topSongs.add(song);
        }

        return topSongs;
    }

    @Override
    public List<Song> getAllSongs() throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public Song getSongById(String songId) throws IOException {
        URL url = new URL("https://api.spotify.com/v1/tracks/" + songId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch song: HTTP " + responseCode);
        }

        Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode songNode = objectMapper.readTree(responseBody);

        Song song = new Song();
        song.setId(songNode.get("id").asText());
        song.setName(songNode.get("name").asText());
        song.setDurationMs(songNode.get("duration_ms").asInt());
        song.setExplicit(songNode.get("explicit").asBoolean());

        return song;
    }

    @Override
    public Song createSong(Song song) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public Song updateSongById(String id, Song updatedSong) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }

    @Override
    public boolean deleteSongById(String id) throws IOException {
        throw new UnsupportedOperationException("not supported by the Spotify API.");
    }
}