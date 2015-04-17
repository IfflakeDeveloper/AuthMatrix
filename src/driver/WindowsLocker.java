package driver;

import com.sun.glass.ui.Robot;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class WindowsLocker {
	Stage currentStage;
	
	public WindowsLocker(Stage stage){
		currentStage = stage;
		lock();
	}
	
	private void lock(){
		
		try{
			
		//KeyHook.blockWindowsKey();	
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		 Screen screen = Screen.getPrimary();
		 Rectangle2D bounds = screen.getVisualBounds();

		 currentStage.setX(bounds.getMinX());
		 currentStage.setY(bounds.getMinY());
		 currentStage.setWidth(bounds.getWidth());
		 currentStage.setHeight(bounds.getHeight());
		// currentStage.initStyle(StageStyle.UNDECORATED);

		 currentStage.setFullScreenExitHint(new String(""));
		 currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		 currentStage.setTitle("Home Page");
		 currentStage.setFullScreen(true);
		 
		 Platform.setImplicitExit(false);
		 currentStage.setAlwaysOnTop(true);
		 disableKeyboard();
		 
		 currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {
			
			        event.consume();
			    }});
		 
		 currentStage.setOnHiding(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {
			
			        event.consume();
			    }});

		 
		currentStage.setScene(scene);
		
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	private void disableKeyboard(){
		
		WindowsSecurity ws =new WindowsSecurity(currentStage);
		AltTabStopper.create(currentStage);
		//obot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
	    
		
		
	}
	
}
