package driver;

import java.awt.Robot;
import javafx.stage.Stage;



public class WindowsSecurity implements Runnable 
{
  private Stage stage;
  private boolean running;
  Thread thread;

  public WindowsSecurity(Stage yourFrame)
  {
    this.stage = yourFrame;
    this.running=true;
    thread = new Thread(this);
    thread.start();
  }

  public void stop()
  {
     this.running = false;
  }

  public void run() {
    try {
      //stage.setAlwaysOnTop(true);
     // stage.setDefaultCloseOperation(Event.);
      kill("explorer.exe"); // Kill explorer
      Robot robot = new Robot();
      int i = 0;
      while (running) {
         sleep(30L);
        // focus();
         releaseKeys(robot);
         sleep(15L);
       //  focus();
         if (i++ % 10 == 0) {
             kill("Taskmgr.exe");
         }
         //focus();
         releaseKeys(robot);
      }
      Runtime.getRuntime().exec("cmd /c explorer.exe"); // Restart explorer
      
    } catch (Exception e) {
    	e.printStackTrace();
    }
  }

  private void releaseKeys(Robot robot) {
    robot.keyRelease(17);
    robot.keyRelease(18);
    robot.keyRelease(127);
    robot.keyRelease(524);
    robot.keyRelease(9);
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {

    }
  }

  private void kill(String string) {
    try {
      Runtime.getRuntime().exec("taskkill /F /IM " + string).waitFor();
    } catch (Exception e) {
    }
  }

  private void focus() {
    
    stage.requestFocus();
  }
}
