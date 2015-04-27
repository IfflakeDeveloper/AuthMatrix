package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.controlsfx.control.Notifications;

import virtualKeyboard.VirtualKeyboard;
import driver.Authorizer;
import driver.ColorPair;
import driver.ColorView;
import driver.GridCell;
import driver.KeyHook;
import driver.RandMatrixCreator;
import driver.WindowsLocker;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPageController implements Initializable {

	@FXML public AnchorPane ColorPane;
	@FXML public AnchorPane TextualPane;
	@FXML public GridPane colorMatrix;
	@FXML public GridPane colorStrip;
	@FXML private Button ExitButton;
	@FXML public GridPane textualMatrix;
	@FXML public TextField colorAuthInput;
	@FXML public TextField textAuthInput;
	@FXML public TextField usernameBox;
	@FXML public Button colorAuthSubmitBtn;
	@FXML public Button textAuthSubmitBtn;
	@FXML private ImageView AvatarBox;
	@FXML private AnchorPane vKeyboardArea;


	public static ToggleButton prevSelectedinTextual=null;
	public static ToggleButton currSelectedinTextual=null;
	public WindowsLocker wLocker;



	public void setLocker(WindowsLocker wl){
		wLocker = wl;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources){

		try{ 
			ColorPane.setVisible(false);
			ExitButton.setOnAction((new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent e){ 
					//KeyHook.unblockWindowsKey();
					wLocker.unlock();
					System.exit(0);
				} }));

			VirtualKeyboard vkb = new VirtualKeyboard();
			vkb.view().setStyle("-fx-border-color: darkblue; -fx-border-radius: 5;");
			vKeyboardArea.getChildren().addAll(vkb.view());


			usernameBox.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent e) { 

					try{
						PreparedStatement stmt = null;
						Connection conn = DBController.init();
						stmt = conn.prepareStatement("SELECT AVATAR FROM USERS WHERE USERNAME=?;");

						stmt.setString(1, usernameBox.getText());
						ResultSet rs = stmt.executeQuery();

						byte[] AvatarArr = rs.getBytes("AVATAR");
						conn.close(); conn=null; 
						ByteArrayInputStream in = new ByteArrayInputStream(AvatarArr);
						BufferedImage read = ImageIO.read(in);
						AvatarBox.setImage(SwingFXUtils.toFXImage(read, null));
						colorAuthSubmitBtn.setDefaultButton(true);
						ColorPane.setVisible(true);
					}
					catch(Exception ex){ ex.printStackTrace(); }
				} });

			ToggleGroup ColorMatrixGroup = new ToggleGroup();
			ToggleGroup TextualMatrixGroup = new ToggleGroup();

			//Color Authentication Matrix computation:

			ArrayList<ColorPair> colorPairList = new ArrayList<ColorPair>();

			for(int x=0;x<4;++x)
				for(int y=0;y<4;++y)
					if(x!=y) colorPairList.add(new ColorPair(x,y));

			Collections.shuffle(colorPairList);

			ObservableList<Node> colorStripNodes = colorStrip.getChildren();

			int colorPairIndex=0;
			ColorPair[] colorPairArray = new ColorPair[4];

			for(Node n: colorStripNodes){

				GridPane colorPairGrid = (GridPane) n;

				ObservableList<Node> colorStrip = colorPairGrid.getChildren();
				//int colorIndex =1;

				int PaneColIndex=0;
				try{ PaneColIndex=GridPane.getColumnIndex(n); } catch(Exception e){}

				colorPairArray[PaneColIndex] = colorPairList.get(colorPairIndex);


				for(Node p : colorStrip)
				{
					ImageView colorHolder = (ImageView) p;

					int colIndex=0;
					try{ colIndex=GridPane.getColumnIndex(p); } catch(Exception e){}

					int color=0;
					if(colIndex==0)  color = colorPairList.get(colorPairIndex).color1;
					else color=colorPairList.get(colorPairIndex).color2;

					ColorView.setColor(colorHolder, color);


					Tooltip.install(colorHolder, new Tooltip(ColorView.getColorName(color)));

				}
				colorPairIndex++;
			}

			ObservableList<Node> colorMatrixNodes = colorMatrix.getChildren();
			int[][] colorMatrix = RandMatrixCreator.getColorMatrix(4);

			thisLoop:
				for(Node n: colorMatrixNodes) {
					int row=0,col=0;
					try{
						col = GridPane.getColumnIndex(n);
						row = GridPane.getRowIndex(n);
					}
					catch(NullPointerException e) {
						continue thisLoop;
					}
					Label cell = (Label) n;

					cell.setText(new Integer(colorMatrix[row-1][col-1]).toString());

				}


			colorAuthSubmitBtn.setOnAction(new EventHandler<ActionEvent>() { 
				@Override 
				public void handle(ActionEvent e) { 

					boolean isAuthorized = new Authorizer().ColorAuthorizer(usernameBox.getText(),colorMatrix,colorPairArray,colorAuthInput.getText());
					if(isAuthorized) { 
						System.out.println("Authorized"); 
						ColorPane.setVisible(false);
						ColorPane.getChildren().clear();
						ColorPane.getChildren().setAll(TextualPane.getChildren());
						ColorPane.setVisible(true); 
						textAuthSubmitBtn.setDefaultButton(true);}

					else  System.out.println("You shall not enter");


				} });	







			//Textual auth Toggle:
			Integer[] textualMatrixArray = driver.RandMatrixCreator.getTextualMatrix();
			ObservableList<Node> textualMatrixNodes = textualMatrix.getChildren();
			ToggleButton matrixArray[][]= new ToggleButton[6][6];
			HashMap<Integer,GridCell> TextualAuthHash = new HashMap<Integer,GridCell>();

			EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() { 
				@Override 
				public void handle(ActionEvent e) { 
					TextualMatrixListener(TextualMatrixGroup, matrixArray);
				} };



				for(Node n: textualMatrixNodes) {

					int columnIndex = 0; int rowIndex=0;

					ToggleButton tb = (ToggleButton) n;
					tb.setToggleGroup(TextualMatrixGroup);
					tb.setOnAction(ev);
					Integer ci = textualMatrix.getColumnIndex(n);
					Integer ri =  textualMatrix.getRowIndex(n);
					if(ci!=null) columnIndex = ci; 
					if(ri!=null) rowIndex = ri;
					matrixArray[rowIndex][columnIndex]=tb;
					int randVal = textualMatrixArray[(columnIndex*6)+rowIndex];
					TextualAuthHash.put(randVal, new GridCell(rowIndex,columnIndex));
					if(randVal<=9) tb.setText(String.valueOf(randVal));
					else{
						char c =(char) (( (int) 'A' )+ (randVal-10));
						tb.setText(String.valueOf(c));
					}
					
					//tb.setDisable(true);
				}

				textAuthSubmitBtn.setOnAction(new EventHandler<ActionEvent>() { 
					@Override 
					public void handle(ActionEvent e) { 
						String username = usernameBox.getText();
						boolean isAuthorized = new Authorizer().TextualAuthorizer(username,textAuthInput.getText(),TextualAuthHash,matrixArray);
						if(isAuthorized) { System.out.println("Authorized"); 
						wLocker.unlock();
						try{
							Connection conn =DBController.init();
							conn.prepareStatement("UPDATE USERS SET LAST_LOGIN=CURRENT_TIMESTAMP WHERE USERNAME='"+username+"';").executeUpdate();
							conn.close(); conn=null;
						}
						catch(Exception ex) {ex.printStackTrace();System.exit(0);}
						System.exit(0); 
						}

						else  System.out.println("You shall not enter");
					} });	
		}

		catch(Exception e){
			e.printStackTrace();
		}

	}




	public  void TextualMatrixListener(ToggleGroup TextualMatrixGroup, ToggleButton[][] matrixArray){

		currSelectedinTextual = (ToggleButton) TextualMatrixGroup.getSelectedToggle();

		if( prevSelectedinTextual == null) {
			prevSelectedinTextual = currSelectedinTextual;
			prevSelectedinTextual.setSelected(true);
		}
		else{
			Integer ci = GridPane.getColumnIndex(currSelectedinTextual);
			Integer ri =GridPane.getRowIndex(prevSelectedinTextual);
			int rowIntersection=0, colIntersection=0;

			if(ri!=null)  rowIntersection = ri;
			if(ci!=null)	 colIntersection =ci;

			textAuthInput.appendText(  matrixArray[rowIntersection][colIntersection].getText() );

			currSelectedinTextual .setSelected(false);
			prevSelectedinTextual.setSelected(false);

			prevSelectedinTextual=null;
			currSelectedinTextual=null;
		}	

	}

}
