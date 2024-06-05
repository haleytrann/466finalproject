import java.util.*;

public class Neuron {
    Random random = new Random();
    public double bias = random.nextDouble(-1, 1);
    public double [] weights;
    private double numInputs;
    private double delta;
    private double activation; // most recent output of neuron


    public Neuron(int numInputs) {
        weights = new double[numInputs];
        for (int i = 0; i < numInputs; i++) {
            weights[i] = random.nextDouble(-1, 1);
        }
        this.numInputs = numInputs;
    }
    public double compute(double [] inputs) {
        double initOutput = 0;
        for (int i = 0; i < inputs.length; i++) {
            initOutput += (inputs[i] * weights[i]);
        }
        activation = (1/(1 + Math.exp(-(initOutput + bias))));
        return activation;
    }

    // getters & setters
    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public double getDelta() {
       return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getOutput() { // get compute value
        return activation;
    }

    // sigmoid / sigmoid derivative? (for individual)
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

//    private double sigmoidDerivative(double x) {
//        return sigmoid(x) * (1 - sigmoid(x));
//    }

    public double calculateOutput(double[] inputs) {
        double weightedSum = 0;
        for (int i = 0; i < inputs.length; i++) {
            weightedSum += inputs[i] * weights[i];
        }
        weightedSum += bias;

        double output = sigmoid(weightedSum); // apply activation function
        return output;
    }

    public void changeOutputNeuronWeight(double error, double[] inputs, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
            delta = (error * inputs[i] * learningRate); // confirm correctness
            weights[i] = weights[i] - delta; // fact check: add or subtract delta?
        }
    }

//    public void changeHiddenNeuronWeight(double[] connectingNeuronErrors, double learningRate) {
//        for (int i = 0; i < weights.length; i++) {
//            delta = (connectingNeuronErrors[i] * activation * learningRate); // confirm correctness
//            weights[i] = weights[i] - delta; // fact check: add or subtract delta?
//        }
//    }

    public void changeHiddenNeuronWeight(double learningRate) { // new, without prevErrors
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * delta * activation;
        }
        bias += learningRate * delta;
    }

}
