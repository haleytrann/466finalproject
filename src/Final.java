import java.io.*;
import java.util.*;

public class Final {

    public static int NUM_FEATURES = 58;
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
            NeuralNetwork nn = new NeuralNetwork(1, new int[]{5});
            // creates neural network with one hidden layer of size 58

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

        ArrayList<double[]> features = prepareFeatures(data);
        ArrayList<int[]> targets = prepareTargets(data);
    }

    public static void parseData(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        int rowCount = 0;
        while(input.hasNextLine()) {
            String[] line = input.nextLine().split(",");
            double values[] = new double[NUM_FEATURES];
            for(int i = 0; i < NUM_FEATURES; i++) {
                values[i] = Double.parseDouble(line[i]);
            }
            data.add(values);
        }
    }

    // create input nodes (features) to put into neural network
    public static ArrayList<double[]> prepareFeatures(ArrayList<double[]> data) {
        ArrayList<double[]> features = new ArrayList<>();

        for (double[] entry : data) {
            double[] feature = new double[57]; // can adjust which nodes if we want different feature selection in the future
            System.arraycopy(entry, 0, feature, 0, 57);
            features.add(feature);
        }

        return features;
    }

    // create output nodes (targets)
    public static ArrayList<int[]> prepareTargets(ArrayList<double[]> data) {
        ArrayList<int[]> targets = new ArrayList<>();

        for (double[] entry : data) {
            int[] target = new int[1];
            target[0] = (int) entry[57];
            targets.add(target);
        }

        return targets;
    }
}
