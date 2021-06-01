/* 
* Name: Carl, Anissa, Serena, Vincent
* Date: 2021.5.19
* Teacher: Mr Ho
* Description: A virtual agenda
* */

import java.awt.event.*;
import java.awt.*;
import java.util.TimeZone;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import com.jcalendar.pane.calendar.CalendarPane;
import com.jcalendar.pane.clock.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;

class VATwo{
    public static void main(String[] args) {
        // reads the csv if it already exists to load tasks
        ArrayList<String> tasks = new ArrayList<String>();
        ArrayList<String> dueDates = new ArrayList<String>();
        ArrayList<String> taskList = new ArrayList<String>(); 
        
        readCSVFile(tasks, dueDates, taskList); // Reads CSV file for incomplete
        readCompleteCSV(taskList); // reads the CSV with the complete tasks for the arrayList

        initalizeMainUI(tasks, dueDates, taskList);
    }

    /**
     * @author: Carl Wang 
     * Base of operations for initalizing the UI
     * 
     * @param tasks - Contains the incomplete task names
     * @param duedates - Contains the incomplete task due dates
     * @param taskList - Contains data from both tasks and dueDates
     */
    public static void initalizeMainUI(ArrayList<String> tasks, ArrayList<String> dueDates, ArrayList<String> taskList) {
        // Initalizes GUI window
        JFrame gui = new JFrame("Virtual Agenda");
        // Initalizes JPanels: agenda, calendar
        JPanel agenda = new JPanel();
        agenda.setBounds(10, 10, 390, 390);
        agenda(agenda, tasks, dueDates, taskList);
        gui.add(agenda);
        agenda.setLayout(null);

        JPanel calendar = new JPanel();
        calendar.setBounds(400, 0, 400, 400);
        calendar(calendar); 
        gui.add(calendar);
        calendar.setLayout(null); // No layout

        // Dispalys the GUI window
        gui.setSize(680, 400);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kill program if closed
        gui.setLayout(null);
        gui.setLocationRelativeTo(null); // Window appears in middle
        gui.setResizable(false); // Cannot maximize window
        gui.setVisible(true);
    }

    /** 
     * @author - Carl Wang
     * @author - Anissa Rampersaud
     * Description: Method deals with the contents of the gui left side and handles interactions between the GUI and the various
     * arrayLists for the CSV's i.e taking in user input to store new tasks, remove finished ones, and add those to their specific CSV's
     * 
     * @param agenda - The agenda JPanel
     * @param tasks - Contains the incomplete task names
     * @param duedates - Contains the incomplete task due dates
     * @param taskList - Contains data from both tasks and dueDates
     */
    public static void agenda(JPanel agenda, ArrayList<String> tasks, ArrayList<String> dueDates, ArrayList<String> taskList) { 
        // JTable -----------------------------------------------------
        DefaultTableModel model = new DefaultTableModel(); // Table coloum and row data
        model.addColumn("Task Name");
        model.addColumn("Due Date");
        populateModel(model, tasks, dueDates);
        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(5, 5, 370, 300);
        
        // JTable Config
        table.getTableHeader().setResizingAllowed(false); // Cannot resize columns
        table.getTableHeader().setReorderingAllowed(false); // Cannot reorder colums
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always have a scroll bar appear
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Ensures theuser cannot multi select rows
        agenda.add(sp);

        // Button: Add Task -------------------------------------------------
        JButton addTask = new JButton("Add");
        addTask.setBounds(5, 315, 75, 25);
        addTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                model.addRow(new Object[] { "", "" }); // Adds a new row to the JTable
            }
        });
        agenda.add(addTask);

        // Button: save Task -----------------------------------------------
        JButton saveTask = new JButton("Save");
        saveTask.setBounds(90, 315, 75, 25);
        saveTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                populatetaskList(taskList, table);
                saveTask(taskList); // Method that stores all data to file (incomplete tasks)
            }
        });
        agenda.add(saveTask);

        // Button: Complete Task --------------------------------------------
        JButton completeTask = new JButton("Done");
        completeTask.setBounds(310, 315, 65, 25);
        // ANISSA: New arrayList to store the completed tasks 
        ArrayList<String> completeTasks = new ArrayList<>();
        completeTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                try {
                    populatetaskList(taskList, table);
                    for (int j = 0; j < 5; j++) { // Lowest IQ solution to multiple button presses to achieve something. Sue me.
                        for (int i = 1; i <= table.getSelectedRows().length; i++) {

                            // ANISSA: Adding a note to the completed tasks so in the array list, that element is altered to add the COMPLETED note
                            int rowNum = table.getSelectedRow(); // getting the selected row
                            String dataForTask = table.getModel().getValueAt(rowNum, 0).toString(); // converting data to string
                            String dataForDue = table.getModel().getValueAt(rowNum, 1).toString();
                            // finding the index of that string in the arrayList to alter and add the COMPLETED note
                            int index = taskList.indexOf(dataForTask + "~" + dataForDue);
                            // Adding the data from the taskList to the completesTasks array 
                            // with the "Completed" note to the task based on its index
                            completeTasks.add("COMPLETED: " + taskList.get(index));
                            // Removing the completed tasks from the taskList 
                            taskList.remove(index);

                            saveTask(taskList); // calling the method to saveTask to update the CSV file
                            saveComplete(completeTasks); // calling the method to save the complete tasks to the CSV

                            model.removeRow(table.getSelectedRow()); // Send data to file before removing here?
                            model.setValueAt("", table.getSelectedRow(), table.getSelectedColumn()); // Bug fix here. JTable would retain some data from removed row
                            model.setValueAt("", table.getSelectedRow(), table.getSelectedColumn() + 1); // These lines here will clear the data before removing the row
                        }
                    }
                    // Note: After doing multiple trials with these added lines, I couldn't get the bug to happen again, but I can't confirm if the bug is really gone or not...
                } catch (Exception e) { // If there is no row selected, do nothing
                    return;
                }
            }
        });
        agenda.add(completeTask);

        // Button: Clear Selection --------------------------------------------
        JButton clear = new JButton("Clear Selection");
        clear.setBounds(200, 315, 100, 25);
        clear.setMargin(new Insets(0, 0, 0, 0)); // Destroys default button-text margins
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                table.clearSelection(); // Clear the row selecton of the JTable
            }
        });
        agenda.add(clear);
    }

    /**
     * @author - 
     * 
     * @param taskList contains data about the incomplete task names and due dates
     */
    public static void saveTask(ArrayList<String> taskList) {
        try {
            // Serena's Code:
            // create file:
            File fileName = new File("Task_List.csv");
            FileWriter fw = new FileWriter(fileName, false);
            BufferedWriter br = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(br);

            // pw.println("Task, Due Date"); // only displayed once, hence outside of for
            // loop
            // br.newLine();
            // the for loop iterates through the elements of the array, executing code
            // tailored to each element
            for (int i = 0; i < taskList.size(); i++) {
                br.write(taskList.get(i));
                br.newLine();
            }

            // close
            pw.flush();
            pw.close();
            br.close();
            fw.close();
            // print statements to notify user
            System.out.println("Data entered.");
            System.out.println("Finished writing to file.");
        } 
        catch (IOException e) {
            System.out.println("Error writing to file.");
        }
        taskList.clear(); // Clears all data in arraylist
    }
   
    /**
     * @author - 
     * Method reads the csv file containing the incomplete tasks
     * 
     * @param tasks - Contains the incomplete task names
     * @param duedates - Contains the incomplete task due dates
     * @param taskList - Contains data from both tasks and dueDates
     */
    public static void readCSVFile(ArrayList<String> tasks, ArrayList<String> dueDates, ArrayList<String> taskList){ //remove the parameter as it's not needed to read a file.
        try{
            File taskFile = new File("Task_List.csv");
            Scanner reader = new Scanner (taskFile);
            reader.useDelimiter("~");
            while (reader.hasNext()){
                String line = reader.nextLine();
                taskList.add(line);
                populateArrays(line, tasks, dueDates);
            }
            System.out.println("Success reading Task List CSV");
            reader.close();
        }
        catch(IOException e){
        System.out.println("Error reading file.");
        }
        taskList.clear(); // Clears all data in arraylist
    }

    /**
     * @author - Anissa Rampersaud
     * Description: Reading through the Complete Tasks CSV and adding those elements to the arrayList 'completeTasks' 
     * 
     * @param completeTasks - String arrayList containing all the completed tasks of the user for the time the program runs
     *                   
     */
    public static void readCompleteCSV(ArrayList<String> completeTasks){
        try{
            // Setting the file to read
            File completeFile = new File("Complete_Tasks.csv");
            Scanner reader = new Scanner(completeFile);
            reader.useDelimiter("~");
            while(reader.hasNext()){
                String line = reader.nextLine(); // reading through each line
                completeTasks.add(line); // adding each line to the arrayList
            }
            // Notifying the user when it's done being read
            System.out.println("Finished reading Complete Task CSV");
            reader.close();
        }
        catch(IOException e){
            System.out.println("Error reading CSV");
        }
    }
    /**
     * @author - Carl Wang 
     * Method deals with things that appear on the right side of the GUI
     * 
     * @param calendar - The JPanel calendar
     */
    public static void calendar(JPanel calendar) {
        // Calendar
        CalendarPane cal = new CalendarPane(TimeZone.getTimeZone("America/Toronto"));
        cal.setBounds(0, 10, 248, 138);
        calendar.add(cal);

        // Clock
        Clock clock = new Clock();
        clock.setTimeZone(TimeZone.getTimeZone("America/Toronto"));
        clock.setBounds(47, 165, 250, 150);
        clock.startClock();
        calendar.add(clock);
    }

    /**
     * @author - Carl Wang
     * Method takes data from the CSV file and converts it into dataTypes
     * 
     * @param fromFile - Data from the incomplete tasks csv file
     * @param tasks - Arraylist (String) that will contain the task name
     * @param dueDates - Arraylist (String) that will contain the task due date
    **/
    public static void populateArrays (String fromFile, ArrayList<String> tasks, ArrayList<String> dueDates){
        String taskString = "";
        String dueString = "";

        int divider = fromFile.indexOf("~"); // Crucial part of code

        fromFile = fromFile.trim(); // Removes leading and trailing spaces

        try{
            taskString = fromFile.substring(0, divider);
            dueString = fromFile.substring(divider+1, fromFile.length());
        }
        catch (Exception e){

        }
        tasks.add(taskString);
        dueDates.add(dueString);
    }

    /**
     * @author - Carl Wang
     * Method populates the DefaultTableModel
     * 
     * @param model - DefaultTableModel
     * @param tasks - Arraylist (String) that contain the task name
     * @param dueDates - Arraylist (String) that contain the task due date
    **/
    public static void populateModel (DefaultTableModel model, ArrayList<String> tasks, ArrayList<String> dueDates){
        for (int i = 0; i < tasks.size(); i++){
            model.addRow(new Object[] {(tasks.get(i)), dueDates.get(i)});
        }
    }

    /**
     * @author - Anissa Rampersaud
     * Description: Takes info from completeTasks arrayList and puts onto a separate CSV called "Complete_Tasks.csv"
     * 
     * @param completeTasks - Calling completeTasks arrayList to get data
     */
     public static void saveComplete (ArrayList<String> completeTasks){
        try{
            // reading through the file to add previous completed tasks
            readCompleteCSV(completeTasks);
            // create new file + add data to it like normal
            File completeCSV = new File("Complete_Tasks.csv");        
            FileWriter fw = new FileWriter(completeCSV, false);
            BufferedWriter br = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(br);

            for (int i = 0; i < completeTasks.size(); i++) {
                br.write(completeTasks.get(i));
                br.newLine();
            }
            
            // close
            pw.flush();
            pw.close();
            br.close();
            fw.close();
            
            // print statements to notify user
            System.out.println("Data entered.");
            System.out.println("Finished writing to Complete Tasks file.");
        }
        catch(IOException e){
            System.out.println("Error writing to Complete_Tasks.csv");
        }
        completeTasks.clear(); // clearing the arrayList to avoid any repeats when adding new completed tasks
    }

    /**
     * @author - Anissa Rampersaud
     * Description: Method populates the arrayList, taskList, when the user hits save on their added task. This method also
     * re-populates the arrayList as for CSV function it needs to be cleared, so this populates before it is cleared
     * 
     * @param taskList - Contains incomplete tasks
     * @param table - The JTable and its data
     */
    public static void populatetaskList (ArrayList<String> taskList, JTable table){
        // ANISSA: Getting the data from the selected row and first column, converting it into a string and saving
        // the info into an arrayList called taskList
        int column = 0; // column for the tasks
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing(); // Fixes bug. Cancels editing once the save button is hit
        }
        for (int i = 0; i < table.getRowCount(); i++){
            Object userInput = table.getModel().getValueAt(i, column); // getting data from the location of the row and column
            String task = userInput.toString(); // converting that data from an object to a string
    
            // Doing the same thing as above but with the due dates
            int column2 = 1;
            Object userDueDate = table.getModel().getValueAt(i, column2);
            String dueDate = userDueDate.toString();
            // Storing both the task and the due date into the array, 'taskList'
            taskList.add(task + "~" + dueDate);
        }
    }
}

/* Code will remain here in case if needed in the future - Carl
public static void settingsWindow(){
    // Initalize new window
    JFrame settings = new JFrame("Settings");
    // Creates the GUI elements
    JButton apply = new JButton("Apply");
    apply.setBounds(200,210,75,25);
    settings.add(apply);
    JButton cancel = new JButton("Cancel");
    cancel.setBounds(60,210,75,25);
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent a){
            settings.setVisible(false);
        }
    });
    settings.add(cancel);
    // Dispalys the GUI window
    settings.setSize(350,300);
    settings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    settings.setLayout(null);
    settings.setLocationRelativeTo(null); // Window appears in middle
    settings.setResizable(false); // Cannot maximize window
    settings.setVisible(true);
}
*/