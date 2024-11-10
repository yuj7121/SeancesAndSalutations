import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;

/**
 * This class is the recipes that will show th euser the recipe book
 * and game instructions if needed, as well as teh save button
 * 
 * <p>
 * 
 * 
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */

public class Recipes extends JPanel implements MouseListener, MouseMotionListener{
    private Image bg; //bg image
    private int go; //0=continue, 1=main menu, 2=settings
    private boolean hover;
    /** list of all icon images to be stores here */
    private Image[] icons; 
    private Image locked; //locked image
    private Image button; //button image
    private Image redButton; //the red button when hovered
    private boolean[] collected;
    private String[] names;
    private String[] formula;
    private String[] unknown_formula;
    private int iconStartx = 50;
    private int iconStarty = 25;
    private int iconNum = 28;
    private int iconSizex = 220;
    private int iconSizey = 135;
    private int numCol = 6;
    private Font georgia;
    private Font georgiaI;
    private Font broadway;
    private Color purple;


    public Recipes (boolean[] col){
        hover = false;
        go = 0;
        collected = col;
        icons = new Image [30];
        formula = new String [30];
        unknown_formula = new String [30];
        names = new String [30];
        georgia = new Font("Georgia", Font.BOLD, 16);
        georgiaI = new Font("Georgia", Font.ITALIC, 14);
        broadway = new Font("Broadway", Font.PLAIN, 40);
        purple = new Color (40, 15, 35); 

        addMouseListener(this);

        for(int i = 0; i < 12; i++){
            formula[i] = "You have it in your shelf.";
            unknown_formula[i] = "You have it in your shelf.";
        }

        //load in images
        try{
            bg = ImageIO.read(new File("lib/images/recipes_bg.png"));
            locked = ImageIO.read(new File("lib/images/icons/locked.png"));
            locked = locked.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            button = ImageIO.read(new File("lib/images/button.png"));
            button = button.getScaledInstance(260, 80, Image.SCALE_DEFAULT);
            redButton = ImageIO.read(new File("lib/images/redButton.png"));
            redButton = redButton.getScaledInstance(260, 80, Image.SCALE_DEFAULT);

            for(int i = 0; i < iconNum; i++){
                int temp = i + 1;
                icons[i] = ImageIO.read(new File("lib/images/icons/"+temp+".png"));
                icons[i] = icons[i].getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "R: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
        try {
            Scanner file = new Scanner(new File("lib/dialogues/formula.txt"));
            int count = 12;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                formula[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "R: Error loading in formula file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
        try {
            Scanner file = new Scanner(new File("lib/dialogues/unknown_formula.txt"));
            int count = 12;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                unknown_formula[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "R: Error loading in unknown formula file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
        //load in item names
        try {
            Scanner file = new Scanner(new File("lib/dialogues/itemNames.txt"));
            int count = 0;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                names[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "C: Error loading in item name file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x>960 && x<1220 && y>600 && y<680) { //if button pressed
            go = 2;
        } else {
            go = 1;
        }
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x>0 && y>0){
            hover = true;
            repaint();
        }
        if(x>960 && x<1220 && y>600 && y<680) { //if button hovered
            hover = true;
            repaint();
        } else {
            hover = false;
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

        if(hover){
            g.drawImage(redButton, 960, 600, null);
        } else {
            g.drawImage(button, 960, 600, null);
        }
        g.setColor(purple);
        g.setFont(broadway);
        g.drawString("Settings", 1000, 653);

        g.setColor(new Color (240, 215, 235)); 
        for(int i = 0; i < iconNum; i++){
            Image curr;
            String str;
            if(collected[i]){
                curr = icons[i];
                str = formula[i];
            } else {
                curr = locked;
                str = unknown_formula[i];
            }
            g.drawImage(curr, iconStartx + iconSizex*(i%numCol), iconStarty + iconSizey*(i/numCol), null);
            g.setFont(georgia);
            g.drawString(names[i], iconStartx + iconSizex*(i%numCol), 75 + iconStarty + iconSizey*(i/numCol));
            g.setFont(georgiaI);
            int here = str.indexOf('+');
            if(here < 0){
                g.drawString(str, iconStartx + iconSizex*(i%numCol), 95 + iconStarty + iconSizey*(i/numCol));
            } else {
                g.drawString("    "+str.substring(0, here), iconStartx + iconSizex*(i%numCol), 95 + iconStarty + iconSizey*(i/numCol));
                g.drawString("+ "+str.substring(here + 2), iconStartx + iconSizex*(i%numCol), 115 + iconStarty + iconSizey*(i/numCol));    
            }
        }
    }

    public int run() {
        while(go == 0){
            revalidate();
            repaint();
        }
        return go;
    }

}


