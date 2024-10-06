import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
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

public class Recipes extends JPanel  {
    private Image bg; //bg image
    /** list of all icon images to be stores here */
    private Image[] icons; 
    private Image locked; //locked image
    private boolean[] killed;
    private boolean[] collected;
    private int[] choices;
    private String[] names;
    private String[] desc = new String [30];
    private int iconStartx = 50;
    private int iconStarty = 50;
    private int iconNum = 15;
    private int iconSize = 130;
    private int numCol = 4;


    public Recipes (boolean[] k, boolean[] col, int[] cho){
        killed = k;
        collected = col;
        choices = cho;
        names = new String[] {"Reeds", "Drop of Blood", "Toadstool", "Crowberry", "Foxglove", 
        "Obsidian", "Pixie Dust", "Dubious Potion", "Opalescent Eyeball", "Shiny Skull", 
        "Spirit in an Hourglass", "Bar of Gold", "Icy Scale", "Cloud in a Bottle", "Humming Geode", 
        "Heart of the Forest", "Fire in a Marble"};
        for(int i = 0; i < 15; i++){
            desc[i] = "It was always there.";
        }

        try{
            bg = ImageIO.read(new File("lib/images/recipes_bg.png"));
            for(int i = 1; i < iconNum + 1; i++){
                icons[i-1] = ImageIO.read(new File("lib/images/icons/"+i+".png"));
                icons[i-1] = icons[i-1].getScaledInstance(130, 130, Image.SCALE_DEFAULT);
            }
            Scanner file = new Scanner(new File("lib/dialogues/descriptions.txt"));
            int count = 15;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                desc[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "R: Error loading in image", "My Little Eldritch", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
        for(int i = 0; i < iconNum; i++){
            Image curr;
            if(collected[i]){
                curr = icons[i];
            } else {
                curr = locked;
            }
            g.drawImage(curr, iconStartx + iconSize*(i%numCol), iconStarty + iconSize*(i/numCol), null);
        }

       
    }

    public void run() {
        repaint();
    }

}


