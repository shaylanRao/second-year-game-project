package sample.models;

import sample.Main;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Stack;

public class GameManager{
    final int[] millisecondsPassed = {0};
    public int lapCounter = 0;
    final int[] lap = {lapCounter};
    private final int maxLaps = Main.settings.getLaps();
    private final Stack<Integer> gateStack;
    private final int[] eachLap = new int[maxLaps];


    public GameManager() {
        gateStack = new Stack<>();
        resetGateStack();
    }


    public void setGateDistances(double[] gateDistances) {
        this.gateDistances = gateDistances;
    }


    private double[] gateDistances;

    public int getNextGate() {
        return gateStack.peek();
    }


    public void startingLap(){
        //logic for beginning of the race passing the finish line to start lap 1
        //this.lapTimer();
        //this.speedBoost
    }


    public void hitGate(){
        //Collision detection for each gate (track gates order, do logic (should be in order 1, 2, 3, 4, 1))
        //Only needs to check one line (front) as all lines come from the center of the car and the distance from the line is +-
        if (0 < gateDistances[7] && gateDistances[7] < 10) {
            gateStack.pop();
            System.out.println(Arrays.toString(eachLap));

        }
        if (gateStack.isEmpty()) {
            resetGateStack();
            lapCounterIncrement();
        }
    }


    public void lapCounterIncrement(){
        lapCounter++;
    }


    public void lapTimer(){
        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //millisecondsPassed[0]++;
                if (lap[0] != lapCounter && lapCounter < maxLaps + 1) {
                    eachLap[lapCounter - 1] = millisecondsPassed[0];
                    millisecondsPassed[0] = 0;
                    System.out.println(lapCounter);
                    lap[0] = lapCounter;
                }
                millisecondsPassed[0]++;
            }
        };
        myTimer.scheduleAtFixedRate(task,1000,1);
    }

    public void finishPosition(){
        //If finished return true
    }

    private void resetGateStack() {
        gateStack.push(0);
        gateStack.push(3);
        gateStack.push(2);
        gateStack.push(1);
        gateStack.push(0);
    }

}