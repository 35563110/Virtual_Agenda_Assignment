/*
* Date: 
* Author:  
* Teacher:
* Description: *using dummy values* 
**/
 
import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

class ReadingWritingFile{
    public static void main(String[] args) {
        // assign variable to csv file 
        String writeFinishedFile = "testrun.csv";
        String writeUnfinishedFile = "finishedtestrun.csv";

        // declare global variables 
        
        // call methods
        unfinishedTasks(writeFinishedFile);
        readCSVFile(writeFinishedFile); 
        finishedTasks(writeUnfinishedFile);
        
    }
    public static void unfinishedTasks(String writeFile) {
        try{
            // arraylist of unfinished task names
            ArrayList<String> taskList = new ArrayList<String>();
            taskList.add("Math");
            taskList.add("Feb 22");
            taskList.add("Physics");
            taskList.add("Mar 17");
            taskList.add("Computer Science");
            taskList.add("Apr 30");
            taskList.add("Music");
            taskList.add("May 1");
            //System.out.println(dueDates.get(0));  testing out arraylists 
    
            // arraylist of unfinished due dates: i can trying using two arraylists- one for tasks, and the other for dates 
            //ArrayList<String> dueDate = new ArrayList<String>();

            // create file: 
            File fileName = new File(writeFile);
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter br = new BufferedWriter(fw);
    
            br.write("Due Date, Task");   // only displayed once, hence outside of for loop
            br.newLine();
            // the for loop iterates through the elements of the array, executing code tailored to each element 
            for(int i = 0; i<taskList.size(); i++){
                br.write(taskList.get(i+1) + "," + taskList.get(i++));
                br.newLine();
            }
           
            // close 
            br.close();
            fw.close();
            // print statements to notify user
            System.out.println("Data entered.");
            System.out.println("Finished writing to file.");
            
        }
        catch (IOException e) {
            System.out.println("Error writing to file.");

        }

    }
    /**
     * 
     */
    public static void finishedTasks(String writeFile){
        try{

            // arraylist of finished task names
            ArrayList<String> taskList = new ArrayList<String>();
            taskList.add("French");
            taskList.add("Feb 26");
            taskList.add("English");
            taskList.add("Mar 9");
            taskList.add("P.E.");
            taskList.add("Apr 21");
            taskList.add("Biology");
            taskList.add("May 10");
    
            File fileName = new File(writeFile);
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter br = new BufferedWriter(fw);
    
            br.write("Due Date, Task");
            br.newLine();
            for(int i = 0; i<taskList.size(); i++){
                br.write(taskList.get(i+1) + "," + taskList.get(i++));
                br.newLine();
            }
           
            // close 
            br.close();
            fw.close();
            System.out.println("Data entered.");
            System.out.println("Finished writing to file.");
            
        }
        catch (IOException e) {
            System.out.println("Error writing to file.");

        }

    }
    /**
     * methods reads the written csv, and stores the data into an array 
     * @param writeFile - passes file name --> tried it with "testrun.csv"
     * 
     */
    public static void readCSVFile(String writeFile){
        try{
            String line; 
            BufferedReader br = new BufferedReader(new FileReader(writeFile)); // FileReader opens csv file
            while ((line = br.readLine()) != null){     // read file line by line until end of file
                String[] taskArray = line.split(",");    // store data in array, and split strings given comma delimiter
                for (int i = 0; i < taskArray.length; i++){
                    System.out.println(taskArray[i]);
                }
            }
            br.close(); 
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }

    }
    
}