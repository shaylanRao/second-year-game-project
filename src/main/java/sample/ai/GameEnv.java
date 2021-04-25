package sample.ai;

import ai.djl.modality.rl.ActionSpace;
import sample.ai.imported.LruReplayBuffer;
import ai.djl.modality.rl.agent.RlAgent;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import sample.ai.imported.RlEnv;

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

    private static boolean currentTerminal = false;
    private static float currentReward = 0.2f;
    
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
        //TODO this function should progress the game to the next frame
        //maybe we can do something like call 1 iteration of the game loop??
    }

    @Override
    public Step[] getBatch() {
        return (Step[]) replayBuffer.getBatch();
    }

    @Override
    public void close() {
        manager.close();
    }


    @Override
    public Step[] runEnvironment(RlAgent agent, boolean training) {
        Step[] batchSteps = new Step[0];
        reset();
        NDList action = agent.chooseAction((ai.djl.modality.rl.env.RlEnv) this, training);
        step(action, training);
        if (training) {
            batchSteps = this.getBatch();
        }
        if (gameStep % 5000 == 0) {

        }        if (gameStep <= OBSERVE) {
            trainState = "observe";
        } else {
            trainState = "explore";
        }
        gameStep++;
        return batchSteps;
    }

    private void closeStep() {
        replayBuffer.closeStep();
    }

    static final class GameStep implements RlEnv.Step {
        private final NDManager manager;
        private final NDList preObservation;
        private final NDList postObservation;
        private final NDList action;
        private final float reward;
        private final boolean terminal;

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
