package driver;

import java.awt.Robot;

import javafx.stage.Stage;

import javax.swing.JFrame;


public class WindowsSecurity implements Runnable 
{
  private Stage stage;
  private boolean running;

  public WindowsSecurity(Stage yourFrame)
  {
    this.stage = yourFrame;
    new Thread(this).start();
  }

  public void stop()
  {
     this.running = false;
  }

  public void run() {
    try {
      this.stage.setAlwaysOnTop(true);
     // this.stage.setDefaultCloseOperation(Event.);
      kill("explorer.exe"); // Kill explorer
      Robot robot = new Robot();
      int i = 0;
      while (running) {
         sleep(30L);
         focus();
         releaseKeys(robot);
         sleep(15L);
         focus();
         if (i++ % 10 == 0) {
             kill("taskmgr.exe");
         }
         focus();
         releaseKeys(robot);
      }
      Runtime.getRuntime().exec("explorer.exe"); // Restart explorer
    } catch (Exception e) {

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
    
    this.stage.requestFocus();
  }
}
