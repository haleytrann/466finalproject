import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Final {

    int data[][];

    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Requires data filepath as argument.");
            return;
        }
        File file = new File(args[0]);
        try {
            parseData(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void parseData(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        
    }
}
