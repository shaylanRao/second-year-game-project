package sample.ai;

import ai.djl.Model;
import javafx.animation.AnimationTimer;
import sample.ai.imported.agent.EpsilonGreedy;
import sample.ai.imported.agent.QAgent;
import sample.ai.imported.agent.RlAgent;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.SequentialBlock;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.initializer.NormalInitializer;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Adam;
import ai.djl.training.tracker.LinearTracker;
import ai.djl.training.tracker.Tracker;
import sample.ai.imported.RlEnv;
import sample.models.Game;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import ai.djl.basicmodelzoo.basic.Mlp;

public class TrainCar {

    // not going to render while training
        //replace image coords with a double or something
    //reward function
        //inverse of distance to next gate minus time taken (along those lines)



    public static final int OBSERVE = 1000;
    private static final float REWARD_DISCOUNT = 0.9f;
    public static final float INITIAL_EPSILON = 0.05f;
    public static final float FINAL_EPSILON = 0.0001f;
    public static final int EXPLORE = 3000000;
    public static final int SAVE_EVERY_STEPS = 100000;
    private static final long INPUT_SIZE = 8;
    private static final long OUTPUT_SIZE = 4;
    private static final int REPLAY_BUFFER_SIZE = 50000;
    private static GameEnv gameEnv;

    public static void setGame(Game game) {
        TrainCar.game = game;
    }

    private static Game game;
    //TODO this might have to be changed to target/main/resources/model
    private static final String MODEL_PATH = "src/main/resources/model";

    static RlEnv.Step[] batchSteps;

    private static Model createOrLoadModel() {
        Model model = Model.newInstance("QNetwork");
        model.setBlock(getBlock());
        return model;
    }

    private static SequentialBlock getBlock() {
        //for now, using this architecture under the assumption that the input is 8x4 - the last 4 frames
        //it may be the case that we change the input size to be 8 in the future
        return new Mlp((int) INPUT_SIZE, (int) OUTPUT_SIZE, new int[]{7});
        /*
        this creates a network with the following structure:
            input layer: 8 neurons
            1 hidden layer of 7 neurons (relu activation function)
            output layer: 4 neurons
        */
    }

    public static void main (String[] args) {
        //TODO find right batch size
        train(100);
    }

    public static void train(int batchSize) {
        GameEnv gameEnv = new GameEnv(NDManager.newBaseManager(), batchSize, REPLAY_BUFFER_SIZE, game);
        Model model = createOrLoadModel();
        boolean training = true;
        DefaultTrainingConfig config = setupTrainingConfig();

        try (Trainer trainer = model.newTrainer(config)) {
            trainer.initialize(new Shape(batchSize, 8));
            trainer.notifyListeners(listener -> listener.onTrainingBegin(trainer));

            RlAgent agent = new QAgent(trainer, REWARD_DISCOUNT);
            Tracker exploreRate = new LinearTracker.Builder()
                    .setBaseValue(INITIAL_EPSILON)
                    .optSlope(-(INITIAL_EPSILON - FINAL_EPSILON) / EXPLORE)
                    .optMinValue(FINAL_EPSILON)
                    .build();
            agent = new EpsilonGreedy(agent, exploreRate);
            final RlAgent finalAgent = agent;
            AnimationTimer animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (GameEnv.trainStep < EXPLORE) {
                        batchSteps = gameEnv.runEnvironment(finalAgent, training);
                    }
                }
            };
            animationTimer.start();
            if (training) {
                Thread thread = new Thread(new TrainerRunnable(model, agent));
                thread.start();
            }
        }
    }


    public static DefaultTrainingConfig setupTrainingConfig() {
        return new DefaultTrainingConfig(Loss.l2Loss())
                .optOptimizer(Adam.builder().optLearningRateTracker(Tracker.fixed(1e-6f)).build())
                .addEvaluator(new Accuracy())
                .optInitializer(new NormalInitializer())
                .addTrainingListeners(TrainingListener.Defaults.basic());
    }

    private static class TrainerRunnable implements Runnable {
        private final Model model;
        private final RlAgent agent;

        public TrainerRunnable(Model model, RlAgent agent) {
            this.model = model;
            this.agent = agent;
        }

        @Override
        public void run() {
            while (GameEnv.trainStep < EXPLORE) {
                //do nothing if below exploration threshold i.e., just observe
                Thread.yield();
                if (GameEnv.gameStep > OBSERVE) {
                    this.agent.trainBatch(batchSteps);
                    GameEnv.trainStep++;
                    if (GameEnv.trainStep > 0 && GameEnv.trainStep % SAVE_EVERY_STEPS == 0) {
                        try {
                            model.save(Paths.get(MODEL_PATH), "dqn-" + GameEnv.trainStep);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
