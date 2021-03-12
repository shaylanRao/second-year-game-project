package sample.models;

import javafx.scene.image.ImageView;
import sample.Main;
import javafx.scene.layout.BorderPane;
import java.util.Timer;

public class Car extends Sprite {

    private boolean goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationModerator;
    private double speed, maximumSpeed, minimumSpeed;
    private boolean powerup;
    final double SPEEDFACTOR = 6;
    private boolean speedBoostOn = false;

    public Car(BorderPane gameBackground, ImageView image) {
        super(gameBackground, image);
        this.setMaximumSpeed(SPEEDFACTOR *0.7);
        //reverse speed (HARD-CODED)
        this.setMinimumSpeed(-2.5);
        this.setAccelerationModerator(SPEEDFACTOR /20000);
    }

    public ImageView getImageView() {
        return super.getImage();
    }

    public boolean isAccelerate() {
        return accelerate;
    }

    public double getMaxSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public double getMinSpeed() {
        return minimumSpeed;
    }

    public void setMinimumSpeed(double maximumSpeed) {
        this.minimumSpeed = maximumSpeed;
    }

    public void setAccelerationModerator(double accelerationModerator){ this.accelerationModerator = accelerationModerator; }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
    }

    public void setGoingForward(boolean goingForward) {
        this.goingForward = goingForward;
    }


    public boolean isGoingForward() {
        return goingForward;
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
     * Either runs functions to accelerate/decelerate the car or let it roll
     * */
    public double speedCalculator() {
        if (this.isAccelerate() || this.isGoingBackward()) {
            this.accelerateForward();
        }
        else {
            this.decelerateForward();
        }
        return this.speed;
    }

    /**
     * Keeps the without the speed bounds and calls to recalculate the speed
     */
    //todo make private
    public void accelerateForward(){
        if(this.speed < this.getMaxSpeed() && this.speed > this.getMinSpeed()) {
            //if acceleration is starting from 0
            if (this.speed == 0){
                this.speed = 0.003;
            }
            this.newSpeedCalc();
        }
    }

    /**
     * Updates the speed by applying acceleration to the current speed
     * Applies the acceleration moderator to adjust rate of acceleration
     */
    private void newSpeedCalc(){
        this.speed = this.speed + (this.newAccCalc() * this.accelerationModerator);
    }


    /**
     * Applies the calculated force to the mass of the car
     * F=ma
     * @return acceleration
     */
    private double newAccCalc(){
        //f=ma
        double mass = 1;
        return (this.longForce()/mass);
    }


    /**
     * Calculates the total longitudinal force on the car
     * @return netForce
     */
    private double longForce(){
        //braking if going forward already
        if (this.isGoingBackward()){
            return(-(this.fBraking() + this.fDrag() + this.fRolling()));
        }
        else{
            return(this.fTraction() - (this.fDrag() + this.fRolling()));
        }
    }

    /**
     * Calculates the tractive force generated in the engine and applied to the wheels
     * The force that moves the car forwards
     * @return thrust
     */
    private double fTraction(){
        double unitVector = 1;
        double engineForce = 37; // real-life m/s acceleration
        //todo if up arrow, then this, else return 0
        return(unitVector*engineForce);
    }



    //todo needs to break less, more roll
    /**
     * The force applied by the brakes
     * Used to decelerate the car
     * Constant force being applied
     * @return const
     */
    private double fBraking(){
        return (130);
    }

    /**
     * Air resistance, resistance force
     * Squared line, starts at 0 and finishes increasingly high
     * @return speed^2*dragConst
     */
    private double fDrag(){
        double dragConst = 0.015;
        return (this.speed * this.speed * dragConst);
    }

    //todo Needs to roll more

    /**
     * The resistive force from the tires on the road
     * Linear line, starts higher but finishes lower
     * @return Speed*rollingConstant
     */
    private double fRolling(){
        double rollConst = 0.45;
        return (this.speed *rollConst);
    }

    /**
     * Controls the way the car rolls when not accelerating
     * decelerates from current speed to 0 when no buttons being pressed
     */

    //todo make private
    public void decelerateForward() {
        //if the speed is the speed is << then just make it 0
        if (this.speed < 0.005 && this.speed > -0.005) {
            this.speed = 0;
            //if rolling forward, roll this way
        } else if (this.speed > 0) {
            if (this.speed < SPEEDFACTOR * 0.05) {
                this.speed -= (SPEEDFACTOR * 0.001);
            } else {
                this.speed -= (SPEEDFACTOR * 0.005);
            }
        //if rolling backwards, roll this way
        } else {
            if (this.speed > -SPEEDFACTOR * 0.05) {
                this.speed += (SPEEDFACTOR * 0.001);
            } else {
                this.speed += (SPEEDFACTOR * 0.005);
            }
        }
    }

    public boolean isActivatedPowerup() {
        return powerup;
    }

    public void setActivatePowerup(boolean powerup) {
        this.powerup = powerup;
    }


    //todo start-off boost
    //


    //todo momentum
    //inheritance for other types of vehicles


    //todo speedBoost
    public void activateSpeedBoost(){
        this.speedBoostOn = true;
    }

    /**
     * If funtion is activated, it will change the value of speedBoostOn to true
     * for a specified time period (1 second)
     */

    public void speedBoost(){
        speedBoostOn = true;
        if (speedBoostOn){
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            speedBoostOn = false;
                        }
                    },
                    1000
            );
        }
    }



    /**
     * Gets the coordinates and angle of the car
     * Calculates the change in coordinates of where the car moves and the angle of rotation
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
     * Moves the car by calculates amount from 'moveCarBy'
     * */
    private void move(double x, double y) {
        final double cx = this.getCX();
        final double cy = this.getCY();

        if (x - cx >= 0 && x + cx <= Main.maxWidth && y - cy >= 0 && y + cy <= Main.maxHeight) {
            this.getImageView().relocate(x - cx, y - cy);
        }
    }

    /**
     * Rotates the image of the car
     * */

    //todo fine-tune
    public void turn(double angle) {
        double cAngle = this.getImageView().getRotate();
        if (cAngle > 360) {
            cAngle = cAngle - 360;
        } else if (cAngle < -360) {
            cAngle = cAngle + 360;
        }
        angle += cAngle;
        this.getImageView().setRotate(angle);
    }

    /**
     * gets the speed of the car
     * @return speed
     */
    public double getForwardSpeed(){
        //System.out.println(this.speed);
        if (speedBoostOn == true){
            //speed boost 140% max speed for a second
            return (getMaxSpeed()+(getMaxSpeed()*0.4));
        }
        else{
            return (this.speedCalculator());
        }
    }

    /**
     * gets the turning speed of the car
     * @return speed
     */
    public double getTurningSpeed(){
        double turningSpeed;
        if (this.speed < 0.7){
            return (0.2*this.speed *this.speed);
        }
        turningSpeed = (((Math.log10(this.getForwardSpeed()/2))+0.5)*4);

        return (turningSpeed);
    }


    /**
     * This function checks whether the coordinates of this Sprite intersects with the sprite passed as the parameter
     * @param other
     * @return bool
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

    /*
    1. forward acceleration
    2. deceleration
    2.5 backwards
    3. turning speed


    4. change the center of the car png


     */


}