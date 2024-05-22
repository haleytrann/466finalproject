import java.util.*;

public class Neuron {
    Random random = new Random();
    public double bias = random.nextDouble(-1, 1);
    public double [] weights = new double[58];
    public double compute(double [] inputs) {
        double initOutput = 0;
        for (int i = 0; i < inputs.length; i++) {
            initOutput += (inputs[i] * weights[i]);
        }
        return initOutput;
    }
}
