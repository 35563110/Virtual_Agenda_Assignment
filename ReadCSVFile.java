import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadCSVFile {
    public static void main (String[] args) throws FileNotFoundException {
        File taskFile = new File("Task_List.csv");
        Scanner reader = new Scanner (taskFile);
        reader.useDelimiter("~");
        while (reader.hasNext()){
            String line = reader.nextLine();
            System.out.println(line);
        }
        reader.close();
    }
}
