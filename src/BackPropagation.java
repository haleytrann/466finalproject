import java.util.ArrayList;

public class BackPropagation {

    // might actually add this to NeuralNetwork class after?
    // update weights using actual values

    private ArrayList<Layer> layers;

    public void backpropagate(double[] inputs, double[] expectedOutputs, double learningRate) {

        // actual targets (need a feedForward method)
        // double[] actualOutputs = feedForward(features);

        // calc deltas for outer layer
        Layer outputLayer = layers.get(layers.size() - 1);
        for (int i = 0; i < outputLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = outputLayer.getNeuron(i);
            // double error = expectedOutputs[i] - actualOutputs[i];
            // neuron.setDelta(error * neuron.compute(inputs)); // need a setter in Neuron class for delta, derivative of sigmoid func
        }

        // calc deltas for hidden layers
        for (int i = layers.size() - 2; i >= 0; i++) {
            Layer currLayer = layers.get(i);
            Layer nextLayer = layers.get(i + 1);
            for (int j = 0; j < currLayer.getNumberOfNeurons(); j++) {
                Neuron neuron = currLayer.getNeuron(j);
                double error = 0.0;
//                for (Neuron nextNeuron : nextLayer.getNeurons()) {
//                    error += nextNeuron.getWeights()[j] * nextNeuron.getDelta();
//                }
//                neuron.setDelta(error * neuron.deriv());
            }
        }

        // update weights & biases for all layers
        double[] layerInputs = inputs;
        for (Layer layer : layers) {
            double[] nextInputs = new double[layer.getNumberOfNeurons()];
            for (int j = 0; j < layer.getNumberOfNeurons(); j++) {
                Neuron neuron = layer.getNeuron(j);
//                for (int k = 0; k < layerInputs.length; k++) {
//                    neuron.getWeights()[k] += learningRate * neuron.getDelta() * layerInputs[k];
//                }
//                neuron.setBias(neuron.getBias() + learningRate * neuron.setDelta());
//                nextInputs[j] = neuron.getOutput();
            }
            layerInputs = nextInputs;
        }
    }


    // train method ?
}
