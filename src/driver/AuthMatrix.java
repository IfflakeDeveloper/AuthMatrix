package driver;
import javafx.application.Application;
import controller.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class AuthMatrix extends Application {
	
	public static void main(String[] args) {
        launch(args);
	}

	
	 @Override
	    public void start(Stage primaryStage) throws Exception {
	      
			
		 FXMLLoader currentLoader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));
		 Pane root = (Pane)currentLoader.load();
		 HomePageController currentController = (HomePageController) currentLoader.getController();
		 currentController.setCurrentStage(primaryStage);
		 
		 Scene scene = new Scene(root);
		 
		 Screen screen = Screen.getPrimary();
		 Rectangle2D bounds = screen.getVisualBounds();

		 primaryStage.setX(bounds.getMinX());
		 primaryStage.setY(bounds.getMinY());
		 primaryStage.setWidth(bounds.getWidth());
		 primaryStage.setHeight(bounds.getHeight());
		 
		 primaryStage.setTitle("Home Page");
		// primaryStage.setFullScreen(true);
		 primaryStage.setScene(scene);
		 primaryStage.show();
	    }
}
