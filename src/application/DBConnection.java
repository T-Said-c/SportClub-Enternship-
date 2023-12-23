package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public Connection con;
	
	public Connection sportclubcon() {
      String databaseName = "sportclub";
      String databaseUser = "root";
      String databasePassword = "inconnus";
      String URL ="jdbc:mysql://localhost/" + databaseName;
      
    	  try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL,databaseUser,databasePassword);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    	  
     return con;
      
      
	}

}
