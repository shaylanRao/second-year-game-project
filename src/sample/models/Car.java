package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controllers.audio.SoundManager;

import java.lang.reflect.Array;
import java.util.Arrays;
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
    private final double carHeight = getCarHeightWidth()[0];
    private final double carWidth = getCarHeightWidth()[1];


    private final LinkedList<Powerup> powerups;
    public PowerUpBar powerUpBar;

    /**
     * Checks if a player has less than 3 power-ups. If there are less than 3, then the new power-up is added to the linked list and 
     * the power-up bar. Otherwise, it removes the least recent power-up and adds it at the end.
     * @param powerup
     * @return void
     */
    public void addPowerup(Powerup powerup) {
        if (getPowerups().size() >= 3) {
            this.powerups.pop();
            this.powerUpBar.removeFirstPowerup(playerNumber);
        }
        this.getPowerups().add(powerup);
        this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup, playerNumber);
    }

    /**
     * Getter method for the power-ups stored in each player's list.
     * @return LinkedList<Powerup>
     */
    public LinkedList<Powerup> getPowerups()
    {
        return powerups;
    }

    /**
     * Checks if a player has passed through a power-up on the track, passes the power-up to addPowerup(), and deactivates the power-up.
     * After 7 seconds, the power-up is set to re-spawn on the screen as long as there is no player in the same spot.
     * @param powerup
     * @return void
     */
    public void handleMapPowerups(Powerup powerup) {
        if (powerup.shouldCollide && collisionDetection(powerup))
        {
            SoundManager.play("prop");
            addPowerup(powerup);
            powerup.deactivate();
        }
        else if (!powerup.shouldCollide) {
            if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
                if(!collisionDetection(powerup)) {
                    powerup.activate();
                }
            }
        }
    }

    /**
     * This method handles power-ups when a player chooses to use them. Thus, it checks the power-up that it is being used and renders 
     * the new object if it is the case. (banana peel when using a banana, etc)
     * @return Powerup
     */
    public Powerup usePowerup () {
        SoundManager.play("powerUp");
        if (powerups.getFirst() instanceof BananaPowerup) {
            BananaDischargePowerup drop = new BananaDischargePowerup(powerups.getFirst().getGameBackground());
            drop.render(getPowerupLoc()[0], getPowerupLoc()[1]);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof OilGhostPowerup) {
            OilSpillPowerup drop = new OilSpillPowerup(powerups.getFirst().getGameBackground());
            drop.render(getPowerupLoc()[0], getPowerupLoc()[1]);
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

    /**
     * Renders the activated power-ups behind the car.
     * @return double[]
     */
    private double[] getPowerupLoc(){
        double[] location = new double[2];
        double hyp = carHeight*2;
        car1Angle = this.getImageView().getRotate();
        if ((car1Angle > -90 && car1Angle < 90) || car1Angle < -270 || car1Angle > 270) {
            location[0] = Math.cos( Math.toRadians(car1Angle)) * hyp + this.getImage().getLayoutX() + carWidth;
            location[1] = (Math.sin( Math.toRadians(car1Angle)) * hyp) + this.getImage().getLayoutY();
        } else {
            location[0] = Math.cos( Math.toRadians(car1Angle)) * hyp + this.getImage().getLayoutX();
            location[1] = (Math.sin( Math.toRadians(car1Angle)) * hyp) + this.getImage().getLayoutY();
        }

        return location;
    }


    public Car(Pane gameBackground, ImageView image, Settings.VehicleType vehicleType) {
        super(gameBackground, image, 0.8);
        //reverse speed (HARD-CODED)
        this.setMinimumSpeed(-1.5);
        this.setSpeedConverter(0.09);
        this.powerups = new LinkedList<>();
        this.powerUpBar = new PowerUpBar(gameBackground);
        this.assignAttributes(vehicleType);
    }

    private void assignAttributes(Settings.VehicleType vehicleType){
        //standard car
        if (vehicleType == Settings.VehicleType.VEHICLE1){
            this.setMaximumSpeed(3);
            this.setAccelerationModerator(0.005);
            this.setTurningSpeedModerator(1);
        }
        //sports car
        else if (vehicleType == Settings.VehicleType.VEHICLE2){
            this.setMaximumSpeed(3.5);
            this.setAccelerationModerator(0.003);
            this.setTurningSpeedModerator(0.93);
        }
        //muscle car
        else{
            this.setMaximumSpeed(2.8);
            this.setAccelerationModerator(0.006);
            this.setTurningSpeedModerator(1.2);
        }
    }

    public double getMass() {
        return mass;
    }

    private double mass;

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
        this.mass = 1;
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
            return(this.fReverse());
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

    /**
     * Method that states whether a power-up had been activated by pressing the power-up button or not.
     * @return boolean - true or false
     */
    public boolean isActivatedPowerup() {
        return powerup;
    }

    /**
     * Sets the state of the power-up to true or false.
     * @param powerup - boolean
     * @return void
     */
    public void setActivatePowerup(boolean powerup) {
        this.powerup = powerup;
    }

    /**
     * Returns the time a power-up has been activated.
     * @return long
     */
    public long getPickedUpPwrtime()
    {
        return pickedUpPwrtime;
    }

    /**
     * Sets the time a power-up has been activated.
     * @return void
     */
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

    public void setTurningSpeedModerator(double turningSpeedModerator) {
        this.turningSpeedModerator = turningSpeedModerator;
    }

    /**
     * gets the turning speed of the car
     * @return speed
     */

    private double turningSpeedModerator;

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
        return (turningSpeed*turningSpeedModerator);
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

    public boolean testCollision(ProjectionRectangle A, ProjectionRectangle B){
        // Test collisions between two Shapes: they can be any child of Shape (Circle or Polygon)
        CollisionNode[] axes = concatenate(A.getAxes(), B.getAxes()); // Get the array of all the axes to project the shapes along

        for (CollisionNode axis: axes) { // Loop over the axes
            // project both Shapes onto the axis
            Projection pA = project(A, axis);
            Projection pB = project(B, axis);
            // do the projections overlap?
            if (!pA.overlap(pB)) {
                // If they don't, the shapes don't either so return false
                return false;
            }
        }

        // All the projections overlap: the shapes collide -> return True
        return true;
    }

    private static Projection project(ProjectionRectangle a, CollisionNode axis) {
        // Project the shapes along the axis
        double min = axis.dot(a.getNode(0, axis)); // Get the first min
        double max = min;
        for (int i = 1; i < a.getNumOfNodes(); i++) {
            double p = axis.dot(a.getNode(i, axis)); // Get the dot product between the axis and the node
            if (p < min) {
                min = p;
            } else if (p > max) {
                max = p;
            }
        }
        return new Projection(min, max);
    }

    public static CollisionNode[] concatenate (CollisionNode[] a, CollisionNode[] b) {
        // Concatenate the two arrays of nodes
        int aLen = a.length;
        int bLen = b.length;

        CollisionNode[] c = (CollisionNode[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }


    public boolean wallCollision(double[] gateDistances){
        boolean retVal = false;
        double diagLen = (Math.sqrt((Math.pow(this.carHeight, 2) + Math.pow(this.carWidth, 2))/4));

        for (int i = 0; i < gateDistances.length; i++){
            //if diagonal has crashed or if forward or backwards have crashed
            if (((i == 2 || i == 6 || i == 1 || i == 5) && gateDistances[i] < diagLen) || ((i == 0 || i == 7) && (gateDistances[i] < carHeight/2))){
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


    public double[] getCarHeightWidth(){
        double[] heightWidth = new double[2];
        heightWidth[0] = (this.getImageView().getBoundsInParent().getHeight());
        heightWidth[1] = (this.getImageView().getBoundsInParent().getWidth());
        return heightWidth;
    }


    private double car2Mass;
    private double car2Force;
    private double car2Angle;

    private double car1Mass;
    private double car1Force;
    private double car1Angle;

    public double[][] momCollCalc(PlayerCar car1, PlayerCar car2){
        double[][] carsMomentum = new double[2][2];
        this.car2Mass = car2.getMass();
        this.car2Force = car2.getForceSpeed();
        this.car2Angle = car2.getImageView().getRotate();

        this.car1Mass = car1.getMass();
        this.car1Force = car1.getForceSpeed();
        this.car1Angle = car1.getImageView().getRotate();


        if(car2Angle < 0){
            car2Angle += 360;
        }
        if(car1Angle < 0){
            car1Angle += 360;
        }

        this.setPostVels();

        this.postVel[0] = this.carsPostVels[0];
        this.postVel[1] = this.carsPostVels[1];
        this.postVel[2] = this.carsPostVels[2];
        this.postVel[3] = this.carsPostVels[3];

        this.setPostMag();

        carsMomentum[0][0] = this.postMag[0];  //Car 1 Post Force
        carsMomentum[0][1] = getPostAngle(1);  //Car 1 Post Angle
        carsMomentum[1][0] = this.postMag[1];  //Car 2 Post Force
        carsMomentum[1][1] = getPostAngle(2);  //Car 2 Post Angle
//        System.out.println("________________");
//        System.out.println("CAR 1 ANGLE " + carsMomentum[0][1]);
//        System.out.println("CAR 2 ANGLE " + carsMomentum[1][1]);
//        System.out.println("CAR 1 MAG " + carsMomentum[0][0]);
//        System.out.println("CAR 2 MAG " + carsMomentum[1][0]);
//        System.out.println("________________");


        double angDif = angleCorrection(carsMomentum[0][1] - car1Angle);

        if((carsMomentum[0][1] < car1Angle +90) && (carsMomentum[0][1] > car1Angle - 90)){
            System.out.println("CASE 1");
            carsMomentum[0][0] =  carsMomentum[0][0] * Math.cos( Math.toRadians(angDif) );
        }
        else if((carsMomentum[0][1]+360 < car1Angle +90) && (carsMomentum[0][1]+360 > car1Angle - 90)){
            System.out.println("CASE 2");
            carsMomentum[0][0] =  carsMomentum[0][0] * Math.cos( Math.toRadians(angDif) );
        }
        else if((carsMomentum[0][1]-360 < car1Angle +90) && (carsMomentum[0][1]-360 > car1Angle - 90)){
            System.out.println("CASE 3");
            carsMomentum[0][0] =  carsMomentum[0][0] * Math.cos( Math.toRadians(angDif) );
        }
        else{
            if (car1Angle > 0 && car2Angle < 90){
                System.out.println("CASE 4 other");
                carsMomentum[0][0] =  -carsMomentum[0][0] * Math.cos( Math.toRadians(angDif) );
            }
            else{
                carsMomentum[0][0] =  carsMomentum[0][0] * Math.cos( Math.toRadians(angDif) );
                System.out.println("CASE 4");
            }
        }


        angDif = angleCorrection(carsMomentum[1][1] - car2Angle);

        if((carsMomentum[1][1] < car2Angle +90) && (carsMomentum[1][1] > car2Angle - 90)){
            if (car2Angle > 0 && car2Angle < 90){
                System.out.println("CASE 1 other");
                carsMomentum[1][0] =  -carsMomentum[1][0] * Math.cos( Math.toRadians(angDif) );
            }
            else{
                carsMomentum[1][0] =  carsMomentum[1][0] * Math.cos( Math.toRadians(angDif) );
                System.out.println("CASE 1");
            }
        }
        else if((carsMomentum[1][1]+360 < car2Angle +90) && (carsMomentum[1][1]+360 > car2Angle - 90)){
            System.out.println("CASE 2");
            carsMomentum[1][0] =  carsMomentum[1][0] * Math.cos( Math.toRadians(angDif) );
        }
        else if((carsMomentum[1][1]-360 < car2Angle +90) && (carsMomentum[1][1]-360 > car2Angle - 90)){
            System.out.println("CASE 3");
            carsMomentum[1][0] =  carsMomentum[1][0] * Math.cos( Math.toRadians(angDif) );
        }
        else{
            System.out.println("CASE 4");
            carsMomentum[1][0] =  carsMomentum[1][0] * Math.cos( Math.toRadians(angDif) );
        }

        System.out.println("");

//        carsMomentum[0][0] =  carsMomentum[0][0] * Math.cos( Math.toRadians(carsMomentum[0][1] - car1Angle) );
//        System.out.println("");
//        System.out.println("CAR 1 ANGLE: " + (carsMomentum[0][1] - car1Angle));
//
//        carsMomentum[1][0] =  carsMomentum[1][0] * Math.cos( Math.toRadians(carsMomentum[1][1] - car2Angle) );
//        System.out.println("CAR 2 ANGLE: " + (carsMomentum[1][1] - car2Angle));
//        System.out.println("");



        return carsMomentum;
    }


    private void forceProportion(int player, double angle){

    }

    private double getPostAngle(int carNum){
        if (carNum == 1){
            //car 1 angle
            if (this.carsPostVels[1] == 0){
                return(car1Angle);
            }
            return angleCorrection(Math.toDegrees(Math.atan(this.postVel[0]/this.postVel[1] ))); // Car 1 net angle
        }
        else{
            if (this.carsPostVels[3] == 0){
                return(car2Angle);
            }
//            System.out.println("Car2 b4: " + car2Force);
//            System.out.println("VERT: " + this.postVel[2]);
//            System.out.println("HORIZ: " + this.postVel[3]);
//            System.out.println("Corrected angle P2:" +angleCorrection(Math.toDegrees(Math.atan(this.postVel[2]/this.postVel[3] ))));
//            System.out.println("angle P2:" + (Math.toDegrees(Math.atan(this.postVel[2]/this.postVel[3] ))));
            return angleCorrection(Math.toDegrees(Math.atan(this.postVel[2]/this.postVel[3]))+90); // Car 2 net angle

        }
    }

    private double angleCorrection(double angle){
        double angleCorrected = angle;
        if(angle < 0){
            angleCorrected += 360;
        }
        return angleCorrected;
    }

    private double[] postMag = new double[2];
    private double[] postVel = new double[4];

    //returns magnitude of force after collision, enter 1 or 2 as parameter to get car 1 or 2 respectively
    private void setPostMag(){
///        Car 1 force magnitude
        this.postMag[0] = Math.sqrt(Math.pow(this.carsPostVels[0], 2) + Math.pow(this.carsPostVels[1], 2));
        this.postMag[1] =  Math.sqrt(Math.pow(this.carsPostVels[2], 2) + Math.pow(this.carsPostVels[3], 2));

    }

    private final double[] carsPostVels = new double[4];


    private void setPostVels(){
        //vertical first

        double car1Vel = this.getVertVel(car1Force, car1Angle);
        double car2Vel = this.getVertVel(car2Force, car2Angle);
        //                  ( (m1       -   m2)     /  (m1          +   m2) )   *  u1     + ( (2*   m2)   /   (m1          +  m2 )     ) * u2
        carsPostVels[0] = ((((car1Mass - car2Mass)/(car1Mass + car2Mass))*car1Vel) + (((2*car2Mass)/(car1Mass + car2Mass))*car2Vel)); //Car 1 vert

        //            ( (2*  m1)          /  (m1           +   m2)   ) * u1     + ((  m2      -  m1)          /  m1           +    m2   ) * u2
        carsPostVels[2] = ((((2*car1Mass)/(car1Mass + car2Mass))*car1Vel)+(((car2Mass - car1Mass)/car1Mass + car2Mass)*car2Vel)); // Car 2 Vert

        car1Vel = this.getHorizVel(car1Force, car1Angle);
        car2Vel = this.getHorizVel(car2Force, car2Angle);
//        System.out.println("CAR 1 FORCE: " + car1Force);
//        System.out.println("CAR 1 ANGLE: " + car1Angle);
//        System.out.println("CAR 1 H VEL: " + car1Vel);

        carsPostVels[1] = ((((car1Mass - car2Mass)/(car1Mass + car2Mass))*car1Vel) + (((2*car2Mass)/(car1Mass + car2Mass))*car2Vel)); //Car 1 Horiz
        carsPostVels[3] = ((((2*car1Mass)/(car1Mass + car2Mass))*car1Vel)+(((car2Mass - car1Mass)/(car1Mass + car2Mass))*car2Vel)); // Car 2 Horiz

//        System.out.println("CAR 1 Vert Force: " + this.carsPostVels[0]);
//        System.out.println("CAR 1 Horiz Force: " + this.carsPostVels[1]);
//        System.out.println("CAR 2 Vert Force: " + this.carsPostVels[2]);
//        System.out.println("CAR 2 Horiz Force: " + this.carsPostVels[3]);

    }


    private double getVertVel(double speed, double angle){
        double angleCorrected = angleCorrection(angle);
        return(Math.sin( Math.toRadians( angleCorrected ) )*speed);
    }

    private double getHorizVel(double speed, double angle){
        double angleCorrected = angleCorrection(angle);
        //to make positive to the right and negative to the left
        return(Math.cos( Math.toRadians( angleCorrected +180 ) )*speed);
    }

    /*
    1. forward acceleration
    2. deceleration
    2.5 backwards
    3. turning speed
    4. change the center of the car png
     */


}
