/**
 * <h1>Database Connector</h1>
 * an interface that connect our application with the database, and do some operation on.
 * <p>
 * 
 *  
 *  @author  Ahmed Ali 
 *  @version 1.0
 *  @since   07 - 1 - 2021
 */
package com.manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class DataConnector {
	
	Connection connection ; // to connect purpose.
	String quarry; // to write our quarry.
	PreparedStatement statement; // to implement our quarry.
	final String dbhost = "localhost"; // our host
	final String dbPort = "3306"; // our port
	String dbName="notifications";
	final String dbUser = "root"; // database user 
	final String dbPassword = "Abcd1234**"; // database password

	/**
	 * a function will get connection with database the return connection to be used in constructor. 
	 * @throws ClassNotFoundException, SQLException.
	 * @return connection .
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
			String connection_ = "jdbc:mysql://" + dbhost +  ":" + dbPort + "/" + dbName;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connection_,dbUser,dbPassword);
//			System.out.println("You're allowed To Send Notification Right Now");
			return connection;
	}
	
}
