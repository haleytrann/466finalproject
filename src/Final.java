import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Final {

    public static int NUM_FEATURES = 58;
    static double[][] data = new double[4601][NUM_FEATURES];

    public static void main(String[] args) {
        File file;
        // getting file path
        if(args.length < 1) {
            // our database is probably in current directory
            System.out.println("Using default path ('./spambase.data' for dataset.");
            file = new File("spambase.data");
        } else {
            // but you can also put in the path as an arg
            file = new File(args[0]);
        }

        try {
            parseData(file); // parse data into arrays

        } catch (FileNotFoundException e) { // catch file not found exception
            e.printStackTrace();
        }
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
            data[rowCount++] = values;
        }
    }
}
