package com.turboracer.models;

public class Settings {

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public enum Track {
        TRACK1,
        TRACK2,
        TRACK3
    }

    public enum PlayMode {
        MULTIPLAYER,
        AI,
        TIMETRIAL
    }

    public enum VehicleType {
        VEHICLE1,
        VEHICLE2,
        VEHICLE3
    }


    private PlayMode playMode = PlayMode.AI;
    private VehicleType vehicleType = VehicleType.VEHICLE1;
    private VehicleType vehicle2Type = VehicleType.VEHICLE2;

    private Track track = Track.TRACK1;
    private int laps = 1;
    private float masterVol = 100;
    private float sfxVol = 100;
    private float bright = 100;
    private boolean colorBlind = false;
    private Difficulty difficulty = Difficulty.MEDIUM;

    public int getWiggleFactor() {
        return wiggleFactor;
    }

    public void setWiggleFactor(int wiggleFactor) {
        if (wiggleFactor >= 0 && wiggleFactor <= 100) {
            this.wiggleFactor = wiggleFactor;
        } else {
            System.out.println("Wiggle factor out of range. Must be between 0 and 100.");
        }
    }

    //goes from 0 to 100
    private int wiggleFactor = 10;

    public int getTrackWidth() {
        return trackWidth;
    }

    public void setTrackWidth(int trackWidth) {
//        if (trackWidth >= 100 && trackWidth <= 500) {
            this.trackWidth = trackWidth;
//        } else {
//            System.out.println("Track width out of range. Must be between 100 and 500.");
//        }

    }

    //goes from 100 to 500
    private int trackWidth = 220;

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public int getLaps(){
        return laps;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleType getVehicle2Type() {
        return vehicle2Type;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVehicle2Type(VehicleType vehicle2Type) {
        this.vehicle2Type = vehicle2Type;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public float getMasterVol() {
        return masterVol;
    }

    public void setMasterVol(float masterVol) {
        this.masterVol = masterVol;
    }

    public float getSfxVol() {
        return sfxVol;
    }

    public void setSfxVol(float sfxVol) {
        this.sfxVol = sfxVol;
    }

    public float getBright() {
        return bright;
    }

    public void setBright(float bright) {
        this.bright = bright;
    }

    public boolean isColorBlind() {
        return colorBlind;
    }

    public void setColorBlind(boolean colorBlind) {
        this.colorBlind = colorBlind;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "playMode=" + playMode +
                ", vehicle1Type=" + vehicleType +
                ", vehicle2Type=" + vehicleType +
                ", wiggle=" + wiggleFactor +
                ", track Width=" + trackWidth +
                ", laps=" + laps +
                ", masterVol=" + masterVol +
                ", sfxVol=" + sfxVol +
                ", difficulty=" + difficulty +
                '}';
    }
}





