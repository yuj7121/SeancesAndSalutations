import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import java.awt.event.*;

/**
 * <h1> Dialogue </h1>
 * This class is the Dialogue class which will display dialogues.
 * 
 * <p>
 *  
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */

public class Dialogue extends JPanel implements MouseListener, MouseMotionListener{
   /** This Image variable stores the background image */
   private Image bg;
   /** This Image variable stores the instructions image */
   private Image witch1;
   private Image familiar; //the thing the witch is talking to
   private Image box; //pretty little textbox   
   /** This array of strings variable stores the all the texts to display */
   private String[] dialogue1 = new String[10];
   private int[] who1 = new int[10]; //who's speaking?? 0=monster, 1=witch
   private int d1Size = 0; //the size of the texts array
   private String[] dialogue2 = new String[10];
   private int[] who2 = new int[10]; //who's speaking??
   private int d2Size = 0; //the size of the texts array
   private String[] choices = new String[6];
   private int[] choicePoints = new int[6];
   private int choicesSize = 0;
   private int[][] consWho = new int[6][10];
   private int[] consIndSize = new int[6];
   private String[][] consequence = new String[6][10];
   private int consSize = -1;

   /** This integer variable stores where in the index the user is */
   private int dialogueIndex;
   /** timer for the typewriting stuff */
   private Timer timer;
   private int typeWriterIndex; //the index of the stirng we're on
   private String currStr;
   private int who; //the current who
   private Font font;
   private int point;

   private int hover; //where the mouse is hovering
   private int clicked; //which option the user clicked on
   private boolean choosing;
   private int where; //0=d1, 1=choices1, 2=d2, 3=choices2


   /**
   * The constructor of the Dialogue class where the base of the thing is constructed,
   * initializing variables using parameters
   *
   */
   public Dialogue(String name) {
      point = 0;
      hover = -1;
      clicked = -1;
      where = 0;
      dialogueIndex = 0;

      addMouseMotionListener(this);
      addMouseListener(this);

      font = new Font("georgia", Font.PLAIN, 24); 
      try{
         bg = ImageIO.read(new File("lib/images/hut.png"));
         box = ImageIO.read(new File("lib/images/box.png"));
         box = box.getScaledInstance(1200, 300, Image.SCALE_DEFAULT);
         witch1 = ImageIO.read(new File("lib/images/witch1.png"));
         familiar = ImageIO.read(new File("lib/images/"+name+".png"));
         dialogueIndex = 0;
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "D: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
      }

      try {
         Scanner file = new Scanner(new File("lib/dialogues/"+name+".txt"));
         int reading = 0; //0=d1, 1=choices1, 2=d2, 3=choice2
         while (file.hasNext())
         {
            String nextLine = file.nextLine();
            nextLine.replaceAll("\u2019", "'");
            nextLine.replaceAll("\u2018", "'");
            char first = nextLine.charAt(0);
            if(first == 'C') {
               if (reading == 0){
                  reading = 1;
               } else {
                  reading = 3;
               }
            } else if (first == 'D'){
               reading = 2;
            } else if (reading == 0){
               if(first == '0'){
                  who1[d1Size] = 0;
               } else {
                  who1[d1Size] = 1;
               }
               dialogue1[d1Size] = nextLine.substring(3);            
               d1Size++;
            } else if(reading == 1 || reading == 3){
               if(first == '!'){
                  if(nextLine.charAt(1) == '+'){
                     choicePoints[choicesSize] = nextLine.charAt(2) - '0';
                  } else {
                     choicePoints[choicesSize] = -1 * (nextLine.charAt(2) - '0');
                  }
                  choices[choicesSize] = nextLine.substring(3);
                  choicesSize++;
                  consSize++;
               } else {
                  if(first == '0'){
                     consWho[consSize][consIndSize[consSize]] = 0;
                  } else {
                     consWho[consSize][consIndSize[consSize]] = 1;
                  }
                  consequence[consSize][consIndSize[consSize]] = nextLine.substring(3);            
                  consIndSize[consSize]++;
               }
            } else if (reading == 2){
               if(first == '0'){
                  who2[d2Size] = 0;
               } else {
                  who2[d2Size] = 1;
               }
               dialogue2[d2Size] = nextLine.substring(3);            
               d2Size++;
            } 
         }
         file.close();
      } catch (Exception e){
         JOptionPane.showMessageDialog(null, "D: Error loading in file: "+name, "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
      }
   }

   public void mousePressed(MouseEvent e){
      int x = e.getX();
      int y = e.getY();
      typeWriterIndex = 0;
      if(!choosing){
         dialogueIndex++;
         if(where == 0){
            if(d1Size == (dialogueIndex)/2) {
               where = 1;
               choosing = true;
               dialogueIndex = 0;
            } else {
               currStr = dialogue1[(dialogueIndex)/2];
               who = who1[(dialogueIndex)/2];
            }
         } else if(where == 1){
            if((dialogueIndex)/2 == consIndSize[clicked - 1]){
               dialogueIndex = 0;
               where = 2;
               System.out.println("where=2 now");
               choosing = false;
            } else {
               currStr = consequence[clicked - 1][(dialogueIndex)/2];
               who = consWho[clicked - 1][(dialogueIndex)/2];
            }
         } else if (where == 2){
            if(d2Size == (dialogueIndex)/2) {
               dialogueIndex = 0;
               where = 3;
               System.out.println("where=3 now");
               choosing = true;
            } else {
               currStr = dialogue2[(dialogueIndex)/2];
               who = who2[(dialogueIndex)/2];
            }
         } else if (where == 3) {
           if((dialogueIndex)/2 == consIndSize[clicked + 3 - 1]){
               System.out.println("where=4 now");
               dialogueIndex = 0;
               where = 4;
               choosing = true;
            } else {
               currStr = consequence[clicked + 3 - 1][(dialogueIndex)/2];
               who = consWho[clicked + 3 - 1][(dialogueIndex)/2];
            }
         }
         return;
      }
      
      if(x>60 && x<660 && y>420 && y<570){
         clicked = 1;
      } else if(x>660 && x<1260 && y>420 && y<570){
         clicked = 2;
      } else if(x>60 && x<660 && y>570 && y<720){
         clicked = 3;
      } else {
         clicked = -1;
         System.out.println("return");
         choosing = true;
         return;
      }
      choosing = false;

      if(where == 1){
         currStr = consequence[clicked - 1][0];
         point += choicePoints[clicked - 1];

      } else if (where == 3){
         System.out.println("where=3, clicked = "+clicked);
         currStr = consequence[clicked - 1 + 3][0];
         System.out.println("str:"+currStr);
         point += choicePoints[clicked - 1 + 3];
      }
      dialogueIndex = 0;
      //System.out.println(point);
   }

   public void mouseMoved(MouseEvent e){
      int x = e.getX();
      int y = e.getY();
      if(!choosing){
         return;
      }

      if(x>60 && x<660 && y>420 && y<570){
         hover = 1;
      } else if(x>660 && x<1260 && y>420 && y<570){
         hover = 2;
      } else if(x>60 && x<660 && y>570 && y<720){
         hover = 3;
      } else{
         hover = -1;
      }      
   }

   public void mouseDragged(MouseEvent e){}
   public void mouseClicked(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseReleased(MouseEvent e){}

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
      if(who == 0){
         g.drawImage(familiar, 500, -30, null);
      } else if (who == 1){
         g.drawImage(witch1, 100, -40, null);
      } 
      g.drawImage(box, 60, 420, null);

      if(choosing){
         int temp = 0;
         if(where == 3){
            temp = 3;
         }

         g.drawString("> " + choices[0+temp], 160, 500);
         g.drawString("> " + choices[1+temp], 760, 500);
         g.drawString("> " + choices[2+temp], 160, 650);
         return;
      }

      if(dialogueIndex%2 != 0){
         write(g, currStr, 130, 480);
      } else {
         write(g, currStr.substring(0, typeWriterIndex), 130, 480);
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
         if (x + wordWidth >= startX + 1080){
            y += lineHeight;
            x = startX;
         }
         g.drawString(word, x, y);
         x += wordWidth;
      }
   }

   //will print out the text one by one
   private void slowPrint(){
      if(timer != null && timer.isRunning()) {
         return;
      }
      timer = new Timer(20,new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if(typeWriterIndex < currStr.length() && dialogueIndex%2 == 0) {
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
   

   /*
   private void printDialogue(int[] who, String[] lines, int size){
      for(int i = 0; i < size; i++){
         currStr = lines[i];
         System.out.println(lines[i]);
      }
   }

   private void runChoices(int start){
      for(int i = start; i < start + 3; i++){
         choosing = true;
         System.out.println("choice "+i+" is: "+choices[i]);
         System.out.println("choice "+i+" points: "+choicePoints[i]);
         System.out.println("choice "+i+" results in: ");
         printDialogue(consWho[i], consequence[i], consIndSize[i]);  
         System.out.println("<<<<<<<<<< that was choice "+i);     
      }
   }

   private void runScripts(){
      System.out.println("First Dialogue____________________");
      printDialogue(who1, dialogue1, d1Size);
      System.out.println("First QNA____________________");
      runChoices(0);
      System.out.println("Second Dialogue____________________");
      printDialogue(who2, dialogue2, d2Size);
      System.out.println("Second QNA____________________");
      runChoices(3);
      
   }
   */
   


   
   /**
    * This method allows the user to cycle through all of the information slides in level one
    */  
   public int run() {
      //runScripts();
      currStr = dialogue1[0];
      while(where != 4){
         revalidate();
         if(typeWriterIndex > 0 && typeWriterIndex > currStr.length()){
            typeWriterIndex = 0;
            dialogueIndex++;
         }
         repaint();
         slowPrint();
         //runScripts();
      }
      return point;
   }
}