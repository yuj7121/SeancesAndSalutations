import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;



/**
 * <h1> Hut </h1>
 * This class is the Hut class of the witch's hut.
 * 
 * <p>
 *  
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */


public class Hut extends JPanel implements MouseListener, MouseMotionListener{
   private Image bg;
   private Image button;
   private Image redButton;
   private int go; //the choice user made
   private Image circleRedLine;
   private Image recipesRedLine;
   private int hover;
   private boolean[] killed;
   private boolean[] collected;
   private Font broadway;
   private Color purple;
   
   public Hut(){}

   public void init(boolean[] ki, boolean[] co){
      killed = ki;
      collected = co;
      go = -1;
      hover = -1;
      broadway = new Font("Broadway", Font.PLAIN, 25);
      purple = new Color (40, 15, 35); 

  

      addMouseMotionListener(this);
      addMouseListener(this);
      try{
         bg = ImageIO.read(new File("lib/images/hut.png"));
         bg = bg.getScaledInstance(1366, 768, Image.SCALE_DEFAULT);
         button = ImageIO.read(new File("lib/images/button.png"));
         button = button.getScaledInstance(220, 80, Image.SCALE_DEFAULT);
         redButton = ImageIO.read(new File("lib/images/redButton.png"));
         redButton = redButton.getScaledInstance(220, 80, Image.SCALE_DEFAULT);
         circleRedLine = ImageIO.read(new File("lib/images/shelf1.png"));
         recipesRedLine = ImageIO.read(new File("lib/images/shelf2.png"));
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "H: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
      }
   }

   public void mousePressed(MouseEvent e){
      int x = e.getX();
      int y = e.getY();
      if(x>0 && x<470 && y>0 && y<630){
         go = 1;
      }
      if(x>960 && x<1366 && y>0 && y<580){
         go = 2;
      }
      /*
      if(x>970 && x<1170 && y>610 && y<690){
         go = 3;
      } 
         */
   }

   public void mouseMoved(MouseEvent e){
      int x = e.getX();
      int y = e.getY();

      if(x>0 && x<470 && y>0 && y<630){
         hover = 0;
         repaint();
      } else if(x>960 && x<1366 && y>0 && y<580){
         hover = 1;
         repaint();
      } 
      /*/
      else if(x>970 && x < 1170 && y>610 && y<690){
         hover = 2;
         repaint();
      } 
         */
        else {
         hover = -1;
         repaint();
      }
   }
    
   public void mouseDragged(MouseEvent e){}
   public void mouseClicked(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseReleased(MouseEvent e){}

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      
      g.setColor(purple);
      g.setFont(broadway);

      g.drawImage(bg, 0, 0, null);
      //g.drawImage(button, 970, 610, null);

      switch (hover){
      case 0:
         g.drawImage(circleRedLine, 0, 0, null);
         break;
      case 1:
         g.drawImage(recipesRedLine, 866, 0, null);
         break;
      case 2:
         //g.drawImage(redButton, 970, 610, null);
         break;
      }
      //g.drawString("Settings", 1022, 657);
   }

   public int run(){
      while(go == -1){
         revalidate();
         repaint();
      }
      return go;
    }
}
