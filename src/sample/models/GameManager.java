package sample.models;

import sample.Main;

public class
GameManager{

    public int lapCounter = 0;
    private int maxLaps = Main.settings.getLaps();
    //Record lap times per lap (post race review thing)
    private double lapTime[];

    private void startingLap(){
        //logic for beginning of the race passing the finish line to start lap 1
    }

    private void finishLine(){
        //Collision detection for each gate (track gates order, do logic (should be in order 1, 2, 3, 4, 1))
    }

    private void lapCounter(){
        //if collision with lap gates (all 4 and back to 1) then lapCounter++
        //start lapTimer
    }

    private void lapTimer(){
        //Do the time
    }

    private void finishPosition(){
        //If finished return true
    }

}
