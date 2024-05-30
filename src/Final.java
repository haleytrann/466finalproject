import java.io.*;
import java.util.*;

public class Final {

    public static int NUM_VARS = 58;
    public static int NUM_FEATURES = 57;
    public static int NUM_TARGETS = 1;


    static ArrayList<double[]> data = new ArrayList<>();

    public static void main(String[] args) {
        File file;
        // getting file path
        if(args.length < 1) {
            // our database is probably in current directory
            System.out.println("Using default path ('./spambase.data') for dataset.");
            file = new File("spambase.data");
        } else {
            // but you can also put in the path as an arg
            file = new File(args[0]);
        }

        try {
            parseData(file); // parse data into arrays

            // probably closer to what we want (hidden layer of size n, then a second smaller layer):
            NeuralNetwork nn = new NeuralNetwork(3, new int[]{57, 8, 1});

            ArrayList<double[]> features = prepareFeatures(data);
            ArrayList<int[]> targets = prepareTargets(data); // Arraylist of arrays of size 1 containing correct answers

            int correct = 0;
            for (int i = 0; i < features.size(); i++) {
                double[] outputs = nn.computeOneEpoch(features.get(i)); // just a test with the small example NN
                String result = "Yes";
                int actualAnswer = targets.get(i)[0];
                if (outputs[0] < 0.5) result = "No"; // only one output neuron -> only one value in array
                int predictedAnswer = (outputs[0] >= 0.5) ? 1 : 0;
                System.out.println("Output value: " + outputs[0]);
                System.out.println("Is Spam?: " + result);
                System.out.println("Actual Answer: " + (actualAnswer == 1 ? "Yes" : "No"));

                if (actualAnswer == predictedAnswer) {
                    correct ++;
                }
            }

            double accuracy = (double) correct / features.size();
            System.out.println(accuracy); // around .39 or .4 (for one epoch)

        } catch (FileNotFoundException e) { // catch file not found exception
            e.printStackTrace();
        }

        // print out parsed data
//        for (int i = 0; i < data.size(); i++) {
//            double[] array = data.get(i);
//            System.out.print("Array " + i + ": ");
//            for (int j = 0; j < array.length; j++) {
//                System.out.print(array[j] + " ");
//            }
//            System.out.println();
//
//        }
    }

    public static void parseData(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        int rowCount = 0;
        while(input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            double values[] = new double[NUM_VARS];
            for(int i = 0; i < NUM_VARS; i++) {
                values[i] = Double.parseDouble(line[i]);
            }
            data.add(values);
        }
    }

    // create input nodes (features) to put into neural network
    public static ArrayList<double[]> prepareFeatures(ArrayList<double[]> data) {
        ArrayList<double[]> features = new ArrayList<>();

        for (double[] entry : data) {
            double[] feature = new double[NUM_FEATURES]; // can adjust which nodes if we want different feature selection in the future
            System.arraycopy(entry, 0, feature, 0, 57);
            features.add(feature);
        }

        return features;
    }

    // create output nodes (targets)
    public static ArrayList<int[]> prepareTargets(ArrayList<double[]> data) {
        ArrayList<int[]> targets = new ArrayList<>();

        for (double[] entry : data) {
            int[] target = new int[NUM_TARGETS];
            target[0] = (int) entry[NUM_FEATURES];
            targets.add(target);
        }

        return targets;
    }
}
