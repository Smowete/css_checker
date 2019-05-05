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

public class Crawler extends JFrame {
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 570;
    public static final int X_LOCATION = 100;
    public static final int Y_LOCATION = 50;
    
    //把节目分为上中三个Panel
    JPanel northPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel();
    
    //输入
    JTextField txtField = new JTextField(30);
    JLabel labelURL = new JLabel("Starting URL: ", JLabel.RIGHT);    
    //输出
    JTextArea txtArea = new JTextArea(10,40);
    JLabel labelTxt = new JLabel("Result");
    
    //按钮
    JButton startButton = new JButton("start");
    JButton cancelButton = new JButton("cancel");

    
    
    public void launchFrame() throws Exception{        
        //界面初始化
        this.setLocation(X_LOCATION, Y_LOCATION);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new GridLayout(3,1)); //总体为三行一列布局
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
    
    public static void main(String[] args)throws Exception {
         new Crawler().launchFrame();
    }
    
}