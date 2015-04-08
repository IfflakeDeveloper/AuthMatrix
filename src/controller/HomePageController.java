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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePageController implements Initializable {
	
	Stage currentStage;
	@FXML private Button RegPageRedirectBtn;
	@FXML private Button LoginPageRedirectBtn;
	@FXML private AnchorPane HomePane;

	
	public void setCurrentStage(Stage inputStage){
			currentStage = inputStage;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
			RegPageRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {

			  
			  @FXML @Override
	            public void handle(ActionEvent event) {
				   
				   try{
				   Parent root = FXMLLoader.load(getClass().getResource("/view/RegPage2.fxml"));
					Scene scene = new Scene(root);
					currentStage.setTitle("Registration Page");
					currentStage.setScene(scene);
				   }
				   
				   catch(Exception e){
					   
					   e.printStackTrace();
				   }
			   }
			   });
			
			
			LoginPageRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {

				  
				  @FXML @Override
		            public void handle(ActionEvent event) {
					   
					   try{
					   Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
						Scene scene = new Scene(root);
						currentStage.setTitle("Login Page");
						currentStage.setScene(scene);
					   }
					   
					   catch(Exception e){
						   
						   e.printStackTrace();
					   }
				   }
				   });

	}

}
