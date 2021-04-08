package sample.models;

import sample.Main;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager{

    public int lapCounter = 0;
    private int maxLaps = Main.settings.getLaps();
    //Record lap times per lap (post race review thing)
    final int[] millisecondsPassed = {0};
    final int lap[] = {lapCounter};
    private int[] eachLap = new int[4];

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
        //final int[] secondsPassed = {0};
        final int lap[] = {lapCounter};
        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int i = 0;
                millisecondsPassed[0]++;
                if (lapCounter != lap[0] && i <= lapCounter) {
                    eachLap[i] = millisecondsPassed[0];
                    lap[0] = lapCounter;
                    i++;
                }
            }
        };
        myTimer.scheduleAtFixedRate(task,1000,1);
        /*TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                if (lapCounter < 4) {
                    lapCounter++;
                    System.out.println(lapCounter);
                }
            }
        };
        myTimer.scheduleAtFixedRate(task,1000,1);
        myTimer.scheduleAtFixedRate(task2,1000,5000);
        //lapTime[0] = secondsPassed[0];
        @Override
            public void run() {
                millisecondsPassed[0]++;
				if (lapCounter != lap[0] && lapCounter < 4) {
					eachLap[lapCounter] = millisecondsPassed[0];
					lap[0] = lapCounter;
				}

            }
        DECOMMENT THIS IF YOU WANT TO TEST TIMER FUNCTION IN GAME */
        /*if (lapCounter == 4) {
            for (int i = 0; i < eachLap.length; i++) {
                System.out.println(eachLap[i]);
            }
        }
        PASTE THIS INTO GAME HANDLE TO TEST TIMER*/
        //System.out.println(millisecondsPassed[0]);
    }

    private void finishPosition(){
        //If finished return true
    }

}
