/* 
 * Name: Carl, Anissa, Serena, Vincent
 * Date: 2021.5.19
 * Teacher: Mr Ho
 * Description: A virtual agenda
* */

import javax.swing.*;
//import java.awt.event.*;

class Virtual_Agenda{
    public static void main (String[] args){
        // Read files?

        // initalizeMainUI(1, 2, 3, 4, 5); <-- This is how I think it might look
        initalizeMainUI();
    }

    public static void initalizeMainUI(){
        // Initalizes GUI window
        JFrame gui = new JFrame("Virtual Agenda");

        // Dispalys the GUI window
        gui.setSize(800,400);
        gui.setLayout(null);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLocationRelativeTo(null); // Window appears in middle
        gui.setVisible(true);
    }
}
