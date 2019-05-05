import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CssWindow extends JFrame {
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 570;
    public static final int X_LOCATION = 400;
    public static final int Y_LOCATION = 200;
    
    JPanel northPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel();
    
    JTextField txtField = new JTextField(25);
    JLabel labelURL = new JLabel("Input File Name: ", JLabel.RIGHT); 

    JTextArea txtArea = new JTextArea(10,40);
    JLabel labelTxt = new JLabel("Result");
    
    JButton startButton = new JButton("start");
    JButton cancelButton = new JButton("cancel");

    
    
    public void launchFrame() throws Exception{        
        this.setLocation(X_LOCATION, Y_LOCATION);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new GridLayout(3,1)); 
        this.add(northPanel);
        this.add(centerPanel);
        this.add(southPanel);
        
        northPanel.add(labelURL);
        northPanel.add(txtField);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(labelTxt);
        centerPanel.add(txtArea);
        southPanel.setLayout(new FlowLayout());
        southPanel.add(startButton);
        southPanel.add(cancelButton);
    
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String url = txtField.getText();
                txtArea.append(url+"\n");
            }
        });    
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                txtArea.setText("");
            }
        });    
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                System.exit(0);
            }
        });    
    }
        
}