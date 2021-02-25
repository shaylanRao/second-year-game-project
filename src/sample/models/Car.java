package sample.models;

import javafx.scene.image.ImageView;
import sample.Main;

public class Car extends Sprite {

    private boolean goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationFactor, maximumAcceleration;
    private double velocity;

    final double speedFactor = 10;

    public Car(ImageView image) {
        super(image);
        this.setAccelerationFactor(0.001);
        this.setMaximumAcceleration(speedFactor*0.8);
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

    public double getMaxVelocity() {
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
    public double accelerationCalculator() {
        if (this.isAccelerate()) {
            this.accelerateForward();
        }
        else {
            this.decelerateForward();
        }

        if (this.velocity < 0){
            //make positive again, 0
            this.velocity = 0;
        }
        return this.velocity;
    }

    private void accelerateForward(){
        if(this.velocity < this.getMaxVelocity()) {
            //if acceleration is starting from 0
            if (this.velocity == 0){
                this.velocity = 0.003;
            }
            //this.accelerationFactor = 9.4*(1-Math.exp(0.01*this.velocity))+0.002;
            //this.accelerationFactor += -((Math.log(1-(this.velocity / 1)))/0.3);
            this.accelerationFactor = (1/(Math.log10(this.velocity)+3.2))-0.25;
            this.velocity += this.accelerationFactor;
        }
    }

    private void decelerateForward(){
        if(this.velocity > 0) {
            if (this.velocity < speedFactor * 0.05) {
                this.velocity -= (speedFactor * 0.001);
            }
            else {
                this.velocity -= (speedFactor * 0.005);
            }

        }
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
        System.out.println(this.velocity);
        return (this.accelerationCalculator() * 1);
    }

    public double getTurningSpeed(){
        return (this.accelerationCalculator() * 1.5);
    }

    //TODO collisionHandler(object 2)
        //Handles collecting OR smashing into powerups OR bump into wall OR bump into car


    //TODO powerup pickup
        //store powerup


    //TODO powerup crash
        //make car spin and slowdown

    /**
     * This function checks whether the coordinates of this Sprite intersects with the sprite passed as the parameter
     * @param other
     * */
    public boolean collisionDetection(Sprite other) {
        double widthCar = this.getImage().getBoundsInLocal().getWidth()/2;
        double heightCar = this.getImage().getBoundsInLocal().getHeight()/2;
        double widthOther = other.getImage().getBoundsInLocal().getWidth()/2;
        double heightOther = other.getImage().getBoundsInLocal().getHeight()/2;
        double cxCar = this.getImage().getLayoutX();
        double cyCar = this.getImage().getLayoutY();
        double cxOther = other.getImage().getLayoutX();
        double cyOther = other.getImage().getLayoutY();

        boolean hasCollided = Math.abs(cyCar - cyOther) <= ((heightCar + heightOther));
        if (cxCar > cxOther) {
            if (cxCar - cxOther + widthOther <= widthCar+widthOther) {
                return hasCollided;
            }
        } else {
            if (cxOther- cxCar - widthOther <= widthCar+widthOther) {
                return hasCollided;
            }
        }
        return false;

    }



}