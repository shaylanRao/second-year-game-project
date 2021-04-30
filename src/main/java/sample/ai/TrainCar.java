package sample.ai;

import ai.djl.Model;
import ai.djl.nn.Block;
import sample.ai.imported.agent.EpsilonGreedy;
import sample.ai.imported.agent.QAgent;
import sample.ai.imported.agent.RlAgent;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Activation;
import ai.djl.nn.Blocks;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.convolutional.Conv2d;
import ai.djl.nn.core.Linear;
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

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import ai.djl.basicmodelzoo.basic.Mlp;

public class TrainCar {
    public static final int OBSERVE = 1000;
    private static final float REWARD_DISCOUNT = 0.9f;
    public static final float INITIAL_EPSILON = 0.01f;
    public static final float FINAL_EPSILON = 0.0001f;
    public static final int EXPLORE = 3000000;
    public static final int SAVE_EVERY_STEPS = 100000;
    private static final long INPUT_SIZE = 8;
    private static final long OUTPUT_SIZE = 5;
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
            input layer: 8*4 = 32 neurons
            1 hidden layer of 24 neurons (relu activation function)
            output layer: 5 neurons
        */
    }

    public static void main (String[] args) {
        //TODO find right batch size
        train(500);
    }

    public static void train(int batchSize) {
        GameEnv gameEnv = new GameEnv(NDManager.newBaseManager(), batchSize, REPLAY_BUFFER_SIZE, game);
        Model model = createOrLoadModel();
        boolean training = true;
        //TODO create and instance of the game here??

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

            int numOfThreads = 2;
            List<Callable<Object>> callables = new ArrayList<>(numOfThreads);
            callables.add(new GeneratorCallable(gameEnv, agent, training));
            if (training) {
                callables.add(new TrainerCallable(model, agent));
            }
            ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
            try {
                try {
                    List<Future<Object>> futures = new ArrayList<>();
                    for (Callable<Object> callable : callables) {
                        futures.add(executorService.submit(callable));
                    }
                    for (Future<Object> future : futures) {
                        future.get();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } finally {
                executorService.shutdown();
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

    private static class GeneratorCallable implements Callable<Object> {
        private final GameEnv gameEnv;
        private final RlAgent agent;
        private final boolean training;
        public GeneratorCallable(GameEnv gameEnv, RlAgent agent, boolean training) {
            this.gameEnv = gameEnv;
            this.agent = agent;
            this.training = training;
        }

        @Override
        public Object call() {
            while (gameEnv.trainStep < EXPLORE) {
                batchSteps = gameEnv.runEnvironment(agent, training);
            }
            return null;
        }
    }

    private static class TrainerCallable implements Callable<Object> {
        private final Model model;
        private final RlAgent agent;

        public TrainerCallable(Model model, RlAgent agent) {
            this.model = model;
            this.agent = agent;
        }

        @Override
        public Object call() throws Exception {
            while (gameEnv.trainStep < EXPLORE) {
                //do nothing if below exploration threshold i.e., just observe
                Thread.sleep(0);
                if (gameEnv.gameStep > OBSERVE) {
                    //TODO check this, may well be wrong
                    this.agent.trainBatch(batchSteps);
                    //TODO maybe change trainStep to static
                    gameEnv.trainStep++;
                    if (gameEnv.trainStep > 0 && gameEnv.trainStep % SAVE_EVERY_STEPS == 0) {
                        model.save(Paths.get(MODEL_PATH), "dqn-" + gameEnv.trainStep);
                    }
                }
            }
            return null;
        }
    }
}
