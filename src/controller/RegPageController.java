package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.annotation.Resources;
import javax.management.Notification;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegPageController implements Initializable {
	
	private int UserID;
	private String UserName;
	private String UserEmail;
	private String PassPhrase;
	private int BlueRank;
	private int RedRank;
	private int YellowRank;
	private int GreenRank;

	@FXML  private Button RegSubmitBtn;
	@FXML  private TextField UserNameBox;
	@FXML  private TextField UserEmailBox;
	@FXML  private TextField BlueRankBox;
	@FXML  private TextField RedRankBox;
	@FXML  private TextField YellowRankBox;
	@FXML  private TextField GreenRankBox;
	@FXML  private TextField PassPhraseBox;
	

    @Override
    public void initialize(URL location, ResourceBundle resources){
		
		
		try{
			
			   RegSubmitBtn.setOnAction(new EventHandler<ActionEvent>() {

			  
			   @Override
	            public void handle(ActionEvent event) {
	            	try{

	            		UserName = UserNameBox.getText();
	            		UserEmail = UserEmailBox.getText();
	            		PassPhrase = PassPhraseBox.getText();

	            		BlueRank = Integer.parseInt(BlueRankBox.getText());
	            		RedRank = Integer.parseInt(RedRankBox.getText());
	            		YellowRank = Integer.parseInt(YellowRankBox.getText());
	            		GreenRank = Integer.parseInt(GreenRankBox.getText());
	            		
	            		if((UserName==null)||(UserEmail==null)||(PassPhrase==null)||(BlueRankBox.getText()==null)||(RedRankBox.getText()==null)||(YellowRankBox.getText()==null)||(GreenRankBox.getText()==null))
	            		{
	            			/*NotificationPane invalidWarning = new NotificationPane();
	            			invalidWarning.setText("Incomplete input!");
	            			invalidWarning.setContent(new Text("One or more fields are not entered."));
	            			invalidWarning.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
	            			invalidWarning.setShowFromTop(true);
	            			invalidWarning.show("Woo");
	            			*/

	            			 Notifications.create()
	            			              .title("Incomplete input!")
	            			              .text("One or more fields are not entered.")
	            			              .darkStyle()
	            			              .hideCloseButton()
	            			              .showWarning();
	            			System.out.print("if mein hoon");
	            			return;
	            			 
	            		}
	            
	            	/*	
	            		
	            		if( (BlueRank<=4&&BlueRank>0)||(RedRank<=4&&RedRank>0)||(GreenRank<=4&&GreenRank>0)||(YellowRank<=4&&YellowRank>0) )
	            			Notifications.create()
  			              .title("Incorrect input!")
  			              .text("Color Ranking must lie between 1 and 4.")
  			              .darkStyle()
  			              .hideCloseButton()
  			              .showWarning();
	            	*/	
	            		PreparedStatement stmt = null;
		            	Connection conn = DBController.init();
		            	
	            		//UserID=(int) (stmt = conn.prepareStatement("SELECT COUNT(*) FROM users;")).executeQuery();
	            	UserID = (int) (Math.random()*140)+89;
	            	stmt = conn.prepareStatement("INSERT INTO users VALUES (?,?,?,?,'null')");
	                stmt.setInt(1, UserID );
	                stmt.setString(2, UserName);
	                stmt.setString(3, UserEmail);
	                stmt.setString(4, PassPhrase);
	                               	                
	                stmt.executeUpdate();
	            	
	                stmt = conn.prepareStatement("INSERT INTO color_rank VALUES (?,?,?,?,?)");
	                stmt.setInt(1, UserID);
	                stmt.setInt(2, RedRank);
	                stmt.setInt(3, BlueRank);
	                stmt.setInt(4, GreenRank);
	                stmt.setInt(5, YellowRank);

	                stmt.executeUpdate();
	            	}
	            	
	            	catch(Exception e){
	        			
	        			e.printStackTrace();
	        		}
	            }
	        });
		   
		   
		   
		   
		   
		}
		
		catch(Exception e){
			
			e.printStackTrace();
		}
		   
		   
		
	}
	
}


