package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsPageController implements Initializable{

	Stage currentStage;
	@FXML private Button HomePageRedirectBtn;


	public void setCurrentStage(Stage inputStage){
		currentStage = inputStage;

	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {


		HomePageRedirectBtn.setOnAction((new EventHandler<ActionEvent>() {


			@Override
			public void handle(ActionEvent event) {

				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));

					Parent root = loader.load();
					Scene scene = new Scene(root);
					HomePageController homeController = (HomePageController) loader.getController();
					homeController.setCurrentStage(currentStage);
					currentStage.setTitle("Home Page");
					currentStage.setScene(scene);

				}

				catch(Exception ex) {ex.printStackTrace();}
			}
		}));


	}

}
