package driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ToggleButton;
import controller.DBController;

public class Authorizer {
	
	
	public boolean ColorAuthorizer(String username, int[][] colorMatrix, ColorPair[] colorPairList ,String colorAuthInput){
	
		try{
			Connection dbc = DBController.init();
			PreparedStatement query= dbc.prepareStatement("SELECT USER_ID FROM USERS WHERE USERNAME=?;");
			query.setString(1, username);
			ResultSet rs = query.executeQuery();
			
			StringBuffer correctColorInput = new StringBuffer();
			
			if(rs==null) return false;
			else{
			
			query= dbc.prepareStatement("SELECT RED_RANK,BLUE_RANK,GREEN_RANK,YELLOW_RANK FROM COLOR_RANKING c, USERS u WHERE u.USERNAME=? AND c.USER_ID=u.USER_ID;");
			query.setString(1, username);
			 rs = query.executeQuery();
				
			int colorRank[] = new int[4]; 
			
			
			colorRank[0] = rs.getInt("RED_RANK");
			colorRank[1] = rs.getInt("BLUE_RANK");
			colorRank[2] = rs.getInt("GREEN_RANK");
			colorRank[3] = rs.getInt("YELLOW_RANK");
			
			for(int i=0;i<4;++i){
				
				
				ColorPair currPair = colorPairList[i];
				int rowIndex = colorRank[currPair.color1];
				int colIndex = colorRank[currPair.color2];
				
				correctColorInput.append(colorMatrix[rowIndex-1][colIndex-1]);
				
				}
			
			if(correctColorInput.toString().equals(colorAuthInput)) return true;
			else return false;
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public boolean TextualAuthorizer(String username, String textualAuthInput,HashMap<Integer,GridCell> textualHash, ToggleButton[][] matrixArray){
		
		try{
		Connection dbc = DBController.init();
		//Statement stmt = dbc.createStatement();
		PreparedStatement query= dbc.prepareStatement("SELECT PASSPHRASE FROM USERS WHERE USERNAME=?;");
		query.setString(1, username);
		ResultSet rs = query.executeQuery();
		String passphrase = rs.getString("passphrase");
		StringBuffer correctTextualInput = new StringBuffer();
		int len = passphrase.length();
		int charForRow =0, charForCol, rowIndex, columnIndex;
		for(int i=0;i<len;i=i+2){
			
			charForRow = 10 + (int) (passphrase.charAt(i)-'A');
			charForCol = 10 + (int) (passphrase.charAt(i+1)-'A');
			
			rowIndex = textualHash.get(new Integer(charForRow)).row;
			columnIndex = textualHash.get(new Integer(charForCol)).column;
			
			correctTextualInput.append(  matrixArray[rowIndex][columnIndex].getText() );
			
		}
		
		
		if((correctTextualInput.toString()).equals(textualAuthInput)) return true;
		else return false;
		
		}
		catch(Exception e){
			e.printStackTrace();
			 return false;
		}
	}
	
	
}
