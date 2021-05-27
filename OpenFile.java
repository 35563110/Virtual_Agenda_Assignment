
// Reference: https://www.javatpoint.com/java-jfilechooser

// Secondary feature for the user to specify the location of a pre-existing file

import javax.swing.*;    
import java.awt.event.*;    
import java.io.*;    

class OpenFile {
    public static void main (String[] args){
        JFrame f = new JFrame("Inferface");
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open File");

        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (e.getSource() == open){    
                    JFileChooser fc = new JFileChooser();    
                    int i = fc.showOpenDialog(null);    
                    if (i == JFileChooser.APPROVE_OPTION){    
                        File f = fc.getSelectedFile();    
                        String filepath = f.getPath(); // Main point of all this code
                        System.out.println(filepath);                  
                    }    
                }    
            }          
        });
        mb.setBounds(0,0,400,20);
        f.setSize(400,400);
        file.add(open);
        mb.add(file);
        f.add(mb);

        f.setFocusable(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kill program if closed
        f.setLayout(null);
        f.setLocationRelativeTo(null); // Window appears in middle
        f.setResizable(false); // Cannot maximize window
        f.setVisible(true);
    }
}
