import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.GraphicsEnvironment;

// main class
  
public class MyLittleEldritch{
   /** This is the main Jframe used for this game */
   private JFrame frame;////
   private boolean[] killed;
   private boolean[] collected;
   private int[] choices;
   
   public MyLittleEldritch(){
      frame = new JFrame("My Little Eldritch");
   }

   /**
    * Initialize the JFrame to be used throughout the entirety of the game
    */
   private void setFrame(){
      frame.setSize(1366, 768);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
   }
   
   /**
    * This helper method will run the splashscreens
    */
   private void runSplash(){
      ImageFades title = new ImageFades("TitleScreen.png");
      frame.add(title);
      title.run();
      frame.remove(title);
   }
      
   //runs the little tutorial dialogue
   private void runTutorial(){
      Dialogue d = new Dialogue("earth.png", "earth.txt");
      frame.add(d);
      d.run();
      frame.remove(d);
   }

   private int runHut(){
      Hut hut = new Hut(killed, collected);
      frame.add(hut);
      int go =  hut.run();
      frame.remove(hut);
      return go;
   }

   private int runCircle(){
      Circle circle = new Circle(collected);
      frame.add(circle);
      int go =  circle.run();
      frame.remove(circle);
      return go;
   }

   private int runRecipes(){
      Recipes recipes = new Recipes(killed, collected, choices);
      frame.add(recipes);
      int go = recipes.run();
      frame.remove(recipes);
      return go;
   }

   private int runDialogue(int number){
      String creature;
      String text;

      switch (number){
         case 0:
            creature = "tutorial";
            text = "tutorial";
            break;
         case 1:
            creature = "earth";
            text = "earth";
            break;            
      }

      Dialogue dialogue = new Dialogue(creature, text);
      frame.add(dialogue);
      dialogue.run();
      frame.remove(dialogue);
      return 0;
   }

   
   /**
    * The run method that implements all the helper methods
    * and acts as the main game runner
    */
   public void run(){  
      setFrame();
      runSplash();

      boolean killed[] = new boolean[6]; //which monsters they killed
      boolean collected[] = new boolean[20]; //ingredients collected
      int choices[] = new int[10]; //choices they made!!!
      int eldritch = -1;

      int go = 0; //0=hut, 1=recipes, 2=circle, 3=dialogue, 4 = quit

      runTutorial();
      go = runHut();

      while (go != 4) {
         switch (go){
            case 0:
               go = runHut();
               break;
            case 1:
               go = runRecipes();
               break;
            case 2:
               eldritch = runCircle();
               break;
            case 3:
               go = runDialogue(eldritch);
               break;
         }
      }
      
      runSplash();
      frame.setVisible(false);
      frame.dispose();
   }
   
   /**
    * This is the main method that instantiates the game and runs it. 
    * @param args This is the command line argument
    */
   public static void main(String args[]){
      MyLittleEldritch game = new MyLittleEldritch();
      game.run();
      
   }
}
