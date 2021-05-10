package sample.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import sample.utilities.imported.FastNoise;
import javafx.stage.Screen;

import java.util.ArrayList;

/**
 * Represents that track that the cars drive on
 */
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
    private final Line[] finWhite;

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
        finWhite = new Line[6];
        outerPoints = new ArrayList<>();
        innerPoints = new ArrayList<>();
        BuildTrack();
    }

    /**
     * Generates the track.
     */
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
            float xoff = map((float) Math.cos(a), -1, 1, 0, 200);
            float yoff = map((float) Math.sin(a), -1, 1, 0, 300);
            float theNoise = noise.GetNoise(xoff, yoff);
            float r = map(theNoise, 0, 1, (int)(Screen.getPrimary().getBounds().getHeight()*0.40), (int)(Screen.getPrimary().getBounds().getHeight()*0.45));
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
                gate.setStroke(Color.TRANSPARENT);
                gates[0] = gate;
                this.setFinLine(outerPoint, innerPoint);
            }
            if (a==1.570796326794896) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.TRANSPARENT);
                gates[1] = gate;
            }
            if (a==3.141592653589791) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.TRANSPARENT);
                gates[2] = gate;
            }
            if (a==4.712388980384686) {
                Line gate = new Line(outerPoint.getXConverted(), outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
                gate.setStroke(Color.TRANSPARENT);
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

    private void setFinLine(Point outerPoint, Point innerPoint) {
        finWhite[0] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
        finWhite[0].setStroke(Color.WHITE);
        finWhite[0].setStrokeWidth(5);

        finWhite[1] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted(), innerPoint.getXConverted(), innerPoint.getYConverted());
        finWhite[1].setStroke(Color.BLACK);
        finWhite[1].getStrokeDashArray().addAll(5.0, 15.0);
        finWhite[1].setStrokeWidth(5);


        finWhite[2] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted()-5, innerPoint.getXConverted(), innerPoint.getYConverted()-5);
        finWhite[2].setStroke(Color.WHITE);
        finWhite[2].setStrokeWidth(5);

        finWhite[3] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted()-5, innerPoint.getXConverted(), innerPoint.getYConverted()-5);
        finWhite[3].setStroke(Color.BLACK);
        finWhite[3].getStrokeDashArray().addAll(5.0, 15.0);
        finWhite[3].setStrokeDashOffset(10);
        finWhite[3].setStrokeWidth(5);

        finWhite[4] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted()-10, innerPoint.getXConverted(), innerPoint.getYConverted()-10);
        finWhite[4].setStroke(Color.WHITE);
        finWhite[4].setStrokeWidth(5);

        finWhite[5] = new Line(outerPoint.getXConverted()+5, outerPoint.getYConverted()-10, innerPoint.getXConverted(), innerPoint.getYConverted()-10);
        finWhite[5].setStroke(Color.BLACK);
        finWhite[5].getStrokeDashArray().addAll(5.0, 15.0);
        finWhite[5].setStrokeWidth(5);
    }

    public Line getFinishLine(){
        return (this.gates[0]);
    }

    public Line[] getFinWhite(){
        return (finWhite);
    }

    /**
     * Converts a value in a given range to a value in a new range.
     * @param value the original value
     * @param istart the lower bound of the input range
     * @param istop the upper bound of the input range
     * @param ostart the lower bound of the output range
     * @param ostop the upper bound of the output range
     * @return the value in the new range
     */
    private float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

}
