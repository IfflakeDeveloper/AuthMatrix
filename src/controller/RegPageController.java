package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.management.Notification;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import virtualKeyboard.VirtualKeyboard;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegPageController implements Initializable {

	private Stage currentStage;
	private int UserID;
	private String LastName;
	private String FirstName;
	private String UserName;
	private String DOB;
	private String UserPhone;
	private String UserEmail;
	private String PassPhrase;
	private int BlueRank;
	private int RedRank;
	private int YellowRank;
	private int GreenRank;
	private Image Avatar;
	private File AvatarFile;

	@FXML  private Button RegSubmitBtn;
	@FXML  private Button AvatarChooserBtn;
	@FXML  private ImageView AvatarBox;
	@FXML private AnchorPane vKeyboardArea;

	@FXML  private TextField FirstNameBox;
	@FXML  private TextField LastNameBox;
	@FXML  private DatePicker DobBox;
	@FXML  private TextField UserNameBox;
	@FXML  private TextField UserPhoneBox;
	@FXML  private TextField UserEmailBox;
	@FXML  private TextField BlueRankBox;
	@FXML  private TextField RedRankBox;
	@FXML  private TextField YellowRankBox;
	@FXML  private TextField GreenRankBox;
	@FXML  private TextField PassPhraseBox;


	public void setCurrentStage(Stage inputStage){
		currentStage = inputStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources){

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vKeyboardArea.getChildren().addAll(vkb.view());


		try{



			AvatarChooserBtn.setOnAction((new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {

					FileChooser fc = new FileChooser();
					fc.setTitle("Choose your Avatar image....");
					fc.setInitialDirectory(
							new File(System.getProperty("user.home"))
							);

					fc.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter("All Images", "*.*"),
							new FileChooser.ExtensionFilter("JPG", "*.jpg"),
							new FileChooser.ExtensionFilter("PNG", "*.png")
							);

					AvatarFile = fc.showOpenDialog(currentStage);
					Avatar = new Image(AvatarFile.toURI().toString());
					AvatarBox.setImage(Avatar);


				}
			}));










			RegSubmitBtn.setOnAction(new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {
					try{

						FirstName = FirstNameBox.getText();
						LastName = LastNameBox.getText();
						UserName = UserNameBox.getText();
						UserPhone= UserPhoneBox.getText();
						DOB = DobBox.getValue().toString();
						UserEmail = UserEmailBox.getText();
						PassPhrase = PassPhraseBox.getText();

						BlueRank = Integer.parseInt(BlueRankBox.getText());
						RedRank = Integer.parseInt(RedRankBox.getText());
						YellowRank = Integer.parseInt(YellowRankBox.getText());
						GreenRank = Integer.parseInt(GreenRankBox.getText());

						if((UserName==null)||(UserEmail==null)||(PassPhrase==null)||(BlueRankBox.getText()==null)||(RedRankBox.getText()==null)||(YellowRankBox.getText()==null)||(GreenRankBox.getText()==null))
						{
							Notifications.create()
							.title("Incomplete input!")
							.text("One or more fields are not entered.")
							.darkStyle()
							.hideCloseButton()
							.showWarning();

							return;

						}



						if(!( (BlueRank<=4&&BlueRank>0)||(RedRank<=4&&RedRank>0)||(GreenRank<=4&&GreenRank>0)||(YellowRank<=4&&YellowRank>0) ))
						{
							Notifications.create()
							.title("Incorrect input!")
							.text("Color Ranking must lie between 1 and 4.")
							.darkStyle()
							.hideCloseButton()
							.showWarning();

							return;
						}
						PreparedStatement stmt = null;
						Connection conn = DBController.init();


						String AvatarFormat = URLConnection.guessContentTypeFromName(AvatarFile.getName()).split("/")[1];
						
						BufferedImage bImage = SwingFXUtils.fromFXImage(Avatar, null);
						ByteArrayOutputStream s = new ByteArrayOutputStream();
						ImageIO.write(bImage, AvatarFormat, s);
						byte[] res  = s.toByteArray();
						s.flush();
						s.close();		



						stmt = conn.prepareStatement("INSERT INTO USERS(AVATAR,FIRST_NAME,LAST_NAME,USERNAME,DOB,PHONE,EMAIL_ID,PASSPHRASE) VALUES (?,?,?,?,?,?,?,?)");


						stmt.setBytes(1, res);
						stmt.setString(2, FirstName);
						stmt.setString(3, LastName);
						stmt.setString(4, UserName);
						stmt.setString(5, DOB);
						stmt.setString(6, UserPhone);
						stmt.setString(7, UserEmail);
						stmt.setString(8, PassPhrase);

						stmt.executeUpdate();

						stmt = conn.prepareStatement("INSERT INTO COLOR_RANKING(USER_ID,RED_RANK,BLUE_RANK,GREEN_RANK,YELLOW_RANK) VALUES ((SELECT USER_ID FROM USERS u WHERE u.USERNAME =?),?,?,?,?)");
						stmt.setString(1, UserName);
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


