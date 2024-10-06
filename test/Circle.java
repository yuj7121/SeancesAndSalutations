import javax.swing.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Scanner;
import java.awt.Image;
import java.awt.Graphics;

/**
 * This class is the Circle class that will show the user the menu to 
 * select ingredients for the summoning
 * 
 * <p>
 * 
 *
 * 
 * @author Yujin Bae
 * @version 10.04.24
 * @since 2024-10-04
 * </p>
 */

public class Circle extends JPanel implements MouseListener, MouseMotionListener{
    private Image bg;
    private Image button;
    private Image longButton;
    private Image box;
    private boolean[] collected;
    private Image[] icons;
    private Image[] bigIcons;
    private int element1;
    private int element2;
    private int result;
    private int currResult;
    private Image locked;
    private boolean go; //whether the user clicked the go button or not
    private boolean exit; //exit this screen
    private boolean showBox;
    private String[] names; //naems of ingredients
    private String[] desc; //naems of ingredients

    private int iconNum; //number of icons/ingredients we have
    private int iconSize; //pixel size
    private int iconStartx; //x corrdinate where the chart starts
    private int iconStarty; //y coordinate
    private int numCol;
    private Font broadway;
    private Font georgia;
    private Font georgiaB;


    public Circle(boolean[] c){
        broadway = new Font("Broadway", Font.PLAIN, 25);
        georgia = new Font("Georgia", Font.PLAIN, 20);
        georgiaB = new Font("Georgia", Font.BOLD, 25);

        collected = c;
        icons = new Image[30];
        bigIcons = new Image[30];
        names = new String[] {"Reeds", "Drop of Blood", "Toadstool", "Crowberry", "Foxglove", 
            "Obsidian", "Pixie Dust", "Dubious Potion", "Opalescent Eyeball", "Shiny Skull", 
            "Spirit in an Hourglass", "Bar of Gold", "Icy Scale", "Cloud in a Bottle", "Humming Geode", 
            "Heart of the Forest", "Fire in a Marble"};
        desc = new String[15]; //item descriptions
        go = false;
        exit = false;
        element1 = -1;
        element2 = -1;
        result = -1;
        currResult = -1;
        showBox = false;

        iconNum = 17;
        iconSize = 150;
        iconStartx = 700;
        iconStarty = 100;
        numCol = 4;

        addMouseMotionListener(this);
        addMouseListener(this);

        try{
            bg = ImageIO.read(new File("../lib/images/circle_bg.png"));
            bg = bg.getScaledInstance(1366, 768, Image.SCALE_DEFAULT);
            box = ImageIO.read(new File("../lib/images/box.png"));
            box = box.getScaledInstance(1000, 300, Image.SCALE_DEFAULT);
            button = ImageIO.read(new File("../lib/images/button.png"));
            longButton = button.getScaledInstance(400, 135, Image.SCALE_DEFAULT);
            button = button.getScaledInstance(170, 60, Image.SCALE_DEFAULT);
            locked = ImageIO.read(new File("../lib/images/icons/locked.png"));
            locked = locked.getScaledInstance(130, 130, Image.SCALE_DEFAULT);
            for(int i = 1; i < iconNum + 1; i++){
                bigIcons[i-1] = ImageIO.read(new File("../lib/images/icons/"+i+".png"));
                icons[i-1] = bigIcons[i-1].getScaledInstance(130, 130, Image.SCALE_DEFAULT);
            }
            Scanner file = new Scanner(new File("../lib/dialogues/descriptions.txt"));
            int count = 0;
            while (file.hasNext()) {
                String nextLine = file.nextLine();
                desc[count] = nextLine;
                count++;
            }
            file.close();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error loading in file", "My Little Eldritch", JOptionPane.WARNING_MESSAGE);
        }
        
    }

    public void mouseMoved(MouseEvent e){

    }

    public void mouseDragged(MouseEvent e){

    }

    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        int which;

        if(showBox){
            if(result > 0){
                collected[result] = true;
            }
            currResult = -1;
            element1 = -1;
            element2 = -1;
            
            showBox = false;
            return;
        }

        if(x>50 && x<220 && y>600 && y<660){
            go = true;
        } else if(x>250 && x<320 && y>600 && y<660){
            element1 = -1;
            element2 = -1;
        } else if(x>450 && x<520 && y>600 && y<660){
            element1 = -1;
            element2 = -1;
            exit = true;
        } else {
            if(x<iconStartx || y < iconStarty){
                return;
            }
            which = ((x - iconStartx) / iconSize) + (((y - iconStarty) / iconSize) * numCol) + 1;
            if(which >= 0 && which <= iconNum)
            if(element1 == -1){
                element1 = which;
            } else {
                element2 = which;
            }
        }        
    }

    private int findResult(){
        int small = element1;
        int big = element2;
        int result = -1;

        if(element1 > element2){
            small = element2;
            big = element1;
        }

        switch (small){
            case 1:
                if(big == 7){
                    result = 18;
                } else if(big == 2){
                    result = 16;
                }
                break;
            case 2:
                if(big == 8){
                    result = 19;
                }
                break;
            case 3:
                if(big == 9){
                    result = 22;
                } else if (big==7){
                    result = 14;
                }
                break;
            case 4:
                if(big==6){
                    result = 20;
                }
                break;
            case 5:
                if(big==6){
                    result = 15;
                } else if(big==10){
                    result = 21;
                }
                break;
            case 7:
                if(big==14){
                    result = 25;
                } else if(big==16){
                    result = 27;
                }
                break;
            case 8:
                if(big==10){
                    result = 13;
                } else if(big==17){
                    result = 23;
                }
                break;
            case 9:
                if(big==13){
                    result = 24;
                } else if(big==11){
                    result = 17;
                }
                break;
            case 10:
                if(big==15){
                    result = 26;
                }
                break;
            case 11:
                if(big==12){
                    result = 28;
                }
                break;     
        }
        return result;
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}

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
  
           if (x + wordWidth >= startX + 550){
              y += lineHeight;
              x = startX;
           }
           g.drawString(word, x, y);
           x += wordWidth;
        }
     }
      

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color (40, 15, 35)); 
        g.setFont(broadway);

        g.drawImage(bg, 0, 0, null);
        g.drawImage(button, 50, 600, null);
        g.drawString("Go", 115, 638);
        g.drawImage(button,250, 600, null);
        g.drawString("Reset", 295, 638);
        g.drawImage(button, 450, 600, null);
        g.drawString("Exit", 505, 638);

        if(element1 != -1){
            g.drawImage(icons[element1 - 1], 100, 150, null);
        }
        if(element2 != -1){
            g.drawImage(icons[element2 - 1], 400, 150, null);
        }

        for(int i = 0; i < iconNum; i++){
            Image curr;
            if(collected[i]){
                curr = icons[i];
            } else {
                curr = locked;
            }
            g.drawImage(curr, iconStartx + iconSize*(i%numCol), iconStarty + iconSize*(i/numCol), null);
        }

        if(showBox){
            if(currResult != -1 && collected[currResult]){
                g.drawImage(icons[currResult], 250, 410, null);
            } else if (currResult != -1){
                g.drawImage(icons[currResult], 250, 410, null);
                g.drawImage(box, 200, 100, null);
                g.drawString(names[currResult], 260, 230);
                g.setFont(georgiaB);
                g.drawString("Congratulations! You found a new recipe", 260, 160);
                g.setFont(georgia);
                write(g, desc[currResult-13], 260, 270);
                g.drawImage(bigIcons[currResult], 800, 40, null);
                g.setFont(broadway);
            } else {
                g.drawImage(longButton, 120, 280, null);
                g.drawString("Bad recipe!", 230, 330);
                g.drawString("Try a different combo", 170, 370);
            }
        }
    }


    public int run(){
        while(!exit && result < 23){
            go = false;
            while(!go && !exit){
                revalidate();
                repaint();
            }
            result = findResult();
            currResult = result;
            showBox = true;
            revalidate();
            repaint();
            System.out.println(result);
        }
        if(exit){
            System.out.println("EXIT!!");
            return 0;
        }
        System.out.println("EXIT!!wnumber"+result);
        return result;
    }

}
