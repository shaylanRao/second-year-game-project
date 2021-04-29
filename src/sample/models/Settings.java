package sample.models;

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
    private VehicleType vehicle2Type = VehicleType.VEHICLE1;

    private Track track = Track.TRACK1;
    private int laps = 1;
    private float masterVol = 100;
    private float sfxVol = 100;
    private float bright = 100;
    private boolean colorBlind = false;
    private Difficulty difficulty = Difficulty.MEDIUM;

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public int getLaps(){
        return this.laps;
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

    public void setVehicle2Type(VehicleType vehicleType) {
        this.vehicle2Type = vehicleType;
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

    @Override
    public String toString() {
        return "Settings{" +
                "playMode=" + playMode +
                ", vehicleType=" + vehicleType +
                ", track=" + track +
                ", laps=" + laps +
                ", masterVol=" + masterVol +
                ", sfxVol=" + sfxVol +
                ", bright=" + bright +
                ", colorBlind=" + colorBlind +
                ", difficulty=" + difficulty +
                '}';
    }
}





