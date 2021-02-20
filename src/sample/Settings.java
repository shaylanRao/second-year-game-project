package sample;

public class Settings {
    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    enum Track {
        TRACK1,
        TRACK2,
        TRACK3
    }

    enum PlayMode {
        STANDARD,
        AI,
        TIMETRIAL
    }

    enum VehicleType {
        VEHICLE1,
        VEHICLE2,
        VEHICLE3
    }

    private PlayMode playMode;
    private VehicleType vehicleType;
    private Track track;
    private float masterVol;
    private float sfxVol;
    private float bright;
    private boolean colorBlind = false;

    private Difficulty difficulty;

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

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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

    public Settings() { }
}





