public class NeuralNetwork {

    private int layerCount;
    private int[] neuronCountByLayer;
    private Neuron[][] neurons; // array of neurons for each layer

    // ex: new NeuralNetwork(2, [64, 8]) for a NN with two hidden layers of size 64 and then 8
    public NeuralNetwork(int layerCount, int[] neuronCountByLayer) {
        this.layerCount = layerCount;
        this.neuronCountByLayer = neuronCountByLayer;
        neurons = new Neuron[layerCount][];

        if(layerCount != neuronCountByLayer.length) {
            System.err.println("Neural network layers and neuron counts do not match.");
            System.exit(-1);
        }
        for(int i = 0; i < layerCount; i++) {
            int neuronCount = neuronCountByLayer[i];
            neurons[i] = new Neuron[neuronCount];
            for(int j = 0; j < neuronCount; j++) {
                neurons[i][j] = new Neuron();
            }
        }
    }
}
