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

public class Pet extends JPanel {
    /** This Image variable stores the background image */
    private Image bg;
    /** This Image variable stores the instructions image */
    private Image witch1;
    private Image familiar; //the thing the witch is talking to
    private Image box; //pretty little textbox   
    /** This array of strings variable stores the all the texts to display */
    private String[] texts = new String[30];
    /** Stores who's speaking for that index. 0=familiar, 1=witch1, 2=witch2 */
    /** This integer variable stores where in the index the user is */
    private int dialogueIndex;
    /** timer for the typewriting stuff */
    private Timer timer;
    private int typeWriterIndex; //the index of the stirng we're on
    private Font font;

    /**
     * The constructor of the Dialogue class where the base of the thing is constructed,
     * initializing variables using parameters
     *
     */
    public Pet(String familiarName, String textsName) {
        dialogueIndex = 0;
        font = new Font("georgia", Font.PLAIN, 24); 
        try{
            bg = ImageIO.read(new File("lib/images/hut.png"));
            box = ImageIO.read(new File("lib/images/box.png"));
            box = box.getScaledInstance(1200, 300, Image.SCALE_DEFAULT);
            witch1 = ImageIO.read(new File("lib/images/witch1.png"));
            familiar = ImageIO.read(new File("lib/images/"+familiarName+".png"));
            dialogueIndex = 0;
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "D: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }

        try {
            Scanner file = new Scanner(new File("lib/dialogues/descriptions.txt"));
            int count = 0;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                texts[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "C: Error loading in description file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
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

            if (x + wordWidth >= startX + 800){
            y += lineHeight;
            x = startX;
            }
            g.drawString(word, x, y);
            x += wordWidth;
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
        g.setFont(font);
        String str;

        g.drawImage(bg, 0, 0, null);
        if(dialogueIndex/2 == 0){
            g.drawImage(witch1, 100, 200, null);
            str = "That doesn’t look right..";
        } else {
            g.drawImage(familiar, 800, 200, null);
            str = texts[12 + dialogueIndex/2];
        }
        g.drawImage(box, 60, 420, null);

        
        if(dialogueIndex%2 != 0){
            write(g, str, 130, 480);
        } else {
            write(g, str.substring(0, typeWriterIndex), 130, 480);

        }
    }


    //will print out the text one by one
    private void slowPrint(){
        String text = texts[dialogueIndex/2];
        if(timer != null && timer.isRunning()) {
            return;
        }
        timer = new Timer(60,new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(typeWriterIndex < text.length()) {
                //revalidate();
                repaint();
                typeWriterIndex++;
            } else {
                timer.stop();
            }
            }
        });
        timer.start();   
    }


    /**
     * This method retrieves the user's mouse input using a graphic coordinate system
     * and increases the counter variable for each click the user does
     */
    private void getClick(){
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            dialogueIndex++;
            typeWriterIndex = 0;
            }  
        });   
    }

    /**
    * This method allows the user to cycle through all of the information slides in level one
    */  
    public void run() {
        getClick();
        while((dialogueIndex/2)<4){
            revalidate();
            slowPrint();
        }
        return;
    } 
}