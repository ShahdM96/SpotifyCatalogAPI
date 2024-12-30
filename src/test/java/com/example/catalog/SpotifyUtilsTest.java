package com.example.catalog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.example.catalog.utils.SpotifyUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpotifyUtilsTest {

    @Test
    public void testValidId() {
        assertTrue(isValidId("6rqhFgbbKwnb9MLmUQDhG6")); // valid Spotify ID
        assertTrue(isValidId("1a2B3c4D5e6F7g8H9iJkL0mN")); // valid 24 character ID
        assertTrue(isValidId("a1b2C3d4E5f6G7h8I9jK0L1m2N4fgY")); // valid 30 character ID
    }

    @Test
    public void testInvalidId() {
        assertFalse(isValidId(null)); // null ID
        assertFalse(isValidId("")); // empty ID
        assertFalse(isValidId("shortID")); // too short ID (less than 15 characters)
        assertFalse(isValidId("thisIDiswaytoolongtobevalidddddd")); // too long ID (more than 30 characters)
        assertFalse(isValidId("!@#$$%^&*()_+")); // invalid characters
        assertFalse(isValidId("1234567890abcdefGHIJKLMNO!@#")); // includes invalid characters
    }

    @Test
    public void testValidURI() {
        assertTrue(isValidURI("spotify:track:6rqhFgbbKwnb9MLmUQDhG6")); // valid Spotify URI
        assertTrue(isValidURI("spotify:album:1a2B3c4D5e6F7g8H9iJkL0mN")); // valid 22 character ID
        assertTrue(isValidURI("spotify:artist:a1b2C3d4E5f6G7h8I9jK0L1m2N")); // valid 30 character ID
        assertTrue(isValidURI("spotify:playlist:6rqhFgbbKwnb9MLmUQDhG6"));
    }

    @Test
    public void testInvalidURI() {
        assertFalse(isValidURI(null)); // null URI
        assertFalse(isValidURI("")); // empty URI
        assertFalse(isValidURI("shortURI")); // too short URI (less than 15 characters)
        assertFalse(isValidURI("thisURIiswaytoolongtobevalidaaaa")); // too long URI (more than 30 characters)
        assertFalse(isValidURI("!@#$$%^&*()_+")); // invalid characters
        assertFalse(isValidURI("1234567890abcdefGHIJKLMNO!@#")); // includes invalid characters
    }

    @Test
    public void testSpotifyClient() {
        assertThrows(IllegalArgumentException.class, () -> getSpotifyClient(null, null));//both null
        assertThrows(IllegalArgumentException.class, () -> getSpotifyClient(null, "valid secret"));//id null
        assertThrows(IllegalArgumentException.class, () -> getSpotifyClient("6rqhFgbbKwnb9MLmUQDhG6", null));//secret null and valid id
        assertThrows(IllegalArgumentException.class, () -> getSpotifyClient("", "valid secret"));//empty id
        assertThrows(IllegalArgumentException.class, () -> getSpotifyClient("6rqhFgbbKwnb9MLmUQDhG6", ""));//ampty secret

    }



}
