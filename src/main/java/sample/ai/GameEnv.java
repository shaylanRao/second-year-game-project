package sample.ai;

import ai.djl.modality.rl.ActionSpace;
import ai.djl.modality.rl.LruReplayBuffer;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;

import java.util.Arrays;

import static sample.ai.TrainCar.OBSERVE;

/**{@inheritDoc}
 * this class represents the racing game RL "environment"
 */
public class GameEnv implements RlEnv {
    private final LruReplayBuffer replayBuffer;
    private final NDManager manager;
    private final ActionSpace actionSpace;
    //TODO should these be kept public?
    public int trainStep = 0;
    public int gameStep = 0;
    private String trainState = "observe";
    private NDList currentObservation;
    
    private static final int[] DO_NOTHING = {1, 0, 0, 0, 0};
    private static final int[] MOVE_LEFT = {0, 1, 0, 0, 0};
    private static final int[] MOVE_RIGHT = {0, 0, 1, 0, 0};
    private static final int[] MOVE_UP = {0, 0, 0, 1, 0};
    private static final int[] MOVE_DOWN = {0, 0, 0, 0, 1};
    
    public GameEnv(NDManager manager, int batchSize, int replayBufferSize) {
        this.manager = manager;
        this.replayBuffer = new LruReplayBuffer(batchSize, replayBufferSize);

        //initialise action space
        actionSpace = new ActionSpace();
        actionSpace.add(new NDList(manager.create(DO_NOTHING)));
        actionSpace.add(new NDList(manager.create(MOVE_UP)));
        actionSpace.add(new NDList(manager.create(MOVE_DOWN)));
        actionSpace.add(new NDList(manager.create(MOVE_LEFT)));
        actionSpace.add(new NDList(manager.create(MOVE_RIGHT)));

        //TODO add code here to intitial observation and to start the game??
        //ie.e, call main game loop here to start it off?
    }

    @Override
    public void reset() {

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
        int[] actionArr = new int[actionNumArr.length];
        for (int i=0; i<actionArr.length; i++) {
            actionArr[i] = (int) actionNumArr[i];
        }
        if (Arrays.equals(actionArr, DO_NOTHING)) {
            //do nothing
        } else if (Arrays.equals(actionArr, MOVE_UP)) {
            //move up
        } else if (Arrays.equals(actionArr, MOVE_DOWN)) {
            //move down
        } else if (Arrays.equals(actionArr, MOVE_LEFT)) {
            //move left
        } else if (Arrays.equals(actionArr, MOVE_RIGHT)) {
            //move right
        }
        stepFrame();
    }

    private void stepFrame() {
    }

    @Override
    public Step[] getBatch() {
        return new Step[0];
    }

    @Override
    public void close() {

    }


    @Override
    public Step[] runEnvironment(RlAgent agent, boolean training) {
        Step[] batchSteps = new Step[0];
        //TODO add logic to run the game
        NDList action = agent.chooseAction((ai.djl.modality.rl.env.RlEnv) this, training);
        step(action, training);
        if (training) {
            batchSteps = this.getBatch();
        }
        if (gameStep <= OBSERVE) {
            trainState = "observe";
        } else {
            trainState = "explore";
        }
        gameStep++;
        return batchSteps;
    }
}
