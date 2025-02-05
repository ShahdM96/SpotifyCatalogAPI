package com.example.catalog.services;// src/main/java/com/example/catalog/services/DatabaseDataSourceService.java


import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class DatabaseDataSourceService implements DataSourceService {

    @Override
    public List<Album> getAllAlbums() throws IOException {
        return List.of();
    }

    @Override
    public Album getAlbumById(String id) throws IOException {
        return null;
    }

    @Override
    public Album createAlbum(Album album) throws IOException {
        return null;
    }


    @Override
    public boolean updateAlbumById(String id, Album updatedAlbum) throws IOException {
        return false;
    }

    @Override
    public boolean deleteAlbumById(String id) throws IOException {
        return false;
    }

    @Override
    public List<Track> getTracksByAlbumId(String id) throws IOException {
        return List.of();
    }

    @Override
    public Album addTrack(String id, Track track) throws IOException {
        return null;
    }

    @Override
    public Album updateTrack(String id, String trackId, Track updatedTrack) throws IOException {
        return null;
    }

    @Override
    public boolean deleteTrack(String id, String trackId) throws IOException {
        return false;
    }

    @Override
    public List<Artist> getAllArtists() throws IOException {
        return List.of();
    }

    @Override
    public Artist getArtistById(String id) throws IOException {
        return null;
    }

    @Override
    public Artist createArtist(Artist artist) throws IOException {
        return null;
    }

    @Override
    public Artist updateArtistById(String id, Artist updatedArtist) throws IOException {
        return null;
    }

    @Override
    public boolean deleteArtistById(String id) throws IOException {
        return false;
    }


    @Override
    public List<Album> getAlbumsByArtistId(String id) throws IOException {
        return List.of();
    }

    @Override
    public List<Song> getTopSongsByArtistId(String artistId, String market) throws IOException {
        return List.of();
    }

    @Override
    public List<Song> getAllSongs() throws IOException {
        return List.of();
    }

    @Override
    public Song getSongById(String id) throws IOException {
        return null;
    }

    @Override
    public Song createSong(Song song) throws IOException {
        return null;
    }

    @Override
    public Song updateSongById(String id, Song updatedSong) throws IOException {
        return null;
    }

    @Override
    public boolean deleteSongById(String id) throws IOException {
        return false;
    }

}