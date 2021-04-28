package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controllers.audio.SoundManager;

import java.util.LinkedList;

public class Car extends Sprite {

    private boolean goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationModerator;
    private double forceSpeed;
    private double speedConverter;
    private double speed, maximumSpeed, minimumSpeed;
    protected boolean powerup;
    private boolean speedBoostOn = false;
    private boolean carSpinOn = false;
    private boolean carSlideOn = false;
    private long pickedUpPwrtime = -1;
    public int playerNumber;

    private final LinkedList<Powerup> powerups;
    //    public ArrayList<Powerup> powerupsDischarge;
    public PowerUpBar powerUpBar;


    public void addPowerup(Powerup powerup) {
        if (getPowerups().size() >= 3) {
            this.powerups.pop();
            this.powerUpBar.removeFirstPowerup(playerNumber);
        }
        this.getPowerups().add(powerup);
        this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup, playerNumber);
    }

    public LinkedList<Powerup> getPowerups()
    {
        return powerups;
    }
    /**
     * Should handle powerup activate here, like changing speed of the car or something
     * */
    public void activatePowerup() {
        if (!(this.getPowerups().isEmpty())) {
            Powerup powerup = getPowerups().pop();
            if (powerup instanceof BananaPowerup) {
                System.out.println("detected banana powerup");
            } else if (powerup instanceof SpeedboosterPowerup) {
                System.out.println("detected speed boosted powerup");
            } else if (powerup instanceof OilGhostPowerup) {
                System.out.println("detected oil ghost powerup");
            }
        }
    }

    public void handleMapPowerups(Powerup powerup) {
        if (powerup.shouldCollide && collisionDetection(powerup))
        {
            SoundManager.play("prop");
            addPowerup(powerup);
            powerup.deactivate();
        }
        else if (!powerup.shouldCollide) {
            if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
                powerup.activate();
            }
        }
    }

    public Powerup usePowerup () {
        SoundManager.play("powerUp");
        double playerCarLayoutX = getImage().getLayoutX();
        double playerCarLayoutY = getImage().getLayoutY();
        double powerupWidth = powerups.getFirst().getImage().getBoundsInLocal().getWidth();
        double powerupHeight = powerups.getFirst().getImage().getBoundsInLocal().getHeight();

        double x = playerCarLayoutX - powerupWidth;
        double y = playerCarLayoutY - powerupHeight;

        if (powerups.getFirst() instanceof BananaPowerup) {
            BananaDischargePowerup drop = new BananaDischargePowerup(powerups.getFirst().getGameBackground());
            drop.render(x, y);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof OilGhostPowerup) {
            OilSpillPowerup drop = new OilSpillPowerup(powerups.getFirst().getGameBackground());
            drop.render(x, y);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof SpeedboosterPowerup) {
            movementPowerup("speedBoost");
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return null;
        }
        //had to return something, but it shouldn't get this far
        return powerups.getFirst();
    }

    public Car(Pane gameBackground, ImageView image, Settings.VehicleType vehicleType) {
        super(gameBackground, image);
        //reverse speed (HARD-CODED)
        this.setMinimumSpeed(-1.5);
        this.setAccelerationModerator(0.005);
        this.setSpeedConverter(0.09);
        this.powerups = new LinkedList<>();
        this.powerUpBar = new PowerUpBar(gameBackground);
        this.assignSpeed(vehicleType);
    }

    private void assignSpeed(Settings.VehicleType vehicleType){
        if (vehicleType == Settings.VehicleType.VEHICLE1){
            this.setMaximumSpeed(3);
        }
        else if (vehicleType == Settings.VehicleType.VEHICLE2){
            this.setMaximumSpeed(3.5);
        }
        else{
            this.setMaximumSpeed(2.8);
        }
    }


    public void setForceSpeed(double forceSpeed) {
        this.forceSpeed = forceSpeed;
    }

    public double getForceSpeed() {
        return forceSpeed;
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

    public void setSpeedConverter(double speedConverter) {
        this.speedConverter = speedConverter;
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


    public void setSpeed(double newSpeed){
        this.speed = newSpeed;
    }

    /**
     * Keeps the without the speed bounds and calls to recalculate the speed
     */
    private void moveCar(){
        //if accelerating forwards when rolling backwards, speeds it up
        if(this.isAccelerate() && this.speed <0) {
            this.speed += Math.abs(this.speed * 0.2);
        }
        this.speedCalc();
    }

    /**
     * Updates the speed by applying acceleration to the current speed
     * Applies the acceleration moderator to adjust rate of acceleration
     */
    private void speedCalc(){
        //if max speed reached, only change if slowing down
        if (this.speed >= this.maximumSpeed){
            if (this.accCalc() < 0){
                this.forceSpeed = this.forceSpeed + (this.accCalc() * accelerationModerator);
                this.speed = this.forceSpeed * this.speedConverter;
            }
        }
        //if min speed reached, only change if moving forward
        else if (this.speed <= minimumSpeed){
            if (this.accCalc() > 0){
                this.forceSpeed = this.forceSpeed + (this.accCalc() * accelerationModerator);
                this.speed = this.forceSpeed * this.speedConverter;
            }
        }
        //else just get new speed
        else{
                this.forceSpeed = this.forceSpeed + (this.accCalc() * accelerationModerator);
                this.speed = this.forceSpeed * this.speedConverter;
        }
    }


    /**
     * Applies the calculated force to the mass of the car
     * F=ma
     * a = f/m
     * @return acceleration
     */
    private double accCalc(){
        //adjusted per vehicle to change acceleration
        double mass = 1;
        return (this.longForce()/mass);
    }


    /**
     * Calculates the total longitudinal force on the car
     * @return netForce
     */
    private double longForce(){
        //braking if going forward already
        if(speed > 0 && this.isGoingBackward()){
            return(-(this.fBraking() + this.fDrag() + this.fRolling()));
        }
        //reversing
        else if (this.isGoingBackward()){
            return(this.fReverse() - ((this.fDrag()+ this.fRolling())));
        }
        //going forward
        else{
            if (this.isAccelerate()){
                return(this.fTraction() + ((this.fDrag()+ this.fRolling())));
            }
            //rolling (forwards or backwards)
            return (-(this.fDrag()+ this.fRolling()));
        }
    }

    /**
     * Calculates the tractive force generated in the engine and applied to the wheels
     * The force that moves the car forwards
     * @return thrust
     */
    private double fReverse(){
        //fixed gear speed
        return(-15);
    }


    private double fTraction(){
        double unitVector = 1;  //used for change in proportion of car size
        double engineForce = 37; // real-life m/s acceleration
        //todo if up arrow, then this, else return 0
        if(this.isAccelerate()){
            return(unitVector*engineForce);
        }
        else{
            return 0;
        }
    }

    /**
     * The force applied by the brakes
     * Used to decelerate the car
     * Constant force being applied
     * @return const
     */
    private double fBraking() {
        if (this.speed < 1) {
            return (130); //fixed bvreak force at low speed
        } else {
            return (130 * (1 / this.speed)); //variable force applied at high speed (slowly breaking)
        }
    }

    /**
     * Air resistance, resistance force
     * Squared line, starts at 0 and finishes increasingly high
     * @return speed^2*dragConst
     */
    private double fDrag(){
        double dragConst = (0.006666); //real life value
        return (forceSpeed * forceSpeed * dragConst);
    }


    /**
     * The resistive force from the tires on the road
     * Linear line, starts higher but finishes lower
     * @return Speed*rollingConstant
     */
    private double fRolling(){
        //todo need to change
        double rollConst = 0.8;
        //if rolling then stop when rolling slowly
        if(Math.abs(this.speed) < 0.25 && !(this.isGoingForward() || this.isGoingBackward())){
            return((forceSpeed)*9);
        }
        return ((forceSpeed) *rollConst);
    }




    /**
     * If function is activated, it will change the value of speedBoostOn to true
     * for a specified time period (1 second)
     */
    public void movementPowerup(String powerUp){
        switch(powerUp){
            case "carSpin":
                carSpinOn = true;
            case "speedBoost":
                speedBoostOn = true;
            case "carSlide":
                carSlideOn = true;
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        switch(powerUp){
                            case "carSpin":
                                carSpinOn = false;
                            case "speedBoost":
                                speedBoostOn = false;
                            case "carSlide":
                                carSlideOn = false;
                        }
                    }
                },
                1000
        );
    }

    public boolean isActivatedPowerup() {
        return powerup;
    }

    public void setActivatePowerup(boolean powerup) {
        this.powerup = powerup;
    }

    public long getPickedUpPwrtime()
    {
        return pickedUpPwrtime;
    }

    public void setPickedUpPwrtime(long pickedUpPwrtime)
    {
        this.pickedUpPwrtime = pickedUpPwrtime;
    }


    //todo momentum
    //inheritance for other types of vehicles


    /**
     * Gets the coordinates and angle of the car
     * Calculates the change in coordinates of where the car moves and the angle of rotation
     */
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

    public void turn(double angle) {
        double cAngle = this.getImageView().getRotate();
        if(carSpinOn) {
            //System.out.println("CARSPIN");
            this.speed = 0;
            cAngle +=11.8;
            this.getImageView().setRotate(cAngle);
        }
        else if(carSlideOn){
            this.getImageView().setRotate(cAngle);
        }
        else {
            if (cAngle > 360) {
                cAngle = cAngle - 360;
            } else if (cAngle < -360) {
                cAngle = cAngle + 360;
            }
            angle += cAngle;
            this.getImageView().setRotate(angle);
        }
    }

    /**
     * gets the speed of the car
     * @return speed
     */
    public double getForwardSpeed(){
        if (speedBoostOn){
            //speed boost 140% max speed for a second
            //todo make current speed + 50%, carry on at the same velocity after
            return (getMaxSpeed()+(getMaxSpeed()*0.4));
        }
        else if(carSpinOn){
            this.speed = 0;
        }
        else if (carSlideOn){
            return (this.speed);
        }
        else{
            this.moveCar();
        }
        return(this.speed);
    }

    /**
     * gets the turning speed of the car
     * @return speed
     */
    public double getTurningSpeed(){
        double turningSpeed;
        double currentSpeed = Math.abs(this.speed);
        if (currentSpeed < 0.2){
            return (0);
        }
        else if (currentSpeed < 1){
            return (Math.log10((1.5*currentSpeed)+0.1)+1);
        }
        turningSpeed = (((Math.log10(1.5/currentSpeed+0.3))+1)*1.2);
        return (turningSpeed);
    }


    /**
     * This function checks whether the coordinates of this Sprite intersects with the sprite passed as the parameter
     * @return bool
     * */
    public boolean collisionDetection(Sprite other) {
        double widthCar = this.getImage().getBoundsInParent().getWidth()/2;
        double heightCar = this.getImage().getBoundsInParent().getHeight()/2;

        double widthOther = other.getImage().getBoundsInParent().getWidth()/2;
        double heightOther = other.getImage().getBoundsInParent().getHeight()/2;

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


    public boolean wallCollision(double[] gateDistances){
        boolean retVal = false;

        for (int i = 0; i < gateDistances.length; i++){
            //if diagonal has crashed or if forward or backwards have crashed
            if (((i == 2 || i == 6 || i == 1 || i == 5) && gateDistances[i] < 34) || ((i == 0 || i == 7) && (gateDistances[i] < 37))){
                retVal = true;
                this.speed = 0;
                break;
            }
            else{
                retVal = false;
            }
        }
        return retVal;
    }



    /*
    1. forward acceleration
    2. deceleration
    2.5 backwards
    3. turning speed
    4. change the center of the car png
     */


}
