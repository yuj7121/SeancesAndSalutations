import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import java.awt.event.*;

/**
 * <h1> Dialogue </h1>
 * This class is the Tutorial class which will display tutorial dialogue.
 * 
 * <p>
 *  
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */

public class Tutorial extends JPanel {
   /** This Image variable stores the background image */
   private Image bg;
   /** This Image variable stores the instructions image */
   private Image witch1;
   private Image familiar; //the thing the witch is talking to
   private Image box; //pretty little textbox   
   /** This array of strings variable stores the all the texts to display */
   private String[] texts = new String[30];
   /** Stores who's speaking for that index. 0=familiar, 1=witch1, 2=witch2 */
   private int[] who = new int[30]; //who's speaking??
   private int textsSize = 0; //the size of the texts array
   /** This integer variable stores where in the index the user is */
   private int dialogueIndex;
   /** timer for the typewriting stuff */
   private Timer timer;
   private int typeWriterIndex; //the index of the stirng we're on
   private Font font;

   /**
   * The constructor of the Dialogue class where the base of the thing is constructed,
   * initializing variables using parameters
   *
   */
   public Tutorial(String familiarName, String textsName) {
      
      font = new Font("georgia", Font.PLAIN, 24); 
      try{
         if(familiarName == "cthulu"){
            familiar = ImageIO.read(new File("lib/images/cthulu.png"));
         }
         bg = ImageIO.read(new File("lib/images/hut.png"));
         bg = bg.getScaledInstance(1366, 768, Image.SCALE_DEFAULT);
         box = ImageIO.read(new File("lib/images/box.png"));
         box = box.getScaledInstance(1200, 300, Image.SCALE_DEFAULT);
         witch1 = ImageIO.read(new File("lib/images/witch1.png"));
         //familiar = ImageIO.read(new File("lib/images/"+familiarName+".png"));
         dialogueIndex = 0;
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "T: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
      }

      try {
         Scanner file = new Scanner(new File("lib/dialogues/"+textsName+".txt"));
         while (file.hasNext())
         {
            String nextLine = file.nextLine();
            char w = nextLine.charAt(0);
            if(w == '0'){
                who[textsSize] = 0;
            } else if(w == '1'){
                who[textsSize] = 1;
            } else if(w == '2'){
                who[textsSize] = 2;
            }
            //nextLine = nextLine.replaceAll("\\\\n", System.getProperty("line.separator"));
            texts[textsSize] = nextLine.substring(3);            
            textsSize++;
         }
         file.close();
      } catch (Exception e){
        JOptionPane.showMessageDialog(null, "T: Error loading in file: "+textsName, "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
      }
   }
   
   void write(Graphics g, String text, int startX, int startY){
      int lineHeight = g.getFontMetrics().getHeight();
      int x = startX;
      int y = startY;
      String[]words = text.split(" ");

      for(String word : words){
         int wordWidth = g.getFontMetrics().stringWidth(word + " ");
         //if at end or \n, move to next line        
         if(word.equals("[newline]")){
            y += lineHeight + 20;
            x = startX;
            continue;
         }

         if (x + wordWidth >= startX + 1060){
            y += lineHeight;
            x = startX;
         }
         g.drawString(word, x, y);
         x += wordWidth;
      }
   }

   /**
    * This method will be called when repaint() is called 
    * It draws the main menu depending on the input
    * @param g This is the graphics where the main menu will be drawn
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setFont(font);

      g.drawImage(bg, 0, 0, null);
      if(who[dialogueIndex/2] == 0){
         g.drawImage(familiar, 100, -40, null);
      } else if (who[dialogueIndex/2] == 1){
         g.drawImage(witch1, 500, -40, null);
      }
      g.drawImage(box, 60, 420, null);

      if(dialogueIndex%2 != 0){
         write(g, texts[dialogueIndex/2], 130, 480);
      } else {
         write(g, texts[dialogueIndex/2].substring(0, typeWriterIndex), 130, 480);
      }
   }

   
   //will print out the text one by one
   private void slowPrint(){
      String text = texts[dialogueIndex/2];
      if(timer != null && timer.isRunning()) {
         return;
      }
      timer = new Timer(20, new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(typeWriterIndex < text.length() && dialogueIndex%2 == 0) {
               //revalidate();
               typeWriterIndex++;
               repaint();
            } else {
               timer.stop();
            }
         }
      });
      timer.start();   
   }
   

  /**
   * This method retrieves the user's mouse input using a graphic coordinate system
   * and increases the counter variable for each click the user does
   */
   private void getClick(){
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            dialogueIndex++;
            typeWriterIndex = 0;
         }
      });   
   }
   
   /**
    * This method allows the user to cycle through all of the information slides in level one
    */  
   public void run() {
      getClick();
      //FIX BUG HERE idalogueIndex should'nt have +1 but this is the only way
      while(((dialogueIndex)/2) < textsSize){
         if(typeWriterIndex > texts[dialogueIndex/2].length() && typeWriterIndex > 0){
            typeWriterIndex = 0;
            dialogueIndex++;
         }
         revalidate();
         repaint();
         slowPrint();
      }
   
      return;
   } 
}