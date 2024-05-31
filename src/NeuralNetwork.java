public class NeuralNetwork {

    private int layerCount;
    private int[] neuronCountByLayer;
    private Layer[] neurons; // array of neurons for each layer

    // ex: new NeuralNetwork(2, [64, 8]) for a NN with two hidden layers of size 64 and then 8
    public NeuralNetwork(int layerCount, int[] neuronCountByLayer) {
        this.layerCount = layerCount;
        this.neuronCountByLayer = neuronCountByLayer;
        neurons = new Layer[layerCount];

        if(layerCount != neuronCountByLayer.length) {
            System.err.println("Neural network layers and neuron counts do not match.");
            System.exit(-1);
        }
        for(int i = 0; i < layerCount; i++) {
            int neuronCount = neuronCountByLayer[i];
            int prevCount = 57;
            if(i > 0) prevCount = neuronCountByLayer[i-1]; // neuron accepts inputs from all prev neurons
            neurons[i] = new Layer(neuronCount, prevCount);
        }
    }

    // pass in inputs into neural network to get actual outputs
    public double[] computeOneEpoch (double[] inputs) {
        double[] outputs = null;
        Layer currentLayer;
        for(int i = 0; i < layerCount; i++) { // loop through layers
            currentLayer = neurons[i];
            outputs = new double[currentLayer.getNumberOfNeurons()]; // create output array for all neurons in the layer
            for(int j = 0; j < currentLayer.getNumberOfNeurons(); j++) { // loop through and compute each neuron
                outputs[j] = currentLayer.getNeuron(j).compute(inputs);
            }
            inputs = outputs;
        }
        return outputs;
    }

    public double returnOutputNeuron (int index, double[] neurons) {
        if (index < 0 || index >= neurons.length) {
            throw new IllegalArgumentException("Neuron index out of bounds");
        }
        double[] outputs = computeOneEpoch(neurons);
        return outputs[index];
    }
}
