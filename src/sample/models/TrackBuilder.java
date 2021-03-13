package sample.models;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import sample.utilities.FastNoise;
import sample.utilities.Mapper;

import java.util.ArrayList;

public class TrackBuilder {
    private final int trackWidth = 150;
    private ArrayList<Line> trackLines;

    public ArrayList<Line> getTrackLines() {
        return trackLines;
    }
    public void BuildTrack() {
        //setup perlin noise generator
        FastNoise noise = new FastNoise();
        noise.SetNoiseType(FastNoise.NoiseType.Perlin);
        //generate points
        ArrayList<Point> outerPoints = new ArrayList<>();
        ArrayList<Point> innerPoints = new ArrayList<>();
        double x1, y1, x2, y2;
        for (double a = 0; a < 6.3; a += 0.1) {
            float xoff = Mapper.map((float) Math.cos(a), -1, 1, 0, 200);
            float yoff = Mapper.map((float) Math.sin(a), -1, 1, 0, 200);
            float theNoise = noise.GetNoise(xoff, yoff);
            float r = Mapper.map(theNoise, 0, 1, 300, 500);
            x1 = r * Math.cos(a)*2;
            y1 = r * Math.sin(a);
            Point outerPoint = new Point(x1, y1);
            outerPoints.add(outerPoint);

            x2 = (r-(trackWidth/2)) * Math.cos(a)*2;
            y2 = (r-(trackWidth/2)) * Math.sin(a);
            Point innerPoint = new Point(x2, y2);
            innerPoints.add(innerPoint);

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
        return;
    }
}
