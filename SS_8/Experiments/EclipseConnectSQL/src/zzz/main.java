package zzz;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class main {

	public static void main(String[] args) {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
			//change for ISU Server
			//currently works on local
			
			java.sql.Statement stt = con.createStatement();
			
			//Create and select db;
		//	stt.execute("CREATE DATABASE IF NOT EXISTS testing3");
			stt.execute("USE petclinic");
			
			//create our table
//			stt.execute("DROP TABLE IF EXISTS testing3");
//			stt.execute("CREATE TABLE testing3("+
//					 "id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," +
//					 " user_name VARCHAR(30)," +
//					  "user_pass VARCHAR(30)," +
//					 " user_email VARCHAR(255)"+ ")");
			
			//Add some entries
			stt.execute("INSERT INTO owners (address, first_name, last_name, telephone) VALUES" +
					"('STANFORD', 'Kordell', 'Schrock', '777'),('Google', 'Apple', 'Bitcoin', '555')"); 
			//this is a post call
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
}
}
