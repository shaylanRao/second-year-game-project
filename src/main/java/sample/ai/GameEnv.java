package sample.ai;

import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import sample.ai.imported.ActionSpace;
import sample.ai.imported.LruReplayBuffer;
import sample.ai.imported.agent.RlAgent;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import sample.ai.imported.RlEnv;
import sample.models.Game;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import static sample.ai.TrainCar.OBSERVE;

/**{@inheritDoc}
 * this class represents the racing game RL "environment"
 */
public class GameEnv implements RlEnv {
    private final LruReplayBuffer replayBuffer;
    private final NDManager manager;
    private final ActionSpace actionSpace;
    //TODO should these be kept public?
    public static int trainStep = 0;
    public static int gameStep = 0;
    private String trainState = "observe";
    private NDList currentObservation;
    public static int gameState;
    public static final int GAME_START = 1;
    public static final int GAME_OVER = 2;

    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;

    private static boolean currentTerminal = false;

    public static void setCurrentTerminal(boolean currentTerminal) {
        GameEnv.currentTerminal = currentTerminal;
    }

    private static float currentReward = 0.2f;
    
    private static final int[] DO_NOTHING = {1, 0, 0, 0, 0};
    private static final int[] MOVE_LEFT = {0, 1, 0, 0, 0};
    private static final int[] MOVE_RIGHT = {0, 0, 1, 0, 0};
    private static final int[] MOVE_UP = {0, 0, 0, 1, 0};
    private static final int[] MOVE_DOWN = {0, 0, 0, 0, 1};
    
    public GameEnv(NDManager manager, int batchSize, int replayBufferSize, Game game) {
        this.manager = manager;
        this.replayBuffer = new LruReplayBuffer(batchSize, replayBufferSize);
        this.game = game;
        //initialise action space
        actionSpace = new ActionSpace();
        actionSpace.add(new NDList(manager.create(DO_NOTHING)));
        actionSpace.add(new NDList(manager.create(MOVE_UP)));
        actionSpace.add(new NDList(manager.create(MOVE_DOWN)));
        actionSpace.add(new NDList(manager.create(MOVE_LEFT)));
        actionSpace.add(new NDList(manager.create(MOVE_RIGHT)));

        currentObservation = createObservation();
    }

    private final Queue<NDArray> obvQueue = new ArrayDeque<>(4);
    
    private NDList createObservation() {
        NDArray observation = manager.create(new Shape(8), DataType.FLOAT32);
        float[] floatDistances = new float[8];
        for (int i=0; i<floatDistances.length; i++) {
            floatDistances[i] = (float) game.getDistances()[i];
        }
        System.out.println(Arrays.toString(floatDistances));
        observation.set(floatDistances);
        return new NDList(observation);
        /*
        float[] floatDistances = new float[8];
        for (int i=0; i<floatDistances.length; i++) {
            floatDistances[i] = (float) game.getDistances()[i];
        }
        observation.set(floatDistances);
        if (obvQueue.isEmpty()) {
            for (int i=0; i<4; i++) {
                obvQueue.offer(observation);
            }
            return new NDList(NDArrays.stack(new NDList(observation, observation, observation, observation), 1));
        } else {
            obvQueue.remove();
            obvQueue.offer(observation);
            NDArray[] buf = new NDArray[4];
            int i = 0;
            for (NDArray nd : obvQueue) {
                buf[i++] = nd;
            }
            return new NDList(NDArrays.stack(new NDList(buf[0], buf[1], buf[2], buf[3]), 1));
        }
         */
    }

    @Override
    public void reset() {
        currentReward = 0.2f;
        currentTerminal = false;
    }

    @Override
    public NDList getObservation() {
        return currentObservation;
    }

    @Override
    public ActionSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public void step(NDList action, boolean training) {
        //TODO logic here to control the car based on the action (chosen by the agent)
        Number[] actionNumArr = action.singletonOrThrow().toArray();
        //converting Number[] to int[]
        int[] actionArr = new int[actionNumArr.length];
        for (int i=0; i<actionArr.length; i++) {
            actionArr[i] = (int) actionNumArr[i];
        }
        if (Arrays.equals(actionArr, DO_NOTHING)) {
            //do nothing
            game.getAiCar().clearMovement();
            //System.out.println("do nothing");
        } else if (Arrays.equals(actionArr, MOVE_UP)) {
            //move up
            game.getAiCar().clearMovement();
            game.getAiCar().setGoingForward(true);
            game.getAiCar().setAccelerate(true);
           // System.out.println("move up");
        } else if (Arrays.equals(actionArr, MOVE_DOWN)) {
            //move down
            game.getAiCar().clearMovement();
            game.getAiCar().setGoingBackward(true);
            //System.out.println("move down");
        } else if (Arrays.equals(actionArr, MOVE_LEFT)) {
            //move left
            game.getAiCar().clearMovement();
            game.getAiCar().setTurnLeft(true);
            //System.out.println("move left");
        } else if (Arrays.equals(actionArr, MOVE_RIGHT)) {
            //move right
            game.getAiCar().clearMovement();
            game.getAiCar().setTurnRight(true);
            //System.out.println("move right");
        }
        //run main game loop once
            game.gameLoopAI();


        NDList preObservation = currentObservation;
        currentObservation = createObservation();
        GameStep step = new GameStep(manager.newSubManager(), preObservation, currentObservation, action, currentReward, currentTerminal);
        if (training) {
            replayBuffer.addStep(step);
        }
//        System.out.println("GAME_STEP " + gameStep +
//                " / " + "TRAIN_STEP " + trainStep +
//                " / " + getTrainState() +
//                " / " + "ACTION " + (Arrays.toString(action.singletonOrThrow().toArray())) +
//                " / " + "REWARD " + step.getReward().getFloat());
        //TODO, set this in game when car crashesd
        if (gameState == GAME_OVER) {
            restartGame();
        }
        //System.out.println(game.getAiCar().getPos().toString());;
    }

    private void restartGame() {
        //TODO add code to start game
        game.resetAICar();
        gameState = GAME_START;
    }

    @Override
    public Step[] runEnvironment(RlAgent agent, boolean training) {
        Step[] batchSteps = new Step[0];
        reset();
        //NDList action = agent.chooseAction(this, training);
        NDList action = this.actionSpace.get(1);
        step(action, training);
        if (training) {
            batchSteps = this.getBatch();
        }
        if (gameStep % 5000 == 0) {
            this.closeStep();
        }
        if (gameStep <= OBSERVE) {
            trainState = "observe";
        } else {
            trainState = "explore";
        }
        gameStep++;
        return batchSteps;
    }

    @Override
    public Step[] getBatch() {
        return (Step[]) replayBuffer.getBatch();
    }

    @Override
    public void close() {
        manager.close();
    }

    private void closeStep() {
        replayBuffer.closeStep();
    }

    public String getTrainState() {
        return this.trainState;
    }

    //TODO set the reward based off of the lap time & distance
    public static void setCurrentReward(float currentReward) {
        GameEnv.currentReward = currentReward;
    }

    static final class GameStep implements RlEnv.Step {
        private final NDManager manager;
        private final NDList preObservation;
        private final NDList postObservation;
        private final NDList action;
        private final float reward;
        private final boolean terminal;
        private String trainState;

        private GameStep(NDManager manager, NDList preObservation, NDList postObservation, NDList action, float reward, boolean terminal) {
            this.manager = manager;
            this.preObservation = preObservation;
            this.postObservation = postObservation;
            this.action = action;
            this.reward = reward;
            this.terminal = terminal;
        }

        @Override
        public NDList getPreObservation(NDManager manager) {
            preObservation.attach(manager); // assign manager to each NDArray in the NDList
            return preObservation;
        }

        @Override
        public NDList getPreObservation() {
            return preObservation;
        }

        @Override
        public NDList getAction() {
            return action;
        }

        @Override
        public NDList getPostObservation(NDManager manager) {
            postObservation.attach(manager);
            return  postObservation; // assign manager to each NDArray in the NDList
        }

        @Override
        public NDList getPostObservation() {
            return postObservation;
        }

        @Override
        public NDManager getManager() {
            return this.manager;
        }

        @Override
        public NDArray getReward() {
            return manager.create(reward); //return reward as an NDArray
        }

        @Override
        public boolean isTerminal() {
            return terminal;
        }


        @Override
        public void close() {
            this.manager.close();
        }


    }}
