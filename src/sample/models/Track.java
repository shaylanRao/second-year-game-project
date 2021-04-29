package sample.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import sample.utilities.FastNoise;
import sample.utilities.Mapper;

import java.util.ArrayList;

public class Track {

    private static final int trackWidth = 220;
    /**
     * Determines how many powerups should be spawned. The greater the spawnFactor, the fewer powerups
     */
    private static final int spawnFactor = 5;
    private ArrayList<Point> powerupSpawns;
    private final ArrayList<Point> outerPoints;
    private final ArrayList<Point> innerPoints;
    private ArrayList<Line> trackLines;


    public Line[] getGates() {
        return gates;
    }

    private final Line[] gates;

    /**
     * @return the list of the PathElement's making up the outer track. This gets used to create a Path object in RandomTrackScreen.java
     */
    public ArrayList<PathElement> getOuterPathElems() {
        ArrayList<PathElement> outerPathElems = new ArrayList<>();
        for (int i = 0; i< outerPoints.size(); i++) {
            if (i == 0) {
                outerPathElems.add(new MoveTo(outerPoints.get(0).getXConverted(), outerPoints.get(0).getYConverted()));
            } else {
                outerPathElems.add(new LineTo(outerPoints.get(i).getXConverted(), outerPoints.get(i).getYConverted()));
            }
        }
        return outerPathElems;
    }

    /**
     * @return the list of the PathElement's making up the inner track. This gets used to create a Path object in RandomTrackScreen.java
     */
    public ArrayList<PathElement> getInnerPathElems() {
        ArrayList<PathElement> innerPathElems = new ArrayList<>();
        for (int i = 0; i< outerPoints.size(); i++) {
            if (i == 0) {
                innerPathElems.add(new MoveTo(innerPoints.get(0).getXConverted(), innerPoints.get(0).getYConverted()));
            } else {
                innerPathElems.add(new LineTo(innerPoints.get(i).getXConverted(), innerPoints.get(i).getYConverted()));
            }
        }
        return innerPathElems;
    }

    public ArrayList<Point> getPowerupSpawns() {
        return powerupSpawns;
    }

    public ArrayList<Line> getTrackLines() {
        return trackLines;
    }

    public Track() {
        gates = new Line[4];
        outerPoints = new ArrayList<>();
        innerPoints = new ArrayList<>();
        BuildTrack();
    }

    private void BuildTrack() {
        //setup perlin noise generator
        FastNoise noise = new FastNoise();
        noise.SetNoiseType(FastNoise.NoiseType.Perlin);
        //generate points
        powerupSpawns = new ArrayList<>();
        double x1, y1, x2, y2, x3, y3;
        int counter = 0;
        for (double a = 0; a < 2*Math.PI; a += Math.toRadians(5)) {
            counter ++;
            float xoff = Mapper.map((float) Math.cos(a), -1, 1, 0, 200);
            float yoff = Mapper.map((float) Math.sin(a), -1, 1, 0, 200);
            float theNoise = noise.GetNoise(xoff, yoff);
            float r = Mapper.map(theNoise, 0, 1, 300, 400);
            x1 = r * Math.cos(a)*2;
            y1 = r * Math.sin(a);
            Point outerPoint = new Point(x1, y1);
            outerPoints.add(outerPoint);

            x2 = (r-(trackWidth/2)) * Math.cos(a)*2;
            y2 = (r-(trackWidth/2)) * Math.sin(a);
            Point innerPoint = new Point(x2, y2);
            innerPoints.add(innerPoint);

            if (a==0) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.RED);
                gates[0] = gate;
            }
            if (a==1.570796326794896) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.BLUE);
                gates[1] = gate;
            }
            if (a==3.141592653589791) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.GREEN);
                gates[2] = gate;
            }
            if (a==4.712388980384686) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.YELLOW);
                gates[3] = gate;
            }

            if (counter % spawnFactor == 0) {
                //generate points halfway through track for powerups to spawn
                double powerupNoise = (Math.random() - 0.5)*20;
                x3 = (r-(trackWidth/4)) * Math.cos(a)*2 + powerupNoise;
                y3 = (r-(trackWidth/4)) * Math.sin(a) + powerupNoise;
                Point spawnPoint = new Point(x3, y3);
                powerupSpawns.add(spawnPoint);
            }
        }
        //generate lines
        ArrayList<Line> outerLines = new ArrayList<>();
        ArrayList<Line> innerLines = new ArrayList<>();
        for (int i = 1; i < outerPoints.size(); i++) {
            if (i== outerPoints.size()-1) {
                Line outerLine = new Line(outerPoints.get(i).getXConverted(),outerPoints.get(i).getYConverted(),
                        outerPoints.get(0).getXConverted(), outerPoints.get(0).getYConverted());
                Line innerLine = new Line(innerPoints.get(i).getXConverted(), innerPoints.get(i).getYConverted(),
                        innerPoints.get(0).getXConverted(), innerPoints.get(0).getYConverted());
                outerLines.add(outerLine);
                innerLines.add(innerLine);
            }
            Line outerLine = new Line(outerPoints.get(i - 1).getXConverted(), outerPoints.get(i - 1).getYConverted(),
                    outerPoints.get(i).getXConverted(), outerPoints.get(i).getYConverted());
            Line innerLine = new Line(innerPoints.get(i - 1).getXConverted(), innerPoints.get(i - 1).getYConverted(),
                    innerPoints.get(i).getXConverted(), innerPoints.get(i).getYConverted());
            outerLines.add(outerLine);
            innerLines.add(innerLine);
        }
        outerLines.addAll(innerLines);
        this.trackLines=outerLines;
    }

    public Line getFinishLine(){
        return (this.gates[0]);
    }
}
