import javax.swing.JFrame;

public class Test{
   /** This is the main Jframe used for this game */
   private JFrame frame;
    
   public Test(){
       frame = new JFrame("My Little Eldritch");
   }
 
    /**
     * Initialize the JFrame to be used throughout the entirety of the game
     */
    private void setFrame(){
       frame.setSize(1366, 768);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.setResizable(true);
       frame.setLocationRelativeTo(null);
   }
    
   private void run(){
      boolean[] collected = new boolean[30];
      collected[3] = true;
      collected[4] = true;
      collected[5] = true;

      setFrame();
      Circle d = new Circle(collected);
      frame.add(d);
      d.run();
      frame.remove(d);
      System.out.println("DONE!!");
   }

   public static void main(String args[]){
      Test game = new Test();
      game.run();
   }
} 