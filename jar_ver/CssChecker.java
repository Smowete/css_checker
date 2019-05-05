
import java.util.*;
import java.io.*;

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


public class CssChecker extends JFrame {
   
   public static final int BOARD_WIDTH = 540;
   public static final int BOARD_HEIGHT = 570;
   public static final int X_LOCATION = 400;
   public static final int Y_LOCATION = 200;
   
   JPanel northPanel1 = new JPanel();
   JPanel northPanel2 = new JPanel();
   JPanel centerPanel = new JPanel();
   JPanel southPanel = new JPanel();
   
   JTextField inputFileName = new JTextField(30);
   JLabel inputFile = new JLabel("Input File Name: ", JLabel.RIGHT); 
   JTextField outputFileName = new JTextField(30);
   JLabel outputFile = new JLabel("Output File Name: ", JLabel.RIGHT); 

   JTextArea resultText = new JTextArea(6,40);
   JLabel result = new JLabel("Result:");
    
   JButton start1 = new JButton("Find Duplicates");
   JButton start2 = new JButton("Automatically Generate");
   JButton clear = new JButton("Clear");
   
   public void launchFrame() throws Exception{
      this.setLocation(X_LOCATION, Y_LOCATION);
      this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
      this.setResizable(false);
      this.setVisible(true);
      this.setLayout(new GridLayout(4,1));
      this.add(northPanel1);
      this.add(northPanel2);
      this.add(centerPanel);
      this.add(southPanel);
      
      northPanel1.add(inputFile);
      northPanel1.add(inputFileName);
      northPanel2.add(outputFile);
      northPanel2.add(outputFileName);
      centerPanel.setLayout(new FlowLayout());
      centerPanel.add(result);
      centerPanel.add(resultText);
      southPanel.setLayout(new FlowLayout());
      southPanel.add(start1);
      southPanel.add(start2);
      southPanel.add(clear);
      
      start1.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try {
               Scanner input = new Scanner(new File(inputFileName.getText()));
               findDuplicates(input);
            } catch (FileNotFoundException ex) {
               
            }
         }
      });
      
      start2.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            try {
               Scanner input = new Scanner(new File(inputFileName.getText()));
               PrintStream output = new PrintStream(new File(outputFileName.getText()));
               Map<String, List<String>> rules = new TreeMap<String, List<String>>();
               process(input, rules);
               printResults(output, rules);
               
            } catch (FileNotFoundException ex) {
            
            }
         }
      }); 
      
      clear.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            resultText.setText("");
         }
      });
      
      this.addWindowListener(new WindowAdapter(){
         public void windowClosing (WindowEvent e){
            System.exit(0);
         }
      });
   }
   
      
   
   
   
   public static void main(String[] args) throws Exception {
      new CssChecker().launchFrame();
   }
   
   public void findDuplicates(Scanner input) {
      boolean inside = false;
      boolean noDup = true;
      Set<String> alreadyFind = new HashSet<String>();
      resultText.append("Duplicated rules: \n");
      while (input.hasNextLine()) {
         String line = input.nextLine();
         if (inside) {
            if (line.charAt(0) == '}') {  // get out, a end line
               inside = false;
            } else {    // keep inside, a rule line
               if (!alreadyFind.contains(line)) {
                  alreadyFind.add(line);
               } else {
                  noDup = false;
                  resultText.append(line + "\n");
               }
            }
         } else {        // (!inside)
            if (line.length() > 0 && line.charAt(line.length() - 1) == '{') {  // a tag line
               inside = true;
            }
         }
      }
      if (noDup) {
         resultText.append("Congratulations! You are good! \n");
      }
      resultText.append("\n");
   }
   
   public static void process(Scanner input, Map<String, List<String>> rules) {
      boolean inside = false;
      String[] nowTags = {};
      while (input.hasNextLine()) {
         String line = input.nextLine();
         if (inside) {
            if (line.charAt(0) == '}') {  // get out, a end line
               inside = false;
            } else {    // keep inside, a rule line
               if (!rules.containsKey(line)) {
                  rules.put(line, new ArrayList<String>());
               }
               // add every tag to that rule
               for (int i = 0; i < nowTags.length; i++) {
                  rules.get(line).add(nowTags[i]);
               }
            }
         } else {        // (!inside)
            if (line.length() > 0 && line.charAt(line.length() - 1) == '{') {  // a tag line
               inside = true;
               nowTags = line.split("[,{]");    // "[, {]+"
               for (int i = 0; i < nowTags.length; i++) {
                  nowTags[i] = nowTags[i].trim();
               }
            }
         }
      }
   }
   
   public void printResults(PrintStream output, Map<String, List<String>> rules) throws FileNotFoundException {
      PrintStream cache = new PrintStream(new File("cache.txt"));
      printCache(cache, rules);
      
      Scanner cacheInput = new Scanner(new File("cache.txt"));
      Map<String, List<String>> tags = new TreeMap<String, List<String>>();
      process2(cacheInput, tags);
      
      printOutput(output, tags);
      resultText.append("Finished!");
      File f = new File("cache.txt");
      f.delete();
      
   }
   
      
   public static void printCache(PrintStream cache, Map<String, List<String>> rules) {
      for (String rule : rules.keySet()) {
         String tagLine = "";
         for (String tag : rules.get(rule)) {
            tagLine += (tag + ", ");
         }
         cache.println(tagLine.substring(0, tagLine.length() - 2) + " {"); 
         cache.println(rule);
         cache.println("}");
         cache.println();
      }
   }
   
   public static void process2(Scanner cacheInput, Map<String, List<String>> tags) {
      boolean inside = false;
      String nowTag = "";
      while (cacheInput.hasNextLine()) {
         String line = cacheInput.nextLine();
         if (inside) {
            if (line.charAt(0) == '}') {  // get out, a end line
               inside = false;
            } else {    // keep inside, a rule line
               if (!tags.containsKey(nowTag)) {
                  tags.put(nowTag, new ArrayList<String>());
               }
               // add every tag to that rule
               tags.get(nowTag).add(line);
            }
         } else {        // (!inside)
            if (line.length() > 0 && line.charAt(line.length() - 1) == '{') {  // a tag line
               inside = true;
               nowTag = line;
            }
         }
      }
   }
   
   public static void printOutput(PrintStream output, Map<String, List<String>> tags) {
      for (String tag : tags.keySet()) {
         output.println(tag);
         
         for (String rule : tags.get(tag)) {
            output.println(rule);
         }
         output.println("}");
         output.println();
      }
   }
   
      
}