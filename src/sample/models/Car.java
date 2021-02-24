package sample.models;

import javafx.scene.image.ImageView;
import sample.Main;

public class Car extends Sprite{

    private boolean goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationFactor, maximumAcceleration;

    final double speedFactor = 10;

    public Car(ImageView image) {
        super(image);
        this.setAccelerationFactor(0.001);
        this.setMaximumAcceleration(speedFactor*0.3);
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


    /**
     * Explain what this function does
     * */
    public double accelerationCalculator(double accelerationFactor) {
        if (this.isAccelerate()) {
            if (accelerationFactor < (speedFactor * 0.2)){
                accelerationFactor+=(speedFactor * 0.003);
            }
            if(accelerationFactor < this.getMaximumAcceleration()) {
                this.accelerationFactor += (speedFactor * 0.02);
            }
        }
        else {
            if(accelerationFactor > 0) {
                if (accelerationFactor < speedFactor * 0.05) {
                    this.accelerationFactor -= (speedFactor * 0.0002);
                }
                else {
                    this.accelerationFactor-= (speedFactor * 0.0005);
                }
            }

        }
        if (accelerationFactor < 0){
            this.accelerationFactor = 0.001;
        }
        return accelerationFactor;
    }

    /**
     * Explain what this function does
     * */
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

    /**
     * Explain what this function does
     * */
    private void move(double x, double y) {
        final double cx = this.getCX();
        final double cy = this.getCY();

        if (x - cx >= 0 && x + cx <= Main.maxWidth && y - cy >= 0 && y + cy <= Main.maxHeight) {
            this.getImageView().relocate(x - cx, y - cy);
        }
    }

    /**
     * Explain what this function does
     * */
    public void turn(double angle) {
        final double cAngle = this.getImageView().getRotate();
        angle += cAngle;

        // removed turnCarTo
        this.getImageView().setRotate(angle);
    }


    public double getForwardVelocity(){
        return (this.accelerationCalculator(this.accelerationFactor) * 1);
    }

    public double getTurningSpeed(){
        return (this.accelerationCalculator(this.accelerationFactor) * 1.5);
    }
}