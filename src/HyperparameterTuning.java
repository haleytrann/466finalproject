import java.util.*;

public class HyperparameterTuning {

    // try grid search
    private ArrayList<double[]> features;
    private ArrayList<double[]> targets;

    public HyperparameterTuning(ArrayList<double[]> features, ArrayList<double[]> targets) {
        this.features = features;
        this.targets = targets;
    }

    public HashMap<String, Object> tune() {

        // grid of hyperparameters
        int[][] hiddenLayerSizesGrid = {{32, 16}, {64, 32}, {128, 64}};
        double[] learningRateGrid = {0.1, 0.01, 0.001};
        int[] epochsGrid = {50, 75, 100};

        double bestF1Score = 0;
        HashMap<String, Object> bestHyperparameters = new HashMap<>();

        for (int[] hiddenLayerSizes : hiddenLayerSizesGrid) {
            for (double learningRate : learningRateGrid) {
                for (int epochs : epochsGrid) {
                    NeuralNetwork nn = new NeuralNetwork(3, hiddenLayerSizes);

                    // train model (same as in main class)
                    for (int epoch = 0; epoch < epochs; epoch++) {
                        for (int i = 0; i < features.size(); i++) {
                            double[] inputs = features.get(i);
                            double[] expectedOutputs = targets.get(i);
                            double[] outputs = nn.computeOneIteration(inputs);
                            nn.backPropagate(inputs, expectedOutputs, outputs, learningRate);
                        }
                    }

                    // evaluate the model
                    int correct = 0;
                    int total = features.size();
                    int truePositives = 0;
                    int falsePositives = 0;
                    int falseNegatives = 0;

                    for (int i = 0; i < total; i++) {
                        double[] outputs = nn.computeOneIteration(features.get(i));
                        int actualAnswer = (int) targets.get(i)[0];
                        boolean isSpam = outputs[0] >= 0.5;
                        boolean correctAnswer = (actualAnswer == 1);

                        if (isSpam) {
                            if (correctAnswer) {
                                truePositives++;
                            } else {
                                falsePositives++;
                            }
                        } else {
                            if (correctAnswer) {
                                falseNegatives++;
                            }
                        }

                        if ((isSpam && correctAnswer) || (!isSpam && !correctAnswer)) {
                            correct++;
                        }

                        double accuracy = (double) correct / total;
                        double precision = (double) truePositives / (truePositives + falsePositives);
                        double recall = (double) truePositives / (truePositives + falseNegatives);
                        double f1Score = 2 * (precision * recall) / (precision + recall);

                        System.out.println("Hidden Layers: " + java.util.Arrays.toString(hiddenLayerSizes) +
                                ", Learning Rate: " + learningRate +
                                ", Epochs: " + epochs +
                                ", Accuracy: " + accuracy +
                                ", Precision: " + precision +
                                ", Recall: " + recall +
                                ", F1 Score: " + f1Score);

                        if (f1Score > bestF1Score) {
                            bestF1Score = f1Score;
                            bestHyperparameters.put("hiddenLayerSizes", hiddenLayerSizes);
                            bestHyperparameters.put("learningRate", learningRate);
                            bestHyperparameters.put("epochs", epochs);
                        }
                    }
                }
            }
        }

        System.out.println("Best Hyperparameters:");
        System.out.println("Hidden Layers: " + java.util.Arrays.toString((int[]) bestHyperparameters.get("hiddenLayerSizes")));
        System.out.println("Learning Rate: " + bestHyperparameters.get("learningRate"));
        System.out.println("Epochs: " + bestHyperparameters.get("epochs"));
        System.out.println("Best F1 Score: " + bestF1Score);

        return bestHyperparameters;
    }
}
