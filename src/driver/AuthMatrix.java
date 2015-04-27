package driver;
import java.nio.file.Paths;
import java.util.Map;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.application.Platform;
import controller.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;



public class AuthMatrix extends Application {

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {


		Map<String,String> parameters = getParameters().getNamed();
		String paramLock = parameters.get("lock");
		
		/*
		
		Preferences prefs = Preferences.userRoot().node("com/ab/AuthMatrix/"+"bxbbncz55");
		System.out.print(prefs.getBoolean("ShowIntersectionAssistance", true));
		*/
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
		primaryStage.initStyle(StageStyle.UNDECORATED);

		primaryStage.setFullScreenExitHint(new String(""));
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setTitle("Home Page");
		primaryStage.setFullScreen(true);

		Platform.setImplicitExit(false);
		/*
		 primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent event) {

			        event.consume();
			    }});
		 */
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				event.consume();
			}});
		
		if( (paramLock!=null) &&(paramLock.equals("true")) ) new WindowsLocker(primaryStage);
		else primaryStage.setScene(scene);

		primaryStage.show();
	}


}
