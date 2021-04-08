package sample.models;

import sample.Main;

import java.util.Stack;

public class
GameManager{

    public int lapCounter = 0;
    private int maxLaps = Main.settings.getLaps();
    //Record lap times per lap (post race review thing)
    private double lapTime[];
    private Stack<Integer> gateStack;
    public void setGateDistances(double[] gateDistances) {
        this.gateDistances = gateDistances;
    }

    private double[] gateDistances;

    public int getNextGate() {
        return nextGate;
    }

    private int nextGate;

    public GameManager() {
        gateStack = new Stack<>();
        gateStack.push(0);
        resetGateStack();
    }
    public void startingLap(){
        //logic for beginning of the race passing the finish line to start lap 1
    }

    public void finishLine(){
        //Collision detection for each gate (track gates order, do logic (should be in order 1, 2, 3, 4, 1))

        nextGate = gateStack.peek();

        for (double distance: this.gateDistances) {
            if (distance < 20) {
                //then it has hit the gate
                System.out.println("hit a gate");
                gateStack.pop();
                if (gateStack.isEmpty()) {
                    lapCounter++;
                    resetGateStack();
                }
            }
        }
    }

    public void lapCounter(){
        //if collision with lap gates (all 4 and back to 1) then lapCounter++
        //start lapTimer
    }

    public void lapTimer(){
        //Do the time
    }

    public void finishPosition(){
        //If finished return true
    }

    private void resetGateStack() {
        gateStack.push(1);
        gateStack.push(2);
        gateStack.push(3);
        gateStack.push(0);
    }

}
