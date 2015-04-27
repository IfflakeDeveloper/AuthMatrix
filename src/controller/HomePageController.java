package controller;

import java.net.URL;
import java.nio.file.Paths;
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
	static boolean toEdit;
	@FXML private Button RegPageRedirectBtn;
	@FXML private Button LoginPageRedirectBtn;
	@FXML private Button ChangeProfileRedirectBtn;
	@FXML private Button SettingsPageRedirectBtn;
	@FXML private AnchorPane HomePane;


	public void setCurrentStage(Stage inputStage){
		currentStage = inputStage;

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {


		try{
			toEdit = false;
			PreparedStatement stmt = null;
			Connection conn = DBController.init();

			stmt = conn.prepareStatement("SELECT * FROM USERS;");
			ResultSet result = stmt.executeQuery();
			if(!result.next()){ 
				LoginPageRedirectBtn.setVisible(false);
				ChangeProfileRedirectBtn.setVisible(false);
				SettingsPageRedirectBtn.setVisible(false);
				String currDir = Paths.get("").toAbsolutePath().toString();
				Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c SETX AuthMatrix \""+currDir+"\"");

			}
			else {  toEdit = true; }

		}

		catch(Exception ex){ ex.printStackTrace(); }




		RegPageRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {

			@FXML @Override
			public void handle(ActionEvent event) {

				try{
					toEdit = false;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegPage.fxml")); 
					Parent root = loader.load();
					RegPageController regController = (RegPageController) loader.getController();
					regController.setCurrentStage(currentStage);
					regController.setToEdit(toEdit);
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



		ChangeProfileRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {


			@FXML @Override
			public void handle(ActionEvent event) {

				try{

					toEdit = true;
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegPage.fxml")); 
					Parent root = loader.load();
					RegPageController regController = (RegPageController) loader.getController();
					regController.setCurrentStage(currentStage);
					regController.setToEdit(toEdit);
					Scene scene = new Scene(root);
					currentStage.setTitle("Registration Page");
					currentStage.setScene(scene);
				}

				catch(Exception e){

					e.printStackTrace();
				}
			}
		});

		SettingsPageRedirectBtn.setOnAction(new EventHandler<ActionEvent>() {


			@FXML @Override
			public void handle(ActionEvent event) {

				try{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SettingsPage.fxml")); 
					Parent root = loader.load();
					SettingsPageController settings = (SettingsPageController) loader.getController();
					settings.setCurrentStage(currentStage);
					Scene scene = new Scene(root);
					currentStage.setTitle("Settings Page");
					currentStage.setScene(scene);

				}

				catch(Exception e){

					e.printStackTrace();
				}
			}
		});

	}

}
