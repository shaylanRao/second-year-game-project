package sample.models;

import javafx.scene.image.ImageView;
import sample.Main;

public class Car extends Sprite{

    private boolean racing, goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationFactor, maximumAcceleration;

    private final double decelerationFactor = 0.02;

    public Car(ImageView image) {
        super(image);
        this.setAccelerationFactor(0.11);
        this.setMaximumAcceleration(5);
    }

    public ImageView getImageView() {
        return super.getImage();
    }

    public boolean isAccelerate() {
        return accelerate;
    }

    public double getAccelerationFactor() {
        return accelerationFactor;
    }

    public void setAccelerationFactor(double accelerationFactor) {
        this.accelerationFactor = accelerationFactor;
    }

    public double getMaximumAcceleration() {
        return maximumAcceleration;
    }

    public void setMaximumAcceleration(double maximumAcceleration) {
        this.maximumAcceleration = maximumAcceleration;
    }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
    }

    public boolean isRacing() {
        return racing;
    }

    public void setRacing(boolean racing) {
        this.racing = racing;
    }

    public boolean isGoingForward() {
        return goingForward;
    }

    public void setGoingForward(boolean goingForward) {
        this.goingForward = goingForward;
    }

    public boolean isGoingBackward() {
        return goingBackward;
    }

    public void setGoingBackward(boolean goingBackward) {
        this.goingBackward = goingBackward;
    }

    public boolean isTurnRight() {
        return turnRight;
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    public boolean isTurnLeft() {
        return turnLeft;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }

    private double getCX() {
        return this.getImageView().getBoundsInLocal().getWidth() / 2;
    }

    private double getCY() {
        return this.getImageView().getBoundsInLocal().getHeight() / 2;
    }

    public double accelerationCalculator(double accelerationFactor) {
        if (this.isAccelerate()) {
            if (accelerationFactor < 2) {
                accelerationFactor += 0.3;
            }
            if(accelerationFactor < this.getMaximumAcceleration()) {
                accelerationFactor += (1/(accelerationFactor*515));
            }
        }
        else {
            if(accelerationFactor > 0) {
                if (accelerationFactor < 0.5) {
                    accelerationFactor -= this.decelerationFactor;
                }
                else {
                    accelerationFactor-= 0.05;
                }
            }

        }
        if (accelerationFactor < 0){
            accelerationFactor = 0.1;
        }

        return accelerationFactor;
    }

    public void moveCarBy(double dy) {
        if (dy == 0) {
            return;
        }

        final double cx = this.getCX();
        final double cy = this.getCY();
        final double angle = this.getImageView().getRotate();

        double angleMoveX = Math.cos(Math.toRadians(angle));
        double angleMoveY = Math.sin(Math.toRadians(angle));

        double x = (cx + this.getImageView().getLayoutX() + (dy * angleMoveX));
        double y = (cy + this.getImageView().getLayoutY() + (dy * angleMoveY));

        this.move(x, y);
    }

    private void move(double x, double y) {
        final double cx = this.getCX();
        final double cy = this.getCY();

        if (x - cx >= 0 && x + cx <= Main.maxWidth && y - cy >= 0 && y + cy <= Main.maxHeight) {
            this.getImageView().relocate(x - cx, y - cy);
        }
    }

    public void turn(double angle) {
        final double cAngle = this.getImageView().getRotate();
        angle += cAngle;

        // removed turnCarTo
        this.getImageView().setRotate(angle);
    }
}
