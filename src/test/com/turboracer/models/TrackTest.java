package com.turboracer.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.turboracer.Main;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {
    private Track track;
    @BeforeEach
    void init() {
        Main.settings = new Settings();
        Main.settings.setTrackWidth(220);
        track = new Track(true);
    }

    @Test
    void getGates() {
        //basic check to see if gates array is correct length
        assertEquals(track.getGates().length, 4);
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