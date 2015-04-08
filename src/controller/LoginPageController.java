package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import driver.Authorizer;
import driver.GridCell;
import driver.RandMatrixCreator;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPageController implements Initializable {
	
	@FXML public GridPane colorMatrix;
	
	
	@FXML public GridPane textualMatrix;
	@FXML public TextField textAuthInput;
	@FXML public TextField usernameBox;
	@FXML public Button textAuthSubmitBtn;
	
	public static ToggleButton prevSelectedinTextual=null;
	public static ToggleButton currSelectedinTextual=null;
	
    @Override
    public void initialize(URL location, ResourceBundle resources){
		
		try{ 
			ToggleGroup ColorMatrixGroup = new ToggleGroup();
			ToggleGroup TextualMatrixGroup = new ToggleGroup();
			
			//Color Authentication Matrix computation:
			
			ObservableList<Node> colorMatrixNodes = colorMatrix.getChildren();
			int[][] colorMatrix = RandMatrixCreator.getColorMatrix(4);
			
			thisLoop:
			for(Node n: colorMatrixNodes) {
					int row=0,col=0;
				try{
				col = GridPane.getColumnIndex(n);
				row = GridPane.getRowIndex(n);
				}
				 //if(col==0||row==0) continue thisLoop;
				 catch(NullPointerException e) {
					continue thisLoop;
				 }
				 Label cell = (Label) n;
				 cell.setText(new Integer(colorMatrix[row-1][col-1]).toString());
				 
			}
			
			
			
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
			}
			
			textAuthSubmitBtn.setOnAction(new EventHandler<ActionEvent>() { 
				@Override 
				public void handle(ActionEvent e) { 
					
					boolean isAuthorized = new Authorizer().TextualAuthorizer(usernameBox.getText(),textAuthInput.getText(),TextualAuthHash,matrixArray);
					if(isAuthorized) System.out.println("Authorized");
					
					else  System.out.println("You shall not enter");
					} });;	
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
