package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.management.Notification;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationMessage;

import com.sun.jna.platform.unix.X11.GC;

import virtualKeyboard.VirtualKeyboard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

	private String currentLoggedUsername;
	private int currentLoggedUserID;

	@FXML  private Button RegSubmitBtn;
	@FXML  private Button AvatarChooserBtn;
	@FXML  private Button HomePageRedirectBtn;
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
	@FXML private ImageView redImage;
	@FXML private ImageView blueImage;
	@FXML private ImageView greenImage;
	@FXML private ImageView yellowImage;

	private boolean TO_EDIT;


	public void setCurrentStage(Stage inputStage){
		currentStage = inputStage;
	}

	public void setToEdit(boolean toEdit){
		TO_EDIT = toEdit;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources){
		TO_EDIT= HomePageController.toEdit;


		if(TO_EDIT){ 

			try{
				Connection conn = DBController.init();
				PreparedStatement stmt = conn.prepareStatement("SELECT USER_ID, USERNAME FROM USERS_LOG ORDER BY TIME DESC;");
				ResultSet rs = stmt.executeQuery();
				currentLoggedUsername = rs.getString("USERNAME");
				currentLoggedUserID = rs.getInt("USER_ID");
				showUserData(currentLoggedUsername);
				conn.close(); conn=null; 

			}
			catch(SQLException SqlEx){ SqlEx.printStackTrace(); }


		}

		else System.out.println("ya ali");

		VirtualKeyboard vkb = new VirtualKeyboard();
		vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
		vKeyboardArea.getChildren().addAll(vkb.view());


		Tooltip usernameTooltip = new Tooltip(">Must contain minimum 5 characters.\n >Capital letters A-Z, small letters a-z and digits from 0-9 are allowed.\n >No Special characters are allowed. \n  >Example – Adam091");
		Tooltip.install(UserNameBox,usernameTooltip);
		UserNameBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					usernameTooltip.show(UserNameBox,UserNameBox.getScene().getWindow().getX() + UserNameBox.getLayoutX() + UserNameBox.getWidth() + 10, UserNameBox.getScene().getWindow().getY() + UserNameBox.getLayoutY() + UserNameBox.getHeight());
				} else {
					usernameTooltip.hide();
				}
			}
		});


		Tooltip passphraseTooltip = new Tooltip(">Passphrase must contain even number of characters.\n >Consecutive charactes cannot be same. \n >Only capital letters A-Z and digits from 0-9 are allowed.\n >Example – COAST951");
		Tooltip.install(PassPhraseBox,passphraseTooltip );
		PassPhraseBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					passphraseTooltip.show(PassPhraseBox,PassPhraseBox.getScene().getWindow().getX() + PassPhraseBox.getLayoutX() + PassPhraseBox.getWidth() + 10, PassPhraseBox.getScene().getWindow().getY() + PassPhraseBox.getLayoutY() + PassPhraseBox.getHeight());
				} else {
					passphraseTooltip.hide();
				}
			}
		});


		try{



			Tooltip.install(redImage, new Tooltip("RED"));
			Tooltip.install(blueImage, new Tooltip("BLUE"));
			Tooltip.install(greenImage, new Tooltip("GREEN"));
			Tooltip.install(yellowImage, new Tooltip("YELLOW"));

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






			RegSubmitBtn.setDefaultButton(true);

			RegSubmitBtn.setOnAction(new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {
					try{

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
						}
						catch(Exception exc) {
							exc.printStackTrace();

							Notifications.create()
							.title("Incomplete input!")
							.text("One or more fields are not entered.")
							.darkStyle()
							.hideCloseButton()
							.showWarning();

							return;

						}


						if((FirstName==null)||(LastName==null)||(UserName==null)||(UserEmail==null)||(PassPhrase==null)||(BlueRankBox.getText()==null)||(RedRankBox.getText()==null)||(YellowRankBox.getText()==null)||(GreenRankBox.getText()==null))
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


						int usernameLen = UserName.length();
						if(usernameLen<5) { notifyInputError(UserNameBox); return;}

						usernamecheck:
							for(int i=0;i<usernameLen;++i){
								int c = UserName.charAt(i);
								if((c>=(int)'a')&&(c<=(int)'z')) {continue usernamecheck;} 
								if((c>=(int)'A')&&(c<=(int)'Z')) {continue usernamecheck;} 
								if((c>=(int)'0')&&(c<=(int)'9')) {continue usernamecheck;}
								//System.out.println((char)(c)+" at "+i); 
								notifyInputError(UserNameBox); 
								return;

							}

						int	ppLen = PassPhrase.length();
						if(ppLen<8) { notifyInputError(PassPhraseBox);	System.out.println("LESS THAN 8"); return;}
						if(ppLen%2!=0) {notifyInputError(PassPhraseBox);System.out.println("EVEN"); return;}


						for(int i=1;i<ppLen;i=i+2){

							if(PassPhrase.charAt(i-1)==PassPhrase.charAt(i)){
								notifyInputError(PassPhraseBox);

								System.out.println(PassPhrase.charAt(i-1)+" at "+i);return;
							}

						}


						ppcheck:
							for(int i=0;i<ppLen;++i){
								int c = PassPhrase.charAt(i);

								if((c>=(int)'A')&&(c<=(int)'Z')){continue ppcheck;}
								if((c>=(int)'0')&&(c<=(int)'9')){continue ppcheck;} 


								System.out.println(c+" at "+i);
								notifyInputError(PassPhraseBox); return; 
							}





						/* Validation completed */

						PreparedStatement stmt = null;
						Connection conn = DBController.init();



						if(!TO_EDIT) {
							stmt = conn.prepareStatement("INSERT INTO USERS(AVATAR,FIRST_NAME,LAST_NAME,USERNAME,DOB,PHONE,EMAIL_ID,PASSPHRASE) VALUES (?,?,?,?,?,?,?,?)");

						}

						else{
							stmt = conn.prepareStatement("UPDATE USERS SET AVATAR=?,FIRST_NAME=?,LAST_NAME=?,USERNAME=?,DOB=?,PHONE=?,EMAIL_ID=?,PASSPHRASE=? WHERE USER_ID="+currentLoggedUsername+";");
						}

						String AvatarFormat;
						if(AvatarFile!=null)  AvatarFormat = URLConnection.guessContentTypeFromName(AvatarFile.getName()).split("/")[1];
						else AvatarFormat = null;
						if(AvatarFormat!=null){

							BufferedImage bImage = SwingFXUtils.fromFXImage(Avatar, null);
							ByteArrayOutputStream s = new ByteArrayOutputStream();
							ImageIO.write(bImage, AvatarFormat, s);		
							byte[] res  = s.toByteArray();
							s.flush();
							s.close();
							stmt.setBytes(1, res);
						}

						else{
							if(TO_EDIT){
								byte[] res =conn.createStatement().executeQuery("SELECT AVATAR FROM USERS WHERE USER_ID="+currentLoggedUserID+";").getBytes("AVATAR"); //.executeQuery().getBytes("AVATAR");
								stmt.setBytes(1, res);
							}
							else{
								stmt.setBytes(1, new byte[]{});
							}
						}

						stmt.setString(2, FirstName);
						stmt.setString(3, LastName);
						stmt.setString(4, UserName);
						stmt.setString(5, DOB);
						stmt.setString(6, UserPhone);
						stmt.setString(7, UserEmail);
						stmt.setString(8, PassPhrase);

						stmt.executeUpdate();


						if(!TO_EDIT) {

							stmt = conn.prepareStatement("INSERT INTO COLOR_RANKING(USER_ID,RED_RANK,BLUE_RANK,GREEN_RANK,YELLOW_RANK) VALUES ((SELECT USER_ID FROM USERS u WHERE u.USERNAME =?),?,?,?,?)");

							stmt.setString(1, UserName);
							stmt.setInt(2, RedRank);
							stmt.setInt(3, BlueRank);
							stmt.setInt(4, GreenRank);
							stmt.setInt(5, YellowRank);
						}
						else{
							stmt = conn.prepareStatement("UPDATE COLOR_RANKING set RED_RANK=?,BLUE_RANK=?,GREEN_RANK=?,YELLOW_RANK=? WHERE USER_ID="+currentLoggedUserID+";");
							stmt.setInt(1, RedRank);
							stmt.setInt(2, BlueRank);
							stmt.setInt(3, GreenRank);
							stmt.setInt(4, YellowRank);
						}


						stmt.executeUpdate();

						Notifications.create()
						.title("Registration Successful!")
						.text("The user has been registered successfully.")
						.darkStyle()
						.hideCloseButton()
						.showWarning();


						Preferences prefs = Preferences.userRoot().node("com/ab/AuthMatrix/"+UserName);
						prefs.putInt("LockTimeInMins", 10);
						prefs.putBoolean("ShowColorLabel", true);
						prefs.putBoolean("vKeyboard", true);
						//prefs.put("HotKey", "Ctrl-Shift-L");
						prefs.putBoolean("ShowNotifications", false);
						prefs.putBoolean("ShowIntersectionAssistance", false);

						System.out.println("REGISTERED");
						
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomePage.fxml"));

						Parent root = loader.load();
						Scene scene = new Scene(root);
						HomePageController homeController = (HomePageController) loader.getController();
						homeController.setCurrentStage(currentStage);
						currentStage.setTitle("Home Page");
						currentStage.setScene(scene);

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


	private void showUserData(String username){

		try{
			Connection dbc = DBController.init();
			PreparedStatement query= dbc.prepareStatement("SELECT FIRST_NAME, LAST_NAME, DOB, PHONE, EMAIL_ID, PASSPHRASE, AVATAR, RED_RANK, BLUE_RANK, GREEN_RANK, YELLOW_RANK FROM USERS u, COLOR_RANKING c WHERE u.USER_ID=c.USER_ID AND u.USERNAME=?;");
			query.setString(1, username);
			ResultSet result = query.executeQuery();

			FirstNameBox.setText(result.getString("FIRST_NAME"));
			LastNameBox.setText(result.getString("LAST_NAME"));
			DobBox.setValue(LocalDate.parse((result.getString("DOB"))));
			UserNameBox.setText(username);
			UserPhoneBox.setText(result.getString("PHONE"));
			UserEmailBox.setText(result.getString("EMAIL_ID"));
			PassPhraseBox.setText(result.getString("PASSPHRASE"));
			RedRankBox.setText(new Integer(result.getInt("RED_RANK")).toString());
			BlueRankBox.setText(new Integer(result.getInt("BLUE_RANK")).toString());
			GreenRankBox.setText(new Integer(result.getInt("GREEN_RANK")).toString());
			YellowRankBox.setText(new Integer(result.getInt("YELLOW_RANK")).toString());

			byte[] AvatarArr = result.getBytes("AVATAR");

			ByteArrayInputStream in = new ByteArrayInputStream(AvatarArr);
			BufferedImage read = ImageIO.read(in);
			AvatarBox.setImage(SwingFXUtils.toFXImage(read, null));
			in.close();

		}
		catch(Exception ex) { ex.printStackTrace(); }


	}

	private void notifyInputError(Control errField){
		/*
		Notifications.create()
		.title("Incomplete input!")
		.text("One or more fields are not entered.")
		.darkStyle()
		.hideCloseButton()
		.showWarning();
		 */

		errField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");

		//Tooltip.install(errField, "Input not in given range.");
		ValidationMessage.error(errField, "One or more fields are not entered.");
		return;

	}

}


