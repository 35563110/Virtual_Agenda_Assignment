
/* 
 * Name: Carl, Anissa, Serena, Vincent
 * Date: 2021.5.19
 * Teacher: Mr Ho
 * Description: A virtual agenda
* */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

class Virtual_Agenda{
    public static void main (String[] args){
        // Read files?

        // initalizeMainUI(1, 2, 3, 4, 5); <-- This is how I think it might look
        initalizeMainUI();
    }

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
        calendar.setBackground(Color.white);
        calendar(calendar); // Will have to add more parameters to this
        gui.add(calendar);
        calendar.setLayout(null);

        // Dispalys the GUI window
        gui.setSize(800,400);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kill program if closed
        gui.setLayout(null);
        gui.setLocationRelativeTo(null); // Window appears in middle
        gui.setResizable(false); // Cannot maximize window
        gui.setVisible(true);
    }

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
        //sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always have a scroll bar appear
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
        saveTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                saveTask(); // Method that stores all data to file
            }
        });
        agenda.add(saveTask);

        // Button: Complete Task --------------------------------------------
        JButton completeTask = new JButton("🗸");
        completeTask.setBounds(300,315,75,25);
        completeTask.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                int row = table.getSelectedRow();
                try{
                    model.removeRow(row); // Send data to file before removing here?
                }
                catch (Exception e){ // If there is no row selected, do nothing
                    return;
                }
            }
        });
        agenda.add(completeTask);

        // Button: Settings --------------------------------------------
        JButton settings = new JButton("⚙");
        settings.setBounds(230,315,50,25);
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a){
                settingsWindow(); // Creates a new window for settings
            }
        });
        agenda.add(settings);
    }

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

    public static void completeTask(){
        // Deletes a task from table
    }

    public static void saveTask(){
        // Creates/overwrites the file containg the to do list data
    }

    public static void calendar (JPanel calendar){

    }
}
