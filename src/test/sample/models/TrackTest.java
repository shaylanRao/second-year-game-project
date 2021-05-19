package sample.models;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {
    private Track track;
    @BeforeEach
    void init() {
        track = new Track();
    }

    @Test
    void getGates() {
        //basic check to see if gates array is correct length
        assertEquals(track.getGates().length, 4);
        //TODO do we need to test more thoroughly than this, i.e., check that the gates are actually correct - what would we compare against??
    }

    @Test
    void getOuterPathElems() {
        //basic check to see if ArrayList is not empty
        assertNotEquals(track.getOuterPathElems().size(), 0);
    }

    @Test
    void getInnerPathElems() {
        //basic check to see if ArrayList is not empty
        assertNotEquals(track.getInnerPathElems().size(), 0);
    }

    @Test
    void getPowerupSpawns() {
        //basic check to see if ArrayList is not empty
        assertNotEquals(track.getPowerupSpawns().size(), 0);
    }

    @Test
    void getTrackLines() {
        //basic check to see if ArrayList is not empty
        assertNotEquals(track.getTrackLines().size(), 0);
    }
}