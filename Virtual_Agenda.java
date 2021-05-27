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
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

class Virtual_Agenda{
    public static void main (String[] args){
       //reads the csv if it already exists to load tasks
        readCSVFile();
        // initalizeMainUI(1, 2, 3, 4, 5); <-- This is how I think it might look
        initalizeMainUI();
    }

    /*
     * @author: Carl Wang
     * Base of operations for initalizing the UI
     * */
    public static void initalizeMainUI(){
        // Initalizes GUI window
        JFrame gui = new JFrame("Virtual Agenda");
        // Initalizes JPanels: agenda, calendar
        JPanel agenda = new JPanel();
        agenda.setBounds(10,10,390,390);
        agenda(agenda); // Will have to add more parameters to this
        gui.add(agenda);
        agenda.setLayout(null);
        
        JPanel calendar = new JPanel();
        calendar.setBounds(400,0,400,400);
        calendar(calendar); // Will have to add more parameters to this
        gui.add(calendar);
        calendar.setLayout(null);


        // Dispalys the GUI window
        gui.setSize(680,400);
        gui.setFocusable(true); // Necessary for keylistener
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kill program if closed
        gui.setLayout(null);
        gui.setLocationRelativeTo(null); // Window appears in middle
        gui.setResizable(false); // Cannot maximize window
        gui.setVisible(true);
    }

    /*
     * @author - Carl Wang
     * 
     * @param JPanel agenda - Deals with stuff on the left side of the GUI
     * */
    public static void agenda (JPanel agenda){ // Going to need string data
        // JTable -----------------------------------------------------
        DefaultTableModel model = new DefaultTableModel(); // Table coloum and row data
        model.addColumn("Task Name");
        model.addColumn("Due Date");
        JTable table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(5,5,370,300);
        // JTable Config
        table.getTableHeader().setResizingAllowed(false); // Cannot resize columns
        table.getTableHeader().setReorderingAllowed(false); // Cannot reorder colums
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always have a scroll bar appear
        //table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Ensures the user cannot multi select rows
        agenda.add(sp);
        // Button: Add Task -------------------------------------------------
        JButton addTask = new JButton("Add");
        addTask.setBounds(5,315,75,25);
        addTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                model.addRow(new Object[]{"", ""}); // Adds a new row to the JTable
            }
        });
        agenda.add(addTask);
        // Button: save Task -----------------------------------------------
        JButton saveTask = new JButton ("Save");
        saveTask.setBounds(90,315,75,25);
        // ANISSA: ArrayList used to store all the tasks the user inputs (put outside so it doesn't keep resetting)
        // moved the arraylist back a few lines since it just didnt exist with the complete task button for some reaso
        ArrayList<String> taskList = new ArrayList<>();
        saveTask.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent a){
                // ANISSA: Getting the data from the selected row and first column, converting it into a string and saving
                // the info into an arrayList called taskList
                int column = 0; // column for the tasks
                int row = table.getSelectedRow(); // the location of the selected row
                Object userInput = table.getModel().getValueAt(row, column); // getting data from the location of the row and column
                String task = userInput.toString(); // converting that data from an object to a string

                // Doing the same thing as above but with the due dates
                int column2 = 1;
                Object userDueDate = table.getModel().getValueAt(row,column2);
                String dueDate = userDueDate.toString();
                // Storing both the task and the due date into the array, 'taskList'
                taskList.add(task + ", " + dueDate); 

                System.out.println(taskList);
                saveTask(taskList); // Method that stores all data to file
                
                //There is a bug when saving that you have to one exactly one blank line to successfully save to file.
            }
        });
        agenda.add(saveTask);
        // Button: Complete Task --------------------------------------------
        JButton completeTask = new JButton("?");
        completeTask.setBounds(310,315,65,25);
        completeTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                try{
                    for (int j = 0; j < 3; j++){ // Lowest IQ solution to multiple button presses to achieve something. Sue me.
                        for (int i = 1; i <= table.getSelectedRows().length; i++){

                            // ANISSA: Adding a note to the completed tasks so in the array list, that element is altered to add
                            // the COMPLETED note
                            int rowNum = table.getSelectedRow();    // getting the selected row
                            String dataForTask = table.getModel().getValueAt(rowNum, 0).toString(); // converting data to string
                            String dataForDue = table.getModel().getValueAt(rowNum, 1).toString();
                            // finding the index of that string in the arrayList to alter and add the COMPLETED note
                            int index = taskList.indexOf(dataForTask + ", " + dataForDue);
                            // Editing the taskList to add the "Completed" note to the task based on its index
                            taskList.set(index,"COMPLETED: " + taskList.get(index));
                            System.out.println(taskList);
           
                            saveTask(taskList);
                            model.removeRow(table.getSelectedRow()); // Send data to file before removing here?
                            model.setValueAt("", table.getSelectedRow(), table.getSelectedColumn()); // Weird bug fix here. JTable would retain some data from removed row
                            model.setValueAt("", table.getSelectedRow(), table.getSelectedColumn()+1); // These lines here will clear the data before removing the row
                        }
                    }   
                    // Note: After doing multiple trials with these added lines, I couldn't get the bug to happen again, 
                    //       but I can't confirm if the bug is really gone or not...
                }
                catch (Exception e){ // If there is no row selected, do nothing
                    return;
                }
            }
        });
        agenda.add(completeTask);
        // Button: Clear Selection --------------------------------------------
        JButton clear = new JButton("Clear Selection");
        clear.setBounds(200,315,100,25);
        clear.setMargin(new Insets(0, 0, 0, 0)); // Destroys default button-text margins
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                table.clearSelection();
            }
        });
        agenda.add(clear);

        // Date Picker
    }

    /* Code will remain here in case if needed in the future
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


    public static void saveTask(ArrayList<String> taskList){ //changed paramater to the arraylist
      try {
     //Serena's Code:
        // create file: 
        File fileName = new File("Task_List.csv");
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter br = new BufferedWriter(fw);
      
        br.write("Task, Due Date");   // only displayed once, hence outside of for loop
        br.newLine();
        // the for loop iterates through the elements of the array, executing code tailored to each element 
        for(int i = 0; i < taskList.size(); i++){
          br.write(taskList.get(i));
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
    
    public static void readCSVFile(){ //remove the parameter as it's not needed to read a file.
      try{
          String line; 
          BufferedReader br = new BufferedReader(new FileReader("Task_List.csv")); // FileReader opens csv file
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
    /*
     * @author - Carl Wang
     * Method deals with things that appear on the right side of the GUI
     * */
      public static void calendar (JPanel calendar){
        // Calendar
        CalendarPane cal = new CalendarPane(TimeZone.getTimeZone("America/Toronto"));
        cal.setBounds(0,10,248,138);
        calendar.add(cal);
        
        // Clock
        Clock clock = new Clock();
        clock.setTimeZone(TimeZone.getTimeZone("America/Toronto"));
        clock.setBounds(47,165,250,150);
        clock.startClock();
        calendar.add(clock);

    }
}
