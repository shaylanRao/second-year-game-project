package sample.ai;

import ai.djl.modality.rl.ActionSpace;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDArrays;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;

import java.util.ArrayDeque;
import java.util.Queue;

public class CarAgent {
    private ActionSpace actionSpace;
    private final Queue<NDArray> observeQueue = new ArrayDeque<>(4);
    private static final int[] DO_NOTHING = {1, 0, 0, 0, 0};
    private static final int[] MOVE_LEFT = {0, 1, 0, 0, 0};
    private static final int[] MOVE_RIGHT = {0, 0, 1, 0, 0};
    private static final int[] MOVE_UP = {0, 0, 0, 1, 0};
    private static final int[] MOVE_DOWN = {0, 0, 0, 0, 1};

    public CarAgent() {
        try (NDManager manager = NDManager.newBaseManager()) {
            //initialise the action space
            actionSpace = new ActionSpace();
            actionSpace.add(new NDList(manager.create(DO_NOTHING)));
            actionSpace.add(new NDList(manager.create(MOVE_UP)));
            actionSpace.add(new NDList(manager.create(MOVE_DOWN)));
            actionSpace.add(new NDList(manager.create(MOVE_LEFT)));
            actionSpace.add(new NDList(manager.create(MOVE_RIGHT)));
        }
        //currentObservation = createObservation();
    }

    public NDList createObservation(double[] distances) {
        try (NDManager manager = NDManager.newBaseManager()) {
            NDArray observation = manager.create(distances); //use raycast distances as observation for 1 time step
            if (observeQueue.isEmpty()) {
                //if the queue is empty then fill it with 4x the current observation
                for (int i=0; i<4; i++) {
                    observeQueue.offer(observation);
                }
                return new NDList(NDArrays.stack(new NDList(observation, observation, observation, observation)));
            } else {
                //else pop from queue and add most recent observation
                observeQueue.remove();
                observeQueue.offer(observation);
                NDArray[] buf = new NDArray[4];
                int i = 0;
                for (NDArray nd: observeQueue) {
                    buf[i++] = nd;
                }
                return new NDList(NDArrays.stack(new NDList(buf[0], buf[1], buf[2], buf[3]), 1));
            }
        }
    }
}
