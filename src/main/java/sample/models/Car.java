package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.ai.GameEnv;
import sample.models.audio.SoundManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.LinkedList;

public class Car extends Sprite {

    private boolean goingForward, goingBackward, turnRight, turnLeft, accelerate;
    private double accelerationModerator;
    private double forceSpeed;
    private double speedConverter;
    private double speed, maximumSpeed, minimumSpeed;
    public int playerNumber;
    protected boolean speedBoostOn = false;
    protected boolean carSpinOn = false;
    protected boolean carSlideOn = false;
    private final double carHeight = getCarHeightWidth()[0];
    private final double carWidth = getCarHeightWidth()[1];

    public float getDistanceTravelled() {
        return distanceTravelled;
    }

    private float distanceTravelled = 0;

    public Raycaster getRaycaster() {
        return raycaster;
    }

    public void setRaycaster(Raycaster raycaster) {
        this.raycaster = raycaster;
        this.raycaster.setPos(new Point(this.getImageView().getLayoutX(), this.getImageView().getLayoutY()));
        this.raycaster.setRot(this.getImageView().getRotate());
    }

    private Raycaster raycaster;



    public Car(Pane gameBackground, ImageView image, Settings.VehicleType vehicleType, Point pos) {
        super(gameBackground, image, 0.8);
        //reverse speed (HARD-CODED)
        this.setMinimumSpeed(-1.5);
        this.setSpeedConverter(0.09);
        this.assignAttributes(vehicleType);
        this.raycaster = new Raycaster(gameBackground, this);
        this.raycaster.setPos(pos);
        this.raycaster.setRot(90);
        this.getImageView().setRotate(90);
    }

    public Car(Pane gameBackground, Settings.VehicleType vehicleType) {
        this(gameBackground, generateCarImageView(vehicleType), vehicleType, null);
    }

    public Car(Pane gameBackground, Settings.VehicleType vehicleType, Point pos) {
        this(gameBackground, generateCarImageView(vehicleType), vehicleType, pos);
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
            //FileInputStream carImageFile = new FileInputStream(imageName);
            InputStream carImageFile = Car.class.getResourceAsStream(imageName);
            if (carImageFile==null) {
                System.out.println("car image file was null");
            }
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
            ex.printStackTrace();
        }
        return null;
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

//    private double getCX() {
//        return this.getImageView().getBoundsInLocal().getWidth() / 2;
//    }
//
//    private double getCY() {
//        return this.getImageView().getBoundsInLocal().getHeight() / 2;
//    }


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


    //todo momentum
    //inheritance for other types of vehicles


    /**
     * Gets the coordinates and angle of the car
     * Calculates the change in coordinates of where the car moves and the angle of rotation
     */
    public void moveCarBy(double dy) {
        distanceTravelled += Math.abs(dy);
        //System.out.println("inside moveCarBy");
        if (dy == 0) {
            return;
        }

        final double cx = getCarHeightWidth()[1];
        final double cy = getCarHeightWidth()[0];

        double angleMoveX = Math.cos(Math.toRadians(this.getImageView().getRotate()));
        double angleMoveY = Math.sin(Math.toRadians(this.getImageView().getRotate()));

        double x = (cx + this.getImageView().getLayoutX() + (dy * angleMoveX));
        double y = (cy + this.getImageView().getLayoutY() + (dy * angleMoveY));

        this.move(x, y);
    }

    /**
     * Moves the car by calculates amount from 'moveCarBy'
     * */
    private void move(double x, double y) {
        final double cx = getCarHeightWidth()[1];
        final double cy = getCarHeightWidth()[0];
        //System.out.println("inside move()");
        if (x - cx >= 0 && x + cx <= Main.maxWidth && y - cy >= 0 && y + cy <= Main.maxHeight) {

            this.getImageView().relocate(x - cx, y - cy);
            //System.out.println("x: " + this.getImageView().getLayoutX() + ", y: " + this.getImageView().getLayoutY());
            //set raycaster position and rotation = the car's position and rotation
            raycaster.setPos(new Point(Point.unconvertX(this.getImageView().getLayoutX() + (carWidth/2)), Point.unconvertY(this.getImageView().getLayoutY() + (carHeight/2))));
            raycaster.setRot(this.getImageView().getRotate());
        }
    }

    /**
     * Rotates the image of the car
     * */

    public void turn(double angle) {
        //double cAngle = this.getImageView().getRotate();
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
        //System.out.println(speed);
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
        double diagLen = Math.sqrt(Math.pow(this.carHeight, 2) + Math.pow(this.carWidth, 2)) / 2;

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


        return carsMomentum;
    }


    private double getPostAngle(int carNum){
        if (carNum == 1){
            if (this.carsPostVels[1] == 0){
                System.out.println("CAR 1 ZERO");
                System.out.println("CAR 1 MAG: " + this.postMag[0]);
                return(car1Angle);
            }
            //todo Below comment is probs wrong (only works for 135 degree ish angle)
            //need to +90 as 0 is taken from west but angles calculated from north
            return angleCorrection(Math.toDegrees(Math.atan(this.postVel[0]/this.postVel[0] ))+90); // Car 1 net angle
        }
        else{
            if (this.carsPostVels[3] == 0){
                System.out.println("CAR 2 ZERO");
                return(car2Angle);
            }
//            System.out.println("Car2 b4: " + car2Force);
//            System.out.println("VERT: " + this.postVel[2]);
//            System.out.println("HORIZ: " + this.postVel[3]);
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

    public void clearMovement() {
        this.setGoingBackward(false);
        //this.setAccelerate(false);
        this.setTurnLeft(false);
        this.setTurnRight(false);
        this.setGoingBackward(false);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    private boolean dead = false;


    public void die() {
        dead = true;
        GameEnv.setCurrentReward(-1f);
        //System.out.println("car died");
        GameEnv.setCurrentTerminal(true);
        GameEnv.gameState = GameEnv.GAME_OVER;
    }
}
