import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
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
   private int go; //the choice user made
   private Image circleRedLine;
   private Image recipesRedLine;
   private int hover;
    
   public Hut(){
        go = -1;
        hover = -1;
        addMouseMotionListener(this);
        addMouseListener(this);
        try{
        bg = ImageIO.read(new File("../lib/images/hut.png"));
        circleRedLine = ImageIO.read(new File("../lib/images/circleRedLine.png"));
        recipesRedLine = ImageIO.read(new File("../lib/images/recipesRedLine.png"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading in image", "My Little Eldritch", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x>200 && x<500 && y>70 && y<770){
            go = 0;
        }
        if(x>980 && x<1280 && y>70 && y<770){
            go = 1;
        }
    }

    public void mouseMoved(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        if(x>200 && x<500 && y>70 && y<770){
        hover = 0;
        repaint();
        } else if(x>980 && x<1280 && y>70 && y<770){
            hover = 1;
        repaint();
        } else {
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

        g.drawImage(bg, 0, 0, null);
        switch (hover){
        case 0:
            g.drawImage(circleRedLine, 200, 70, null);
            break;
        case 1:
        g.drawImage(recipesRedLine, 980, 70, null);
        break;
        }
   }

    public int run(){
        while(go == -1){
            revalidate();
            repaint();
        }
        return go;
    }
}
