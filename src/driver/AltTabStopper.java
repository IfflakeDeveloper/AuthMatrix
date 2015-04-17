package driver;





import java.awt.Robot;
import java.awt.event.KeyEvent;

import javafx.stage.Stage;

public class AltTabStopper implements Runnable
{
     private boolean working = true;
     private Stage stage;

     public AltTabStopper(Stage frame)
     {
          this.stage = frame;
     }

     public void stop()
     {
          working = false;
     }

     public static AltTabStopper create(Stage s)
     {
         AltTabStopper stopper = new AltTabStopper(s);
         new Thread(stopper, "Alt-Tab Stopper").start();
         return stopper;
     }

     public void run()
     {
         try
         {
        	 Robot robot = new Robot();
     	    
             while (working)
             {
            	 robot.keyRelease(KeyEvent.VK_CONTROL);
            	 robot.keyRelease(KeyEvent.VK_WINDOWS);
                  robot.keyRelease(KeyEvent.VK_ALT);
                  robot.keyRelease(KeyEvent.VK_TAB);
                 
                  
                  try {  Thread.sleep(5); } catch(Exception e) {}
             }
         } catch (Exception e) { e.printStackTrace(); System.exit(-1); }
     }
     
     void focus(){
    	 stage.requestFocus();
     }
}

