package driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javafx.scene.control.ToggleButton;
import controller.DBController;

public class Authorizer {
	
	public boolean TextualAuthorizer(String username, String textualAuthInput,HashMap<Integer,GridCell> textualHash, ToggleButton[][] matrixArray){
		
		try{
		Connection dbc = DBController.init();
		//Statement stmt = dbc.createStatement();
		PreparedStatement query= dbc.prepareStatement("SELECT passphrase FROM users WHERE Name=?;");
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
		
		System.out.println(correctTextualInput);
		
		if((correctTextualInput.toString()).equals(textualAuthInput)) return true;
		else return false;
		
		}
		catch(Exception e){
			e.printStackTrace();
			 return false;
		}
	}
}
