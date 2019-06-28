/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the EditorJFrame.
 */

/*try {
        // Set metl look and feel
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        // Set theme to ocean
        MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {

        } */

//to do
//make options menu 

package fortest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author 161067
 */
public class EditorJFrame extends JFrame implements ActionListener { 
    //Frame 
    JFrame frame;     
    //Menu Bar
    JMenuBar menuBar;
    //TextField that show the file path.
    JTextField filePathTextField= new JTextField("New File");
    //The Path of file
    String curPath="";
    // Text component 
    JTextArea textArea; 
    
    // Constructor 
    EditorJFrame(){ 
        // Create a frame 
        frame = new JFrame("Text Editor"); 
        // Text component 
        textArea = new JTextArea(); 
        // Create a menubar 
        menuBar = new JMenuBar(); 
        // Create amenu for menu 
        JMenu menu1 = new JMenu("File"); 
        // Create menu items 
        JMenuItem[] m1= new JMenuItem[6];
        
        m1[0] = new JMenuItem("New"); 
        m1[1] = new JMenuItem("Open"); 
        m1[2] = new JMenuItem("Save");
        m1[3] = new JMenuItem("Save as");
        m1[4] = new JMenuItem("Statistics");
        m1[5] = new JMenuItem("Exit");
        // Add action listener 
        for(int i=0;i<6;i++){
            m1[i].addActionListener(this);
            menu1.add(m1[i]);
            if(i==3||i==4){
                menu1.addSeparator();
            }
        }

        JMenu menu2 = new JMenu("Edit"); 
        // Create menu items 
        JMenuItem[] m2= new JMenuItem[6];
        m2[0] = new JMenuItem("Cut"); 
        m2[1] = new JMenuItem("Copy"); 
        m2[2] = new JMenuItem("Paste");
        m2[3] = new JMenuItem("Clear All");
        m2[4] = new JMenuItem("Select All");
        m2[5] = new JMenuItem("Time/Date");
        
        // Add action listener 
        for(int i=0;i<6;i++){
            m2[i].addActionListener(this);
            menu2.add(m2[i]);
            if(i==2||i==4){
                menu2.addSeparator();
            }
        }

        JMenu menu3=new JMenu("About");
        
        JMenuItem m30=new JMenuItem("About TextEditor");
        
        m30.addActionListener(this);
        
        menu3.add(m30);
        
        menuBar.add(menu1); 
        menuBar.add(menu2); 
        menuBar.add(menu3); 
        
        //Buttons
        JButton[] buttons =new JButton[7];
        buttons[0] = new JButton("New"); 
        buttons[1] = new JButton("Open"); 
        buttons[2] = new JButton("Save");
        buttons[3] = new JButton("Clear All");
        buttons[4] = new JButton("Save as");
        buttons[5] = new JButton("Statistics");
        buttons[6] = new JButton("Exit");
        
        for (JButton button : buttons) {
            button.addActionListener(this);
            button.setBackground(Color.white);
            button.setFont(new Font(null,Font.BOLD,16));
        }
        
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(1,7));
        for(int i=0;i<7;i++){ 
            buttonPanel.add(buttons[i]);
        }
        buttonPanel.setMaximumSize(new Dimension(2000,40));
        
        JScrollPane scrollPaneofTextField = new JScrollPane(filePathTextField);
        scrollPaneofTextField.setMaximumSize(new Dimension(2000,30));
        filePathTextField.addActionListener(this);
        //Without this there are some lines bellow TextField
        scrollPaneofTextField.setUI(null);
        
        JScrollPane scrollPaneofTextArea = new JScrollPane(textArea);
        
        //Add to frame
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                m1[5].doClick();
            }
        });
        frame.setJMenuBar(menuBar);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(buttonPanel);
        frame.add(scrollPaneofTextField); 
        frame.add(scrollPaneofTextArea); 
        frame.setSize(800, 600); 
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    } 

    // If a button is pressed 
    @Override
    public void actionPerformed(ActionEvent e){ 
        String actionCommand = e.getActionCommand(); 
        boolean isNewFile=false;
        
        if(actionCommand.equals("New")){    
            textArea.setText(""); 
            curPath="";
            isNewFile=true;
        }else if(actionCommand.equals("Save")){
            SaveAndOpen.save(frame,textArea,curPath);
        }else if(actionCommand.equals("Open")){ 
            JFileChooser jfc = new JFileChooser("f:"); 
            
            if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
                SaveAndOpen.open(frame, textArea, curPath=jfc.getSelectedFile().getAbsolutePath());
            }else{
                JOptionPane.showMessageDialog(frame, "The user cancelled the operation");
            }
        }else if(actionCommand.equals("Save as")){  
            JFileChooser jfc = new JFileChooser("f:"); 
            
            if (jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION){
                SaveAndOpen.save(frame, textArea, curPath=jfc.getSelectedFile().getAbsolutePath());
            }else{
                JOptionPane.showMessageDialog(frame, "The user cancelled the operation");
            }
        }else if(actionCommand.equals("Statistics")){
            String statistics="";
            if(curPath.equals("")){
                statistics="New file";
            }else{
                File file = new File(curPath); 
                //find name
                statistics+="File Name:\t\t\t\t"+file.getName()+"\n";
                //find size
                statistics+="Size:\t\t\t\t\t&nbsp;";
                double size= file.length();
                if(size>1024){
                    size/=1024;
                    if(size>1024){
                        size/=1024;
                        if(size>1024){
                            size/=1024;
                            statistics+=size+" gigabytes";
                        }else{
                            statistics+=size+" megabytes";
                        }
                    }else{
                        statistics+=size+" kilobytes";
                    }
                }else{
                    statistics+=size+" bytes";
                }
                statistics+="\n";
                //find letters,words,lines
                int wordCounter=0,letterCounterNoSpace=0,spaceCounter=0,lineCounter=1,paragraphCounter=1;
                
                if(file.length()!=0){
                    wordCounter=1;
                }
                
                try{ 
                    FileReader fr = new FileReader(file); 

                    BufferedReader br = new BufferedReader(fr); 
                    int temp;
                    boolean paragraph=true;
                    while ((temp=br.read())!=-1) { 
                        //System.out.println((char)fr.read());
                        if(temp!=' '){
                            letterCounterNoSpace++;
                            if(temp=='\n'){
                                lineCounter++;
                                //checks if new line and not new paragraph, turns false if new line, will turn true if the next character is 
                                //new line again which will mean new paragraph
                                paragraph=!paragraph;
                                //αν εχει newline χωρις χαρακτηρα το μετραει ως λεξη
                                
                                wordCounter++;
                                if(paragraph){
                                    paragraphCounter++;
                                    //Double new line isn't a word so undo the previous ++;
                                    wordCounter--;
                                }
                            }else{
                                paragraph=true;
                            }
                        }else{
                            spaceCounter++;
                            wordCounter++;
                        }
                    } 
                }catch (IOException evt) { 
                    JOptionPane.showMessageDialog(frame, "No such file.","No such file.",ERROR_MESSAGE); 
                }
                
                statistics+="Words:\t\t\t\t\t"+wordCounter+"\n";
                statistics+="Characters (No Space):\t"+letterCounterNoSpace+"\n";
                statistics+="Characters (With Space):&nbsp;&nbsp;"+(letterCounterNoSpace+spaceCounter)+"\n";
                statistics+="Paragraphs:\t\t\t&nbsp;&nbsp;&nbsp;"+paragraphCounter+"\n";
                statistics+="Lines:\t\t\t\t\t"+lineCounter+"\n";
            }
            //label to change the size and font
            //html so that new lines can be displayed properly
            JLabel statisticsLabel=new JLabel("<html>" +statistics.replaceAll("\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")+"<html>"); //
            statisticsLabel.setFont(new Font("Courier New",Font.BOLD,11));
            JOptionPane.showMessageDialog(frame, statisticsLabel, "File Statistics", JOptionPane.INFORMATION_MESSAGE);
        }else if(actionCommand.equals("Exit")) { 
            if(JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Exit Window", 0)==JFileChooser.APPROVE_OPTION){ 
                System.exit(0);
            }
        }else if(actionCommand.equals("Cut")) { 
            textArea.cut(); 
        }else if(actionCommand.equals("Copy")) { 
            textArea.copy(); 
        }else if(actionCommand.equals("Paste")) { 
            textArea.paste();
        }else if(actionCommand.equals("Clear All")){
            if(JOptionPane.showConfirmDialog(frame, "Are you sure you want clear all? (There is no undo button)", "Clear All?", 0)==JFileChooser.APPROVE_OPTION){ 
                textArea.setText("");
            }
        }else if(actionCommand.equals("Select All")){
            textArea.selectAll();
        }else if(actionCommand.equals("Time/Date")){
            //το format ειναι ιδιο με του Notepad
            textArea.insert(java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm a dd-MM-yyyy"))+"",
                            textArea.getCaretPosition());
        }else if(actionCommand.equals("About TextEditor")){
            JOptionPane.showMessageDialog(frame, "Made in 2019\nby Γιώργος Λυδιώτης 161067", "About Text Editor", JOptionPane.INFORMATION_MESSAGE);
        }else if(actionCommand.equals(filePathTextField.getText())){ //when filepath is clicked enter
            SaveAndOpen.open(frame, textArea, curPath=filePathTextField.getText());
        }
        
        if(isNewFile||curPath.equals("")){
            filePathTextField.setText("New File");
        }else{
            filePathTextField.setText(curPath);
        }
        filePathTextField.setAlignmentY(LEFT_ALIGNMENT);
    }
} 