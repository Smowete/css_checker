
import java.util.*;
import java.io.*;


public class CssChecker {
   
   public static void main(String[] args) throws Exception {
      //new CssWindow().launchFrame();
   
      Scanner console = new Scanner(System.in);

      System.out.println("(1) Check duplicated rules, (2) Automatically generate file");
      System.out.print("1 or 2 ? --> ");
      int type = console.nextInt();
      
      System.out.print("What is the input file name? --> ");
      Scanner input = new Scanner(new File(console.next()));
      
      
      if (type == 1) {
         findDuplicates(input);
      } else if (type == 2) {
         System.out.print("What is the output file name? --> ");
         PrintStream output = new PrintStream(new File(console.next()));
         
         Map<String, List<String>> rules = new TreeMap<String, List<String>>();
         
         process(input, rules);
         printResults(output, rules);
      } else {
         throw new IllegalArgumentException("Not valid number" + type);
      }
       
   }
   
   public static void findDuplicates(Scanner input) {
      boolean inside = false;
      boolean noDup = true;
      Set<String> alreadyFind = new HashSet<String>();
      System.out.println();
      System.out.println("Duplicated rules:");
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
                  System.out.println(line);
               }
            }
         } else {        // (!inside)
            if (line.length() > 0 && line.charAt(line.length() - 1) == '{') {  // a tag line
               inside = true;
            }
         }
      }
      if (noDup) {
         System.out.println("Congratulations! You are good!");
      }
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
   
   public static void printResults(PrintStream output, Map<String, List<String>> rules) throws FileNotFoundException {
      PrintStream cache = new PrintStream(new File("cache.txt"));
      printCache(cache, rules);
      
      Scanner cacheInput = new Scanner(new File("cache.txt"));
      Map<String, List<String>> tags = new TreeMap<String, List<String>>();
      process2(cacheInput, tags);
      
      printOutput(output, tags);
      System.out.println("Finished!");
      
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