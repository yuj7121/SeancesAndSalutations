import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;


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


public class Settings extends JPanel implements MouseListener, MouseMotionListener{
    private Image bg;
    private Image button;
    private Image redButton;
    private int go; //the choice user made
    private Image circleRedLine;
    private Image recipesRedLine;
    private int hover;
    private boolean[] killed;
    private boolean[] collected;
    private int choices;
    private Font broadway;
    private Color purple;

    public Settings(){}

    public void init(boolean[] ki, boolean[] co, int cho){
        killed = ki;
        collected = co;
        choices = cho;
        go = -1;
        hover = -1;

        loadSave();

        broadway = new Font("Broadway", Font.PLAIN, 30);
        purple = new Color (40, 15, 35); 

        addMouseMotionListener(this);
        addMouseListener(this);
        try{
            bg = ImageIO.read(new File("lib/images/recipes_bg.png"));
            bg = bg.getScaledInstance(1366, 768, Image.SCALE_DEFAULT);
            button = ImageIO.read(new File("lib/images/button.png"));
            button = button.getScaledInstance(350, 110, Image.SCALE_DEFAULT);
            redButton = ImageIO.read(new File("lib/images/redButton.png"));
            redButton = redButton.getScaledInstance(350, 110, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "S: Error loading in image", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void writeSave(){
        try {
            File save = new File("lib/save/saveFile.txt");
            if(!save.exists()){
                save.createNewFile();
            }
            FileWriter writer = new FileWriter("lib/save/saveFile.txt");
            for(int count = 0; count < 37; count++) {
                String nextLine;
                if(count == 0){
                    nextLine = ""+choices;
                } else if (count < 7) {
                    if(killed[count - 1] = true){
                        nextLine = "1";
                    } else {
                        nextLine = "0";
                    }
                } else {
                    if(collected[count-7] = true){
                        nextLine = "1";
                    } else {
                        nextLine = "0";                    }
                }
                writer.write(nextLine);
                count++;
            }
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "S: Error writing to save file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadSave(){
        try {
            File save = new File("lib/save/saveFile.txt");
            if(!save.exists()){
                save.createNewFile();
                writeSave();
            }
            Scanner file = new Scanner(new File("lib/save/saveFile.txt"));
            int count = 0;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                if(count == 0){
                    choices = Integer.parseInt(nextLine);
                    System.out.println("choices:"+choices);
                } else if (count < 7) {
                    if(nextLine.charAt(0) == '1'){
                        killed[count - 1] = true;
                        System.out.println("killed:true");
                    } else {
                        killed[count - 1] = false;
                        System.out.println("killed:false");
                    }
                } else {
                    if(nextLine.charAt(0) == '1'){
                        collected[count-7] = true;
                        System.out.println("collected:true");
                    } else {
                        collected[count-7] = false;
                        System.out.println("collected:false");
                    }
                }
                count++;
            }
            file.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "S: Error loading in save file", "Seances & Salutations", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if(x>100 && x<400 && y>300 && y<400){
            go = 1;
            System.out.println("111");
        } else if(x>500 && x<800 && y>300 && y<400){
            go = 2;
            System.out.println("22");
        } else if(x>900 && x < 1200 && y>300 && y<400){
            go = 3;
            System.out.println("13333311");
        } 
    }

    public void mouseMoved(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        if(x>100 && x<400 && y>300 && y<400){
            hover = 0;
            repaint();
        } else if(x>500 && x<800 && y>300 && y<400){
            hover = 1;
            repaint();
        } else if(x>900 && x < 1200 && y>300 && y<400){
            hover = 2;
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
        g.setColor(purple);
        g.setFont(broadway);

        g.drawImage(bg, 0, 0, null);
        
        g.drawImage(button, 100, 300, null);
        g.drawImage(button, 500, 300, null);
        g.drawImage(button, 900, 300, null);

        switch (hover){
        case 0:
            g.drawImage(redButton, 100, 300, null);
            break;
        case 1:
            g.drawImage(redButton, 500, 300, null);
            break;
        case 2:
            g.drawImage(redButton, 900, 300, null);
            break;
        }

        g.drawString("Save Game", 180, 365);
        g.drawString("Load Last Save", 550, 365);
        g.drawString("Exit Game", 990, 365);

    }

    public int run(){
        while(go != 3){
            if(go == 1) {
                //sdfsdf
                loadSave();
            } else if (go == 2) {
                //dfsfhskld
                writeSave();
            }
            revalidate();
            repaint();
        }
        return 0;
    }
}
