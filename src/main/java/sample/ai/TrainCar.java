package sample.ai;

import ai.djl.Model;
import ai.djl.modality.rl.agent.EpsilonGreedy;
import ai.djl.modality.rl.agent.QAgent;
import ai.djl.modality.rl.agent.RlAgent;
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
import sample.models.Game;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TrainCar {
    public static final int OBSERVE = 1000;
    private static final float REWARD_DISCOUNT = 0.9f;
    public static final float INITIAL_EPSILON = 0.01f;
    public static final float FINAL_EPSILON = 0.0001f;
    public static final int EXPLORE = 3000000;
    public static final int SAVE_EVERY_STEPS = 100000;
    private static GameEnv gameEnv;
    private static final String MODEL_PATH = "src/main/resources/model";

    static RlEnv.Step[] batchSteps;

    public void setGameEnv(GameEnv gameEnv) {
        this.gameEnv = gameEnv;
    }

    private static Model createOrLoadModel() {
        Model model = Model.newInstance("QNetwork");
        model.setBlock(getBlock());
        return model;
    }

    private static SequentialBlock getBlock() {
        //TODO will need to change this architecture for our purposes
        // conv -> conv -> conv -> fc -> fc
        return new SequentialBlock()
                .add(Conv2d.builder()
                        .setKernelShape(new Shape(8, 8))
                        .optStride(new Shape(4, 4))
                        .optPadding(new Shape(3, 3))
                        .setFilters(4).build())
                .add(Activation::relu)

                .add(Conv2d.builder()
                        .setKernelShape(new Shape(4, 4))
                        .optStride(new Shape(2, 2))
                        .setFilters(32).build())
                .add(Activation::relu)

                .add(Conv2d.builder()
                        .setKernelShape(new Shape(3, 3))
                        .optStride(new Shape(1, 1))
                        .setFilters(64).build())
                .add(Activation::relu)

                .add(Blocks.batchFlattenBlock())
                .add(Linear
                        .builder()
                        .setUnits(512).build())
                .add(Activation::relu)

                .add(Linear
                        .builder()
                        .setUnits(2).build());
    }

    public static void train(int batchSize) {
        Model model = createOrLoadModel();
        boolean training = true;
        //TODO create and instance of the game here??

        DefaultTrainingConfig config = setupTrainingConfig();

        try (Trainer trainer = model.newTrainer(config)) {
            trainer.initialize(new Shape(batchSize, 4, 80, 80));
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
                    this.agent.trainBatch((ai.djl.modality.rl.env.RlEnv.Step[]) batchSteps);
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
