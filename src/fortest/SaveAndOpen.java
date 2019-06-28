package fortest;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTextArea;

/**
 * @author Giorgos Lydiotis
 */
public class SaveAndOpen {
    public static void save(JFrame frame,JTextArea textArea,String curPath){
        try{
            File file = new File(curPath); 
            try{ 
                FileWriter fw = new FileWriter(file, false); 

                BufferedWriter bw = new BufferedWriter(fw); 

                bw.write(textArea.getText()); 

                bw.flush(); 
                bw.close(); 
            }catch (IOException e) { 
                JOptionPane.showMessageDialog(frame, "You haven't selected a file name. Click \"Save as\" instead.","Error",0); 
            }
        }catch(HeadlessException e){
            JOptionPane.showMessageDialog(frame, "You haven't selected a file name. Click \"Save as\" instead.","Error",0); 
        }

    }
    
    public static void open(JFrame frame, JTextArea textArea, String curPath){
        File file = new File(curPath); 

        try { 
            String s1 = "", s2 = ""; 

            FileReader fr = new FileReader(file); 

            BufferedReader br = new BufferedReader(fr); 

            s2 = br.readLine(); 

            while ((s1 = br.readLine()) != null) { 
                s2 = s2 + "\n" + s1; 
            } 

            textArea.setText(s2); 
        }catch (IOException evt) { 
            JOptionPane.showMessageDialog(frame, "No such file.","No such file.",ERROR_MESSAGE);  
        }
    }
}