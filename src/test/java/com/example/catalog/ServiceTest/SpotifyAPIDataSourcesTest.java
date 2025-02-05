package com.example.catalog.ServiceTest;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import com.example.catalog.services.SpotifyAPIDataSources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpotifyAPIDataSourcesTest {

    private SpotifyAPIDataSources spotifyService;
    private SpotifyAPIDataSources mockSpotifyService;

    @BeforeEach
    void setUp() throws IOException {
        // Create a mock instance of the Spotify service
        mockSpotifyService = mock(SpotifyAPIDataSources.class);
        spotifyService = new SpotifyAPIDataSources();  // Initialize the real service instance if needed
    }

    @Test
    void shouldReturnAlbumWhenAlbumIdIsValid() throws IOException {
        Album mockAlbum = new Album();
        mockAlbum.setId("123");
        mockAlbum.setName("The Album Test");
        mockAlbum.setReleaseDate("2023-01-01");
        mockAlbum.setTotalTracks(10);

        when(mockSpotifyService.getAlbumById("123")).thenReturn(mockAlbum);

        Album album = mockSpotifyService.getAlbumById("123");

        assertNotNull(album, "Album should not be null.");
        assertEquals("123", album.getId(), "Album ID mismatch.");
        assertEquals("The Album Test", album.getName(), "Album name mismatch.");
        assertEquals(10, album.getTotalTracks(), "Album track count mismatch.");

        verify(mockSpotifyService, times(1)).getAlbumById("123");
    }

    @Test
    void shouldReturnArtistWhenArtistIdIsValid() throws IOException {
        Artist mockArtist = new Artist();
        mockArtist.setId("456");
        mockArtist.setName("John Doe");
        mockArtist.setPopularity(85);
        mockArtist.setFollowers(250000);

        when(mockSpotifyService.getArtistById("456")).thenReturn(mockArtist);

        Artist artist = mockSpotifyService.getArtistById("456");

        assertNotNull(artist);
        assertEquals("456", artist.getId());
        assertEquals("John Doe", artist.getName());
        assertEquals(85, artist.getPopularity());

        verify(mockSpotifyService, times(1)).getArtistById("456");
    }

    @Test
    void shouldReturnTracksForAlbumWhenAlbumIdIsValid() throws IOException {
        Track track1 = new Track();
        track1.setId("track1");
        track1.setName("Awesome Track");

        Track track2 = new Track();
        track2.setId("track2");
        track2.setName("Another Cool Track");

        List<Track> mockTracks = List.of(track1, track2);

        when(mockSpotifyService.getTracksByAlbumId("123")).thenReturn(mockTracks);

        List<Track> tracks = mockSpotifyService.getTracksByAlbumId("123");

        assertNotNull(tracks);
        assertEquals(2, tracks.size());
        assertEquals("Awesome Track", tracks.get(0).getName());

        verify(mockSpotifyService, times(1)).getTracksByAlbumId("123");
    }

    @Test
    void shouldReturnAlbumsForArtistWhenArtistIdIsValid() throws IOException {
        Album album1 = new Album();
        album1.setId("album1");
        album1.setName("First Album");

        Album album2 = new Album();
        album2.setId("album2");
        album2.setName("Second Album");

        List<Album> mockAlbums = List.of(album1, album2);

        when(mockSpotifyService.getAlbumsByArtistId("456")).thenReturn(mockAlbums);

        List<Album> albums = mockSpotifyService.getAlbumsByArtistId("456");

        assertNotNull(albums);
        assertEquals(2, albums.size());
        assertEquals("First Album", albums.get(0).getName());

        verify(mockSpotifyService, times(1)).getAlbumsByArtistId("456");
    }

    @Test
    void shouldReturnTopSongsForArtistWhenArtistIdAndCountryCodeAreValid() throws IOException {
        Song song1 = new Song();
        song1.setId("song1");
        song1.setName("Top Hit One");

        Song song2 = new Song();
        song2.setId("song2");
        song2.setName("Top Hit Two");

        List<Song> mockSongs = List.of(song1, song2);

        when(mockSpotifyService.getTopSongsByArtistId("456", "US")).thenReturn(mockSongs);

        List<Song> songs = mockSpotifyService.getTopSongsByArtistId("456", "US");

        assertNotNull(songs);
        assertEquals(2, songs.size());
        assertEquals("Top Hit One", songs.get(0).getName());

        verify(mockSpotifyService, times(1)).getTopSongsByArtistId("456", "US");
    }

    @Test
    void shouldReturnSongWhenSongIdIsValid() throws IOException {
        Song mockSong = new Song();
        mockSong.setId("song123");
        mockSong.setName("Classic Song");
        mockSong.setDurationMs(210000);
        mockSong.setExplicit(true);

        when(mockSpotifyService.getSongById("song123")).thenReturn(mockSong);

        Song song = mockSpotifyService.getSongById("song123");

        assertNotNull(song);
        assertEquals("song123", song.getId());
        assertEquals("Classic Song", song.getName());
        assertTrue(song.isExplicit(), "The song should be explicit.");

        verify(mockSpotifyService, times(1)).getSongById("song123");
    }

    @Test
    void shouldThrowIOExceptionWhenArtistIdIsInvalid() throws IOException {
        when(mockSpotifyService.getArtistById("invalid")).thenThrow(new IOException("Failed to fetch artist: HTTP 404"));

        Exception exception = assertThrows(IOException.class, () -> {
            mockSpotifyService.getArtistById("invalid");
        });

        assertTrue(exception.getMessage().contains("Failed to fetch artist: HTTP 404"));
    }

    @Test
    void shouldThrowIOExceptionWhenAlbumIdIsInvalid() throws IOException {
        when(mockSpotifyService.getAlbumById("invalid")).thenThrow(new IOException("Failed to fetch album: HTTP 404"));

        Exception exception = assertThrows(IOException.class, () -> {
            mockSpotifyService.getAlbumById("invalid");
        });

        assertTrue(exception.getMessage().contains("Failed to fetch album: HTTP 404"));
    }
}
