package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.ai.AICar;
import sample.models.audio.SoundManager;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.LinkedList;

public class Car extends Sprite {

    private boolean goingForward, goingBackward, turnRight, turnLeft, isAccelerate;
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
    protected final double carHeight = getCarHeightWidth()[0];
    protected final double carWidth = getCarHeightWidth()[1];
    private double mass;
    private double turningSpeedModerator;

    public Raycaster getRaycaster() {
        return raycaster;
    }

    protected Raycaster raycaster;



    /**
     * This is the constructor for the car class, it sets the image size of the car, the minimum speeds,
     * the value needed to convert the speed to a value usable in the game render. It also initialises the powerup features
     * @param gameBackground the pane of the gameplay screen
     * @param image the image used so that the car can be rendered, manipulated and features of the image accessed
     * @param vehicleType this dictates how the attributes of the vehicle differ between each vehicle available
     */
    public Car(Pane gameBackground, ImageView image, Settings.VehicleType vehicleType) {
        super(gameBackground, image, 0.8);
        //reverse speed (HARD-CODED)
        this.setMinimumSpeed(-1.5);
        this.setSpeedConverter(0.09);
        this.assignAttributes(vehicleType);
        this.raycaster = new Raycaster(gameBackground, this);
        //this.raycaster.setPos(pos);
        this.raycaster.setRot(90);
        this.getImageView().setRotate(90);
    }

    public Car(Pane gameBackground, Settings.VehicleType vehicleType, Point pos) {
        this(gameBackground, generateCarImageView(vehicleType), vehicleType);
    }

    protected static ImageView generateCarImageView(Settings.VehicleType vehicleType) {
        String imageName;
        try {
            if(vehicleType.equals(Settings.VehicleType.VEHICLE1)){
                imageName = "/images/original_car.png";
            }
            else if (vehicleType.equals(Settings.VehicleType.VEHICLE2)){
                imageName = "/images/vehicleTwo.png";
            }
            else{
                imageName = "/images/vehicleThree.png";
            }
            InputStream carImageFile = Main.class.getResourceAsStream(imageName);
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }

    /**
     * This is a function that checks which car was selected by the user and adjusts values that change how to car moves,
     * the maximum speed of each car, the mass and the rate of acceleration
     * @param vehicleType this is which car was selected by the player
     */
    private void assignAttributes(Settings.VehicleType vehicleType){
        //standard car
        if (vehicleType == Settings.VehicleType.VEHICLE1){
            this.setMaximumSpeed(3);
//            this.setAccelerationModerator(0.005);
            this.mass = 1;
            this.setTurningSpeedModerator(1);
        }
        //sports car
        else if (vehicleType == Settings.VehicleType.VEHICLE2){
            this.setMaximumSpeed(3.5);
            this.setAccelerationModerator(0.003);
            this.mass = 0.833;
            this.setTurningSpeedModerator(0.93);
        }
        //muscle car
        else{
            this.setMaximumSpeed(2.8);
            this.setAccelerationModerator(0.006);
            this.mass = 1.66;
            this.setTurningSpeedModerator(1.2);
        }
    }

    /**
     * This is used to calculated the momentum of the car
     * @return mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * This is a setter for the force acting on the car
     * @param forceSpeed the value of force wanted to be applied on the car
     */
    public void setForceSpeed(double forceSpeed) {
        this.forceSpeed = forceSpeed;
    }

    /**
     * This is a getter for the force speed
     * @return forceSpeed
     */
    public double getForceSpeed() {
        return forceSpeed;
    }

    /**
     * This is a getter for the image view of the car, where image attributes can be accessed
     * @return ImageView
     */
    public ImageView getImageView() {
        return super.getImage();
    }

    /**
     * This is a boolean value which states whether the player is accelerating the car
     * @return accelerate
     */
    public boolean isAccelerate() {
        return isAccelerate;
    }

    /**
     * This is a getter for the maximum speed the car can travel forwards at
     * @return maximumSpeed
     */
    public double getMaxSpeed() {
        return maximumSpeed;
    }

    /**
     * This is a setter used in the constructor to set the maximum speed to car can travel forwards at
     * @param maximumSpeed the maximum speed the car can move forwards at
     */
    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    /**
     * This is a setter used in the constructor to set the minimum speed to car can travel forwards at
     * @param maximumSpeed the minimum speed the car can move forwards at
     */
    public void setMinimumSpeed(double maximumSpeed) {
        this.minimumSpeed = maximumSpeed;
    }

    /**
     * This is a setter for the acceleration moderator
     * @param accelerationModerator a value  used to adjust the calculated physics value to a usable game value
     */
    public void setAccelerationModerator(double accelerationModerator){ this.accelerationModerator = accelerationModerator; }

    /**
     * This is a setter for acceleration
     * @param accelerate the change in velocity (acceleration)
     */
    public void setAccelerate(boolean accelerate) {
        this.isAccelerate = accelerate;
    }

    /**
     * This is a setter for storing whether the car is moving forward or not
     * @param goingForward a boolean value
     */
    public void setGoingForward(boolean goingForward) {
        this.goingForward = goingForward;
    }

    /**
     * This is a setter for the speed converter, used in adjusting the position of the car
     * @param speedConverter a value  used to adjust the calculated physics value to a usable game value
     */
    public void setSpeedConverter(double speedConverter) {
        this.speedConverter = speedConverter;
    }

    /**
     * This is mimics a getter for checking if the car is moving forwards
     * @return goingForward
     */
    public boolean isGoingForward() {
        return goingForward;
    }

    /**
     * This is mimics a getter for checking if the car is moving backwards
     * @return goingBackward
     */
    public boolean isGoingBackward() {
        return goingBackward;
    }

    /**
     * This is a setter for storing is the car is going backwards
     * @param goingBackward a value used to check the movement of the car
     */
    public void setGoingBackward(boolean goingBackward) {
        this.goingBackward = goingBackward;
    }

    /**
     * This is mimics a getter for checking if the car is turning right
     * @return turnRight
     */
    public boolean isTurnRight() {
        return turnRight;
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    /**
     * This is mimics a getter for checking if the car is turning left
     * @return turnLeft
     */
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
        //adjusted per vehicle to change acceleration, kept at one for consistency
        return (this.longForce()/1);
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
     * Returns a constant force for reversing the car
     * @return const
     */
    private double fReverse(){
        //fixed gear speed
        return(-15);
    }


    /**
     * This is the tractive force delivered from the cars engine and is only activate if the player is accelerating
     * (foot on the pedal to move forwards)
     * @return tractiveEngineForce
     */
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
     * Drag force (air resistance), a resistive force
     * Follows and exponential line, starting at 0 and finishing increasingly high
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
     */
    public void setPickedUpPwrtime(long pickedUpPwrtime)
    {
        this.pickedUpPwrtime = pickedUpPwrtime;
    }

    /**
     * Gets the coordinates and angle of the car
     * Calculates the change in coordinates of where the car moves and the angle of rotation
     * @param dy
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
     * @param x the change in the x-axis
     * @param y the change in the y-axis
     * */
    private void move(double x, double y) {
        final double cx = this.getCX();
        final double cy = this.getCY();

        if (x - cx >= 0 && x + cx <= Main.maxWidth && y - cy >= 0 && y + cy <= Main.maxHeight) {
            this.getImageView().relocate(x - cx, y - cy);

            raycaster.setPos(new Point(Point.unconvertX(this.getImageView().getLayoutX() + (carWidth/2)), Point.unconvertY(this.getImageView().getLayoutY() + (carHeight/2))));
            raycaster.setRot(this.getImageView().getRotate());
        }
    }


    /**
     * Rotates the image of the car, unless a powerup is affecting its turning
     * @param angle change in angle
     */
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

    /**
     * This is used to check whether the two cars have collided
     * @param A the boundary around one car
     * @param B the boundary around another car
     * @return boolean
     */
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

    //todo
    /**
     *
     * @param a
     * @param axis
     * @return
     */
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

    //todo
    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static CollisionNode[] concatenate (CollisionNode[] a, CollisionNode[] b) {
        // Concatenate the two arrays of nodes
        int aLen = a.length;
        int bLen = b.length;

        CollisionNode[] c = (CollisionNode[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }


    /**
     * This is used to check if the car has collided into the wall
     * The sped of the car is adjusted accordingly
     * @param gateDistances the distances from the car to the edges of the wall
     * @return boolean
     */
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

    /**
     * This is a getter for the car height and width
     * @return heightWidth
     */
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
    protected double car1Angle;

    /**
     * This calculates the momentum of a car on car collision
     * @param car1 one of the cars
     * @param car2 the other car
     * @return carsMomentum
     */
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

        System.out.println();


        return carsMomentum;
    }


    /**
     * This gets the angles of both cars after the collision
     * @param carNum the car playerNumber
     * @return
     */
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
            return angleCorrection(Math.toDegrees(Math.atan(this.postVel[2]/this.postVel[3]))+90); // Car 2 net angle

        }
    }

    /**
     * This corrects the angle to be within a constant and limmited range
     * @param angle the calculated angle
     * @return angleCorrected
     */
    private double angleCorrection(double angle){
        double angleCorrected = angle;
        if(angle < 0){
            angleCorrected += 360;
        }
        return angleCorrected;
    }

    private final double[] postMag = new double[2];
    private final double[] postVel = new double[4];

    //returns magnitude of force after collision, enter 1 or 2 as parameter to get car 1 or 2 respectively

    /**
     * This sets the magnitude of the force on both ater ther collision
     */
    private void setPostMag(){
        this.postMag[0] = Math.sqrt(Math.pow(this.carsPostVels[0], 2) + Math.pow(this.carsPostVels[1], 2));
        this.postMag[1] =  Math.sqrt(Math.pow(this.carsPostVels[2], 2) + Math.pow(this.carsPostVels[3], 2));
    }

    private final double[] carsPostVels = new double[4];


    /**
     * This sets the velocities in the vertical and horizontal direction for both cars
     */
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

        carsPostVels[1] = ((((car1Mass - car2Mass)/(car1Mass + car2Mass))*car1Vel) + (((2*car2Mass)/(car1Mass + car2Mass))*car2Vel)); //Car 1 Horiz
        carsPostVels[3] = ((((2*car1Mass)/(car1Mass + car2Mass))*car1Vel)+(((car2Mass - car1Mass)/(car1Mass + car2Mass))*car2Vel)); // Car 2 Horiz

    }


    /**
     * This is a getter for the vertical velocity of a car
     * @param speed speed at crash
     * @param angle angle at crash
     * @return verticalSpeed
     */
    private double getVertVel(double speed, double angle){
        double angleCorrected = angleCorrection(angle);
        return(Math.sin( Math.toRadians( angleCorrected ) )*speed);
    }

    /**
     * This is a getter for the horizontal velocity of a car
     * @param speed speed at crash
     * @param angle angle at crash
     * @return verticalSpeed
     */
    private double getHorizVel(double speed, double angle){
        double angleCorrected = angleCorrection(angle);
        //to make positive to the right and negative to the left
        return(Math.cos( Math.toRadians( angleCorrected +180 ) )*speed);
    }

}
