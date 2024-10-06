import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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

public class Recipes extends JPanel implements MouseListener{
    private Image bg; //bg image
    /** list of all icon images to be stores here */
    private Image[] icons; 
    private Image locked; //locked image
    private boolean[] killed;
    private boolean[] collected;
    private int choices;
    private String[] names;
    private String[] formula;
    private String[] unknown_formula;
    private int iconStartx = 50;
    private int iconStarty = 25;
    private int iconNum = 28;
    private int iconSizex = 220;
    private int iconSizey = 135;
    private int numCol = 6;
    private boolean exit;
    private Font georgia;
    private Font georgiaI;


    public Recipes (boolean[] k, boolean[] col, int cho){
        killed = k;
        collected = col;
        choices = cho;
        
        exit = false;
        icons = new Image [30];
        formula = new String [30];
        unknown_formula = new String [30];
        names = new String [30];
        georgia = new Font("Georgia", Font.BOLD, 16);
        georgiaI = new Font("Georgia", Font.ITALIC, 14);


        addMouseListener(this);

        for(int i = 0; i < 12; i++){
            formula[i] = "It was always there.";
            unknown_formula[i] = "It was always there.";
        }

        //load in images
        try{
            bg = ImageIO.read(new File("lib/images/recipes_bg.png"));
            locked = ImageIO.read(new File("lib/images/icons/locked.png"));
            locked = locked.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
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

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color (240, 215, 235)); 

        g.drawImage(bg, 0, 0, null);
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

    public void mouseClicked(MouseEvent e){
        exit = true;
        return;
    }

    public void mousePressed(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}


    public void run() {
        while(!exit){
            revalidate();
            repaint();
        }
    }

}


