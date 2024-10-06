import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

/**
 * <h1> Tutorial </h1>
 * This class is the Tutorial class which wil go thru the basic backstory of the game.
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
   private Image witch2;
   private Image box;
  
   
   /** This array of strings variable stores the all the texts to display */
   private String[] texts = {
      "This is the first level of Under Pressure and the content inside will help you understand the idea of academic high standards and academic stress. During this first level you will learn about where academic stress comes from, how it can affect you and how you can minimize it. To switch between screens all you need to do is click the mouse.", 
      "",
      "",
      "",
      "Academic stress is caused by two primary factors. The first factor is academic activities such as understanding material, tests and homework. This factor largely impacts students as mounting workloads cause many students to be overwhelmed and not have proper work-life balance. ", 
      "The second and larger factor affecting academic stress is parental pressure to succeed. Many students face extreme amounts of pressure from parents to succeed which causes kids to overwork themselves in order to live up to these expectations.", 
      "Academic stress over long periods of time can lead to several different psychological disorders for students. Some common disorders that can arise from academic pressure include depression and anxiety which can cause students to have issues in all aspects of their life.", 
      "Academic pressure from parents can sometimes lead to nervous disorders being developed by students due to constant high expectations. In very extreme cases, high academic stress can even lead to suicide which has recently been becoming more common for students.", 
      "The best ways to deal with academic stress are setting aside time for yourself, distracting yourself from the stress and finding ways to outwardly express the stress. Ensuring not to avoid dealing with stress is also quite important.",
      "For example, when dealing with parental pressure it�s okay to not listen to every negative comment you hear in order to maintain a healthy lifestyle.", 
      "So that's it for the lesson! Now you can move on to actually face some academic pressure."
      };
   /** This integer variable stores where in the level the user is */
   private int i;

   /**
   * The constructor of the LevelOne class where the base of the first level is constructed,
   * initializing variables using parameters
   *
   */
   public monologue() {
      try{
         bg = ImageIO.read(new File("/lib/images/hut.png"));
         witch1 = ImageIO.read(new File("/lib/images/witch1.png"));
         witch1 = ImageIO.read(new File("/lib/images/witch2.png"));
         i = 0;
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
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
      Font font = new Font("Broadway", Font.PLAIN, 25);
      g.setFont(font);
      if(i == 0){
         g.drawImage(ins, 0, 0, null);
         g.drawString(texts[i].substring(0,90), 60, 535);
         g.drawString(texts[i].substring(90,180), 50, 570);
         g.drawString(texts[i].substring(180,264), 60, 605);
         g.drawString(texts[i].substring(264), 125, 640);
      }
      else if(i == 1){
         g.drawImage(bg, 0, 0, null);
      }
      else if(i == 2){
         g.drawImage(bg, 0, 0, null);
         g.drawImage(teacher, 0, 0, null);
      }
      else if(i == 3){
         g.drawImage(bg, 0, 0, null);
         g.drawImage(teacher, 0, 0, null);
         g.drawImage(student, 0, 0, null);
      }
      else {
         g.drawImage(bg, 0, 0, null);
         g.drawImage(teacher, 0, 0, null);
         g.drawImage(student, 0, 0, null);
         g.drawImage(speech, 0, 0, null);
         if(i == 4) {
            g.drawString(texts[i].substring(0,53), 310, 525);
            g.drawString(texts[i].substring(54,97), 310, 555);
            g.drawString(texts[i].substring(98,146), 310, 585);
            g.drawString(texts[i].substring(147,190), 310, 615);
            g.drawString(texts[i].substring(191, 238), 310, 645);
            g.drawString(texts[i].substring(239), 310, 675);
         } else if(i == 5) {
            g.drawString(texts[i].substring(0,47), 310, 525);
            g.drawString(texts[i].substring(48,101), 310, 555);
            g.drawString(texts[i].substring(102,150), 310, 585);
            g.drawString(texts[i].substring(151,202), 310, 615);
            g.drawString(texts[i].substring(203), 310, 645);
         } else if(i == 6) {
            g.drawString(texts[i].substring(0,53), 310, 525);
            g.drawString(texts[i].substring(54,109), 310, 555);
            g.drawString(texts[i].substring(110,151), 310, 585);
            g.drawString(texts[i].substring(152,200), 310, 615);
            g.drawString(texts[i].substring(201, 255), 310, 645);
            g.drawString(texts[i].substring(256), 310, 675);
         } else if(i == 7) {
            g.drawString(texts[i].substring(0,49), 310, 525);
            g.drawString(texts[i].substring(50,102), 310, 555);
            g.drawString(texts[i].substring(103,156), 310, 585);
            g.drawString(texts[i].substring(157,208), 310, 615);
            g.drawString(texts[i].substring(209, 251), 310, 645);
            g.drawString(texts[i].substring(252), 310, 675);
         } else if(i == 8) {
            g.drawString(texts[i].substring(0,54), 310, 525);
            g.drawString(texts[i].substring(55,109), 310, 555);
            g.drawString(texts[i].substring(110,158), 310, 585);
            g.drawString(texts[i].substring(159,211), 310, 615);
            g.drawString(texts[i].substring(212), 310, 645);
         } else if(i == 9) {
            g.drawString(texts[i].substring(0,48), 310, 525);
            g.drawString(texts[i].substring(49,102), 310, 555);
            g.drawString(texts[i].substring(103), 310, 585);
         } else if(i == 10) {
            g.drawString(texts[i].substring(0,51), 310, 525);
            g.drawString(texts[i].substring(52), 310, 555);
         }
      }
   }
   
  /**
   * This method retrieves the user's mouse input using a graphic coordinate system
   * and increases the counter variable for each click the user does
   */
   private void getClick(){
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            i++;
         }  
      });   
   }
   
   /**
    * This method allows the user to cycle through all of the information slides in level one
    */  
   public void run() {
      getClick();
      while(i<10){
         revalidate();
         repaint();
      }
   } 
}