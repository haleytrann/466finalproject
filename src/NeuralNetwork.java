public class NeuralNetwork {

    private int layerCount;
    private int[] neuronCountByLayer;
    private Layer[] layers; // array of neurons for each layer

    // ex: new NeuralNetwork(2, [64, 8]) for a NN with two hidden layers of size 64 and then 8
    public NeuralNetwork(int layerCount, int[] neuronCountByLayer) {
        this.layerCount = layerCount;
        this.neuronCountByLayer = neuronCountByLayer;
        layers = new Layer[layerCount];

        if(layerCount != neuronCountByLayer.length) {
            System.err.println("Neural network layers and neuron counts do not match.");
            System.exit(-1);
        }
        for(int i = 0; i < layerCount; i++) {
            int neuronCount = neuronCountByLayer[i];
            int prevCount = 57;
            if(i > 0) prevCount = neuronCountByLayer[i-1]; // neuron accepts inputs from all prev neurons
            layers[i] = new Layer(neuronCount, prevCount);
        }
    }

    // pass in inputs into neural network to get actual outputs
    public double[] computeOneIteration (double[] inputs) {
        double[] outputs = null;
        Layer currentLayer;
        for(int i = 0; i < layerCount; i++) { // loop through layers
            currentLayer = layers[i];
            outputs = new double[currentLayer.getNumberOfNeurons()]; // create output array for all neurons in the layer
            for(int j = 0; j < currentLayer.getNumberOfNeurons(); j++) { // loop through and compute each neuron
                outputs[j] = currentLayer.getNeuron(j).compute(inputs);
            }
            inputs = outputs;
        }
        return outputs;
    }


    public void backPropagate(double[] inputs, double[] expectedOutputs,
                              double[] actualOutputs, double learningRate) {

        // calc deltas for outer layer
        Layer outputLayer = layers[layers.length - 1]; // sorry my computer doesn't recognize getLast() :(
        // double prevErrors[] = new double[outputLayer.getNumberOfNeurons()]; // don't need since calc delta for each neuron based on error
        for (int i = 0; i < outputLayer.getNumberOfNeurons(); i++) {
            Neuron neuron = outputLayer.getNeuron(i);
            // NOTE: error for neurons other
            double error = expectedOutputs[i] - actualOutputs[i];
            // prevErrors[i] = error;
            double delta = error * sigmoidDerivative(actualOutputs[i]);
            neuron.setDelta(delta);
            neuron.changeOutputNeuronWeight(error, learningRate); // weight update for output layer
        }

        // calc deltas for hidden layers
        // for each hidden layer neuron, must update weights based on error of connecting neuron
        // aka loop through the following layer sending in all its errors
        for (int i = layers.length - 2; i >= 0; i--) {
            Layer currLayer = layers[i];
            Layer nextLayer = layers[i + 1];
            for (int j = 0; j < currLayer.getNumberOfNeurons(); j++) {
                Neuron neuron = currLayer.getNeuron(j);
                double error = 0.0;
                for (Neuron nextNeuron : nextLayer.getNeurons()) {
                    error += nextNeuron.getWeights()[j] * nextNeuron.getDelta();

                }

                double delta = error * sigmoidDerivative(neuron.getOutput()); // calculate output of neuron?
                neuron.setDelta(delta);
                neuron.changeHiddenNeuronWeight(learningRate); // weight update for hidden layers
                // ^ commented out temporarily
            }
        }

        // update weights & biases for all layers
//        double[] layerInputs = inputs;
//        for (Layer layer : layers) {
//            double[] nextInputs = new double[layer.getNumberOfNeurons()];
//            for (int j = 0; j < layer.getNumberOfNeurons(); j++) {
//                Neuron neuron = layer.getNeuron(j);
//                for (int k = 0; k < layerInputs.length; k++) {
//                    neuron.getWeights()[k] += learningRate * neuron.getDelta() * layerInputs[k];
//                }
////                neuron.setBias(neuron.getBias() + learningRate * neuron.setDelta());
////                nextInputs[j] = neuron.getOutput();
//            }
//            layerInputs = nextInputs;
//        }
    }


    public double returnOutputNeuron (int index, double[] neurons) {
        if (index < 0 || index >= neurons.length) {
            throw new IllegalArgumentException("Neuron index out of bounds");
        }
        double[] outputs = computeOneIteration(neurons);
        return outputs[index];
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        return sigmoid(x) * (1 - sigmoid(x));
    }
}
