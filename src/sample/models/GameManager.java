package sample.models;

import sample.Main;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Stack;

public class GameManager{

    public int lapCounter = 0;
    private int maxLaps = Main.settings.getLaps();
    //Record lap times per lap (post race review thing)
    private double lapTime[];
    private Stack<Integer> gateStack;
    final int[] millisecondsPassed = {0};
    final int lap[] = {lapCounter};
    private int[] eachLap = new int[4];

    public void setGateDistances(double[] gateDistances) {
        this.gateDistances = gateDistances;
    }
    private int nextGate;


    private double[] gateDistances;

    public int getNextGate() {
        return nextGate;
    }

    public GameManager() {
        gateStack = new Stack<>();
        gateStack.push(0);
        resetGateStack();
    }
    public void startingLap(){
        //logic for beginning of the race passing the finish line to start lap 1
    }

    public void hitGate(){
        //Collision detection for each gate (track gates order, do logic (should be in order 1, 2, 3, 4, 1))
        nextGate = gateStack.peek();
        //Only needs to check one line (front) as all lines come from the center of the car and the distance from the line is +-
        if (0 < gateDistances[7] && gateDistances[7] < 1) {
            gateStack.pop();
        }
        if (gateStack.isEmpty()) {
            lapCounter++;
            resetGateStack();
            nextGate = gateStack.peek();
        }
    }

    public void lapCounter(){
        //if collision with lap gates (all 4 and back to 1) then lapCounter++
        //start lapTimer
    }

    public void lapTimer(){
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

    public void finishPosition(){
        //If finished return true
    }

    private void resetGateStack() {
        gateStack.push(3);
        gateStack.push(2);
        gateStack.push(1);
        gateStack.push(0);
    }

}
