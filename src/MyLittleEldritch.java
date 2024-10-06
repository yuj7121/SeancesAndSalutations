import javax.swing.*;
/*
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.GraphicsEnvironment;
*/

// main class
  
public class MyLittleEldritch{///
   /** This is the main Jframe used for this game */
   private JFrame frame;////
   private boolean[] killed;
   private boolean[] collected;
   private int choices;
   
   public MyLittleEldritch(){
      frame = new JFrame("My Little Eldritch");
      killed = new boolean[30];
      collected = new boolean[30];
      choices = 0;
      for(int i = 0; i < 12; i++){
         collected[i] = true;
      }
      
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
      Tutorial d = new Tutorial("tutorial", "tutorial");
      frame.add(d);
      d.run();
      frame.remove(d);
   }

   private int runHut(){
      Hut hut = new Hut();
      hut.init(killed, collected);
      frame.add(hut);
      int go = hut.run();
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
      recipes.run();
      frame.remove(recipes);
      return 0;
   }

   private int runDialogue(int number){
      String creature;
      int points;
      switch (number){
         case 23:
            creature = "fire";
            break;
         case 24:
            creature = "water";
            break;
         case 25:
            creature = "wind";
            break;
         case 26:
            creature = "earth";
            break;
         case 27:
            creature = "wood";
            break;
         case 28:
            creature = "metal";
            break;
         default:
            creature = " ";
            text = " ";
      }
      Dialogue dialogue = new Dialogue(creature);
      frame.add(dialogue);
      points = dialogue.run();
      frame.remove(dialogue);
      return points;
   }


   
   /**
    * The run method that implements all the helper methods
    * and acts as the main game runner
    */
   public void run(){  
      int go = 0; //0=hut, 1=circle, 2=recipes, 3=quit, else dialogue

      setFrame();

      //runSplash();
      //runTutorial();
      
      go = runHut();

      while (go != 3) {
         switch (go){
            case 0: //hut
               go = runHut();
               break;
            case 1: //circle
               go = runCircle();
               break;
            case 2: //recipes
               go = runRecipes();
               break;
            default: //dialogue
               runDialogue(go);
               go = 0;
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
