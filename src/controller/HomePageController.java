package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import driver.WindowsLocker;
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


		try{
			PreparedStatement stmt = null;
			Connection conn = DBController.init();

			stmt = conn.prepareStatement("SELECT * FROM USERS;");
			ResultSet result = stmt.executeQuery();
			if(!result.next()) 
				LoginPageRedirectBtn.setVisible(false);
			else RegPageRedirectBtn.setText("Edit Profile");
		}

		catch(Exception ex){ ex.printStackTrace(); }




		RegPageRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {


			@FXML @Override
			public void handle(ActionEvent event) {

				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegPage.fxml")); 
					Parent root = loader.load();
					RegPageController regController = (RegPageController) loader.getController();
					regController.setCurrentStage(currentStage);
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

					new WindowsLocker(currentStage);  
				}

				catch(Exception e){

					e.printStackTrace();
				}
			}
		});

	}

}
