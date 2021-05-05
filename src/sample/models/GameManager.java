package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameManager{
    final int[] millisecondsPassed = {0};
    final int[] millisecondsPassedP2 = {0};
    public int lapCounter = 0;
    final int[] lap = {lapCounter};
    private final int maxLaps = Main.settings.getLaps();
    private final Stack<Integer> gateStack;
    private final int[] eachLap = new int[maxLaps];
    private double result=0;
    private PlayerCar player;

    private TimerBar timerbar;
    private TimerBar timerbar1;
    private TimerBar timerbar2;


    public double getResult1() {
        return result1;
    }

    private double result1=0;
    private double tt=0;
    private double tt1=0;
    private double ttp2=0;
    private double ttp21=0;


    Pane gameBackground;


    public GameManager(Pane gameBackground, PlayerCar playercar) {
        this.gameBackground = gameBackground;
        player = playercar;
        gateStack = new Stack<>();
        resetGateStack();
        updateBar(95, 80);
        timerRender();
    }


    public void setGateDistances(double[] gateDistances) {
        this.gateDistances = gateDistances;
    }


    private double[] gateDistances;

    public int getNextGate() {
        return gateStack.peek();
    }


    public void hitGate(){
        //Collision detection for each gate (track gates order, do logic (should be in order 1, 2, 3, 4, 1))
        //Only needs to check one line (front) as all lines come from the center of the car and the distance from the line is +-
        timerRender();
        if (0 < gateDistances[7] && gateDistances[7] < 10) {
            gateStack.pop();
        }
        if (gateStack.isEmpty()) {
            resetGateStack();
            lapCounterIncrement();
        }
    }


    public void lapCounterIncrement(){
        lapCounter++;
        this.updateBar(getLoc()[0], getLoc()[1]);
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
                    lap[0] = lapCounter;
                }
                millisecondsPassed[0]++;

                result = TimeUnit.MILLISECONDS.toSeconds(millisecondsPassed[0]);
            }

        };
        //initial delay
        myTimer.scheduleAtFixedRate(task,4300,1);
    }


    //for Timer
    Image timer[] = new Image[]{
            new Image("file:src/sample/resources/images/numbers/0.png"),
            new Image("file:src/sample/resources/images/numbers/1.png"),
            new Image("file:src/sample/resources/images/numbers/2.png"),
            new Image("file:src/sample/resources/images/numbers/3.png"),
            new Image("file:src/sample/resources/images/numbers/4.png"),
            new Image("file:src/sample/resources/images/numbers/5.png"),
            new Image("file:src/sample/resources/images/numbers/6.png"),
            new Image("file:src/sample/resources/images/numbers/7.png"),
            new Image("file:src/sample/resources/images/numbers/8.png"),
            new Image("file:src/sample/resources/images/numbers/9.png"),
    };

    private int[] getLoc(){
        int[] location = new int[2];
        if(player.getCarNumber() == 1){
            location[0] = 95;
        }
        else{
            location[0] = 700;
        }
        location[1] = 80;
        return(location);
    }


private int index = (int)result;

    public void timerRender(){
        if (!(index >= result)) {
            int second1 = 0;//0XX
            int second2 = 0;//X0X
            int second3 = 0; //XX0

            if (index < 10) {
                second1 = 0;
                second3 = index;
                second2 = 0;
            } else if (index < 100) {
                second1 = 0;
                second3 = index % 10;
                second2 = index / 10;
            } else if (index < 1000) {
                second1 = index / 100;
                second2 = (index % 100) / 10;
                second3 = (index % 100) % 10;
            }
            if (timerbar != null) timerbar.deactivate();
            if (timerbar1 != null) timerbar1.deactivate();
            if (timerbar2 != null) timerbar2.deactivate();
            timerbar = new TimerBar(gameBackground, new ImageView(timer[second1]));
            timerbar1 = new TimerBar(gameBackground, new ImageView(timer[second2]));
            timerbar2 = new TimerBar(gameBackground, new ImageView(timer[second3]));

            if (player.getCarNumber() ==1 ){
                timerbar.render(95, 80);
                timerbar1.render(95 + 80, 80);
                timerbar2.render(95 + 160, 80);
            }
            else {
                timerbar.render(1500, 80);
                timerbar1.render(1500 + 80, 80);
                timerbar2.render(1500 + 160, 80);
            }
        }
        index = (int) result;
    }



    public int totalTime(){
        int totalTimeElapsed = 0;
        for (int i = 0; i < maxLaps; i++) {
            totalTimeElapsed += eachLap[i];
        }
        return totalTimeElapsed;
    }


    private void resetGateStack() {
        gateStack.push(0);
        gateStack.push(3);
        gateStack.push(2);
        gateStack.push(1);
        gateStack.push(0);
    }

    Image lapWord = new Image("file:src/sample/resources/images/LapCounter/lap.png");

    public void wordBar(double x, double y) {
        LapBar lapBar = new LapBar(gameBackground, new ImageView(lapWord));
        lapBar.render(x, y);
    }

    Image[] updateNumbers = new Image[]{
            new Image("file:src/sample/resources/images/LapCounter/1.png"),
            new Image("file:src/sample/resources/images/LapCounter/2.png"),
            new Image("file:src/sample/resources/images/LapCounter/3.png"),
            new Image("file:src/sample/resources/images/LapCounter/4.png"),
            new Image("file:src/sample/resources/images/LapCounter/5.png"),
            new Image("file:src/sample/resources/images/LapCounter/6.png"),
            new Image("file:src/sample/resources/images/LapCounter/7.png"),
            new Image("file:src/sample/resources/images/LapCounter/8.png"),
            new Image("file:src/sample/resources/images/LapCounter/9.png"),
            new Image("file:src/sample/resources/images/LapCounter/10.png")
    };

    public void updateBar(double x, double y) {
        if (lapCounter==0){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[0]));
            lapBar.render(x, y);
        }else if (lapCounter==1){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[1]));
            lapBar.render(x, y);
        }else if (lapCounter==2){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[2]));
            lapBar.render(x, y);
        }else if (lapCounter==3){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[3]));
            lapBar.render(x, y);
        }else if (lapCounter==4){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[4]));
            lapBar.render(x, y);
        }else if (lapCounter==5){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[5]));
            lapBar.render(x, y);
        }else if (lapCounter==6){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[6]));
            lapBar.render(x, y);
        }else if (lapCounter==7){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[7]));
            lapBar.render(x, y);
        }else if (lapCounter==8){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[8]));
            lapBar.render(x, y);
        }else if (lapCounter==9){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[9]));
            lapBar.render(x, y);
        }else if (lapCounter==10){
            LapBar lapBar = new LapBar(gameBackground, new ImageView(updateNumbers[10]));
            lapBar.render(x, y);
        }

    }

    Image[] fixNumbers = new Image[]{
            new Image("file:src/sample/resources/images/LapCounter/max1.png"),
            new Image("file:src/sample/resources/images/LapCounter/max2.png"),
            new Image("file:src/sample/resources/images/LapCounter/max3.png"),
            new Image("file:src/sample/resources/images/LapCounter/max4.png"),
            new Image("file:src/sample/resources/images/LapCounter/max5.png"),
            new Image("file:src/sample/resources/images/LapCounter/max6.png"),
            new Image("file:src/sample/resources/images/LapCounter/max7.png"),
            new Image("file:src/sample/resources/images/LapCounter/max8.png"),
            new Image("file:src/sample/resources/images/LapCounter/max9.png"),
            new Image("file:src/sample/resources/images/LapCounter/max10.png")
    };

    public void fixBar(double x, double y){
        if (maxLaps == 1) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[0]));
            lapBar.render(x, y);
        } else if (maxLaps == 2) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[1]));
            lapBar.render(x, y);
        } else if (maxLaps == 3) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[2]));
            lapBar.render(x, y);
        } else if (maxLaps == 4) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[3]));
            lapBar.render(x, y);
        } else if (maxLaps == 5) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[4]));
            lapBar.render(x, y);
        } else if (maxLaps == 6) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[5]));
            lapBar.render(x, y);
        } else if (maxLaps == 7) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[6]));
            lapBar.render(x, y);
        } else if (maxLaps == 8) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[7]));
            lapBar.render(x, y);
        } else if (maxLaps == 9) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[8]));
            lapBar.render(x, y);
        } else if (maxLaps == 10) {
            LapBar lapBar = new LapBar(gameBackground, new ImageView(fixNumbers[9]));
            lapBar.render(x, y);
        }


    }

    public boolean finishedLaps(){
        return lapCounter == maxLaps;
    }


}

