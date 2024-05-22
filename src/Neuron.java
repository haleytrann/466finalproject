import java.util.*;

public class Neuron {
    Random random = new Random();
    public double bias = random.nextDouble(-1, 1);
    public double [] weights = new double[58];
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
}
