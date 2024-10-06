import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * This class is the ImageFades class that will take in the name of an 
 * image file and make it fade in and out gradually. It will be used 
 * for the logo introduction and the title splash screen
 * 
 * <p>
 * Version 1 - 3 hours, Yujin, May 16th, 2022
 * Made it to take in an image and made that image fade in and out. 
 *
 * Version 2 - 1 hour, Yujin, June 9th, 2022
 * Made this class take in a String to display as well for the gameover screens. 
 *
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */

public class ImageFades extends JPanel implements ActionListener {
   /** This int variable stores the current opacity of the image */
   private int opacity;
   /** This boolean variable determines if the image is fading in or out */
   private boolean in;
   /** This Image variable stores the image that is fading in and out*/
   private Image image;
   /** This String variable stores the text we want to display */
   private String text;
   
   /**
    * The contructor of ImageFades class that takes on the name of 
    * the image and initializes variable with default values
    * @param img This is the name o athe image file we want to fade  
    */
   public ImageFades (String img) {
      opacity = 2;
      in = true;
      text = "";
      try{
         image = ImageIO.read(new File("lib/images/"+img));
         image = image.getScaledInstance(1366, 768, Image.SCALE_DEFAULT);
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "I: Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
         e.printStackTrace();
      }
   }

   /**
    * This contructor of ImageFades class takes in the name of the image
    * and the string we want to display and initializes varaible with default values
    * @param img This is the name of the image file we want to fade  
    * @param t This is the text that we want to display
    */
   public ImageFades (String img, String t) {
      opacity = 2;
      in = true;
      text = t;
      try{
         image = ImageIO.read(new File("lib/images/"+img));
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
   }

   /**
    * This method will be called when repaint() is called. 
    * It draws the image with a specific opacity.
    * The opacity is raised or lowered every time it is called.
    * @param g This is the graphics to be drawn
    */
   @Override
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;  //we need g to be 2d to do stuffs
      if (in) {   //if the image should be fading in
         opacity+=2;
      } else {
         opacity-=3;
      }
      if (opacity == 100) {   /*if the image finished completely fading in*/
         in = false;
      }
      g2.setColor(new Color(110, 50, 140));   /*background colour is blue*/
      g2.fillRect(0, 0, getWidth(), getHeight());  /*draws the background*/
      /*sets transparency according to opacity*/
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity*0.01f));
      g2.drawImage(image, 0, 0, null);    /*draws the image*/
      g2.setColor(Color.BLACK);   /*text color is black*/
      /* draws the texts */
      String temp = text;
      int i = temp.indexOf(" ") + 35;
      for (int j = 0; i >= 35; j++) {
         i = (temp.substring(35)).indexOf(" ") + 35;
         g2.drawString(temp.substring(1, i), 60, 460 + 52 * j);
         temp = temp.substring(i);
         if (temp.length() < 35) {
            g2.drawString(temp.substring(1), 60, 460 + 52 * (j+1));
            break;
         }
      }
   }

   /**
    * This method will run the animation that fades the image
    */
   public void run() {
      Font font = new Font("Broadway", Font.PLAIN, 48);
      setFont(font);
      Timer time;
      if(text.equals("")){
         time = new Timer (3, this);
      } else {
         time = new Timer (5, this);
      }
      while(opacity > 1){ //stops when the image completely faded out
         revalidate();
         time.start();
      }
      time.stop();
   }
   
   /**
    * This method implements the ActionListener Class.
    * @param e instance of the ActionEvent class
    */
   public void actionPerformed (ActionEvent e) {
      repaint();
   }
}