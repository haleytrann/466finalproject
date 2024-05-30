import java.util.*;

public class Neuron {
    Random random = new Random();
    public double bias = random.nextDouble(-1, 1);
    public double [] weights = new double[58];
    private double delta;
    public Neuron() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble(-1, 1);
        }
    }
    public double compute(double [] inputs) {
        double initOutput = 0;
        for (int i = 0; i < inputs.length; i++) {
            initOutput += (inputs[i] * weights[i]);
        }
        return (1/(1 + Math.exp(-(initOutput + bias))));
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

    // sigmoid / sigmoid derivative? (for individual)
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private Double sigmoidDerivative(Double x){
        return sigmoid(x) * (1 - sigmoid(x));
    }




}
