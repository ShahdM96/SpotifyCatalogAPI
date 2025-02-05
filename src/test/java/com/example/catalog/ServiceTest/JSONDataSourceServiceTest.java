
package com.example.catalog.ServiceTest;

import com.example.catalog.model.Album;
import com.example.catalog.model.Artist;
import com.example.catalog.model.Song;
import com.example.catalog.model.Track;
import com.example.catalog.services.JSONDataSourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONDataSourceServiceTest{

    private JSONDataSourceService dataSourceService;

    @BeforeEach
    void initializeService() throws IOException {
        dataSourceService = new JSONDataSourceService();
    }

    @Test
    void shouldReturnAllAlbums() throws IOException {
        List<Album> albumList = dataSourceService.getAllAlbums();
        assertNotNull(albumList, "Albums should not be null");
    }

    @Test
    void shouldCreateAndRetrieveAlbum() throws IOException {
        Album newAlbum = new Album();
        newAlbum.setName("Fresh Album");
        newAlbum.setReleaseDate("2015-08-15");

        Album createdAlbum = dataSourceService.createAlbum(newAlbum);
        assertNotNull(createdAlbum);
        assertNotNull(createdAlbum.getId(), "Album ID should be generated");

        Album fetchedAlbum = dataSourceService.getAlbumById(createdAlbum.getId());
        assertNotNull(fetchedAlbum);
        assertEquals("Fresh Album", fetchedAlbum.getName(), "Album names should match");
    }

    @Test
    void shouldUpdateExistingAlbum() throws IOException {
        Album originalAlbum = new Album();
        originalAlbum.setName("Initial Album");
        originalAlbum.setReleaseDate("2010-03-03");

        Album albumToUpdate = dataSourceService.createAlbum(originalAlbum);

        Album updatedAlbumDetails = new Album();
        updatedAlbumDetails.setName("Updated Album Title");
        updatedAlbumDetails.setReleaseDate("2023-05-20");

        boolean isUpdated = dataSourceService.updateAlbumById(albumToUpdate.getId(), updatedAlbumDetails);
        assertTrue(isUpdated, "Album update should succeed");

        Album updatedAlbum = dataSourceService.getAlbumById(albumToUpdate.getId());
        assertEquals("Updated Album Title", updatedAlbum.getName(), "Updated album name should be correct");
    }

    @Test
    void shouldDeleteAlbum() throws IOException {
        Album albumToDelete = new Album();
        albumToDelete.setName("Album to Remove");

        Album savedAlbum = dataSourceService.createAlbum(albumToDelete);

        boolean deletionStatus = dataSourceService.deleteAlbumById(savedAlbum.getId());
        assertTrue(deletionStatus, "Album should be deleted successfully");

        Album deletedAlbum = dataSourceService.getAlbumById(savedAlbum.getId());
        assertNull(deletedAlbum, "Deleted album should not be found");
    }

    @Test
    void shouldReturnTracksForAlbum() throws IOException {
        Album albumWithTracks = new Album();
        albumWithTracks.setName("Album with Multiple Tracks");

        Album savedAlbum = dataSourceService.createAlbum(albumWithTracks);

        Track trackOne = new Track();
        trackOne.setName("Track 1");

        Track trackTwo = new Track();
        trackTwo.setName("Track 2");

        dataSourceService.addTrack(savedAlbum.getId(), trackOne);
        dataSourceService.addTrack(savedAlbum.getId(), trackTwo);

        List<Track> tracks = dataSourceService.getTracksByAlbumId(savedAlbum.getId());
        assertNotNull(tracks, "Tracks should not be null");
        assertEquals(2, tracks.size(), "There should be exactly two tracks in the album");
    }

    @Test
    void shouldAddTrackToAlbum() throws IOException {
        Album albumForTrack = new Album();
        albumForTrack.setName("Album for New Track");

        Album savedAlbum = dataSourceService.createAlbum(albumForTrack);

        Track newTrack = new Track();
        newTrack.setName("Track A");

        Album albumWithNewTrack = dataSourceService.addTrack(savedAlbum.getId(), newTrack);
        assertNotNull(albumWithNewTrack, "Album with new track should not be null");
        assertEquals(1, albumWithNewTrack.getTracks().size(), "There should be one track in the album");
    }

    @Test
    void shouldRetrieveArtistById() throws IOException {
        Artist newArtist = new Artist();
        newArtist.setName("Sample Artist");

        Artist savedArtist = dataSourceService.createArtist(newArtist);

        Artist retrievedArtist = dataSourceService.getArtistById(savedArtist.getId());
        assertNotNull(retrievedArtist, "Artist should not be null");
        assertEquals("Sample Artist", retrievedArtist.getName(), "Artist names should match");
    }

    @Test
    void shouldReturnAllArtists() throws IOException {
        List<Artist> allArtists = dataSourceService.getAllArtists();
        assertNotNull(allArtists, "Artist list should not be null");
    }

    @Test
    void shouldSaveNewArtist() throws IOException {
        Artist artistToSave = new Artist();
        artistToSave.setName("Rising Artist");

        Artist savedArtist = dataSourceService.createArtist(artistToSave);
        assertNotNull(savedArtist, "Saved artist should not be null");
        assertNotNull(savedArtist.getId(), "Saved artist should have an ID");
    }

    @Test
    void shouldUpdateArtistDetails() throws IOException {
        Artist initialArtist = new Artist();
        initialArtist.setName("Initial Artist");

        Artist savedArtist = dataSourceService.createArtist(initialArtist);

        Artist updatedArtistInfo = new Artist();
        updatedArtistInfo.setName("Updated Artist");

        Artist updatedArtist = dataSourceService.updateArtistById(savedArtist.getId(), updatedArtistInfo);
        assertNotNull(updatedArtist, "Updated artist should not be null");
        assertEquals("Updated Artist", updatedArtist.getName(), "Artist name should be updated");
    }

    @Test
    void shouldDeleteArtist() throws IOException {
        Artist artistToDelete = new Artist();
        artistToDelete.setName("Artist for Deletion");

        Artist savedArtist = dataSourceService.createArtist(artistToDelete);

        boolean isDeleted = dataSourceService.deleteArtistById(savedArtist.getId());
        assertTrue(isDeleted, "Artist should be deleted successfully");

        Artist deletedArtist = dataSourceService.getArtistById(savedArtist.getId());
        assertNull(deletedArtist, "Deleted artist should not exist");
    }

    @Test
    void shouldRetrieveSongById() throws IOException {
        Song newSong = new Song();
        newSong.setName("Hit Song");

        Song savedSong = dataSourceService.createSong(newSong);

        Song fetchedSong = dataSourceService.getSongById(savedSong.getId());
        assertNotNull(fetchedSong, "Song should not be null");
        assertEquals("Hit Song", fetchedSong.getName(), "Song names should match");
    }

    @Test
    void shouldSaveNewSong() throws IOException {
        Song songForSaving = new Song();
        songForSaving.setName("Chart-topping Song");

        Song savedSong = dataSourceService.createSong(songForSaving);
        assertNotNull(savedSong, "Saved song should not be null");
        assertNotNull(savedSong.getId(), "Saved song should have an ID");
    }

    @Test
    void shouldUpdateSongDetails() throws IOException {
        Song originalSong = new Song();
        originalSong.setName("Old Track");

        Song savedSong = dataSourceService.createSong(originalSong);

        Song updatedSongInfo = new Song();
        updatedSongInfo.setName("New Hit Track");

        Song updatedSong = dataSourceService.updateSongById(savedSong.getId(), updatedSongInfo);
        assertNotNull(updatedSong, "Updated song should not be null");
        assertEquals("New Hit Track", updatedSong.getName(), "Song name should be updated");
    }

    @Test
    void shouldDeleteSong() throws IOException {
        Song songToDelete = new Song();
        songToDelete.setName("Song to Remove");

        Song savedSong = dataSourceService.createSong(songToDelete);

        boolean deletionStatus = dataSourceService.deleteSongById(savedSong.getId());
        assertTrue(deletionStatus, "Song should be deleted successfully");

        Song deletedSong = dataSourceService.getSongById(savedSong.getId());
        assertNull(deletedSong, "Deleted song should not exist");
    }
}
