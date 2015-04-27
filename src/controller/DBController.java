package controller;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement; 

public class DBController {

	static Connection connection = null;  
    static ResultSet resultSet = null;  
    static Statement statement = null;
	

    public static Connection init(){
    	
    try 
    {  
        Class.forName("org.sqlite.JDBC");  
        connection = DriverManager.getConnection("jdbc:sqlite:src/model/AuthDb.db"); 
        statement = connection.createStatement();  
        
    }
    
    catch(Exception e){ 
    	
    	e.printStackTrace();
    	
    }
    
    return connection;
}
}
