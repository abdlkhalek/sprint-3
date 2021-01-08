package com.manage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SMSConnector extends DataConnector implements Create,Delete,Update,Read {

	static ArrayList<String> types = new ArrayList<>(); // to store notification that we been created.
	/**
	* a constructor that will connect with the database.
	 * @throws SQLException.
	 * @throws ClassNotFoundException.
	*/
	public SMSConnector() throws ClassNotFoundException, SQLException {
		connection = getConnection();
		quarry = "SELECT * FROM sms";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			types.add(result.getString("subject"));
		}
		statement.close();// for memory lack
	}
	@Override
	public void read() throws SQLException {
		quarry = "SELECT * FROM sms";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			System.out.println(result.getInt("id")
							   + " - " + result.getString("subject") +
							   "\n" + result.getString("content")
					);
		}
		statement.close();// for memory lack
	}

	@Override
	public boolean read(int id) throws SQLException {
		quarry = "SELECT subject,content FROM sms WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			System.out.println(id + " - " +  result.getString("subject")+"==>>" + result.getString("content"));
			statement.close();// for memory lack
			return true;
		}
		statement.close();// for memory lack
		return false;
	}

	@Override
	public boolean update(int id,String subject, String content) throws SQLException {
		quarry = "SELECT * FROM sms";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			int a= result.getInt("id");
			if ( a == id) {
				quarry = "UPDATE sms SET subject = ? , content = ?" + "WHERE id = ?";
				statement = connection.prepareStatement(quarry);
				statement.setString(1, subject); // for content
				statement.setString(2, content); // for subject
				statement.setInt(3, id);
				statement.executeUpdate();
				statement.close();// for memory lack
				return true; // if exist.
			}
		}
		statement.close();// for memory lack
		return false; // if does not exist.
	}

	@Override
	public boolean delete(int id) throws SQLException {
		String gotSubject = getSubject(id);
		quarry = "DELETE FROM sms WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		if (statement.execute()) {
			types.remove(types.indexOf(gotSubject));
			statement.close(); // for memory lack
			System.out.println("Deleted Successfully");
			return true;
		}
		statement.close();// for memory lack
		return false;
	}

	@Override
	public boolean create(NotificationModel notification) throws SQLException {
		if (types.contains(notification.getSubject())) {
			return false;
		}
		quarry = "INSERT INTO sms(id,subject,content)" + "VALUES(?,?,?)" ;
		statement = connection.prepareStatement(quarry);
		statement.setInt(1,notification.getId());
		statement.setString(2,notification.getSubject());
		statement.setString(3,notification.getContent());
		statement.executeUpdate();
		statement.close(); // for memory lack
		types.add(notification.getSubject());
		return true;
	}
	
	public String getSubject(int id) throws SQLException{
		quarry = "SELECT subject FROM sms WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			String subject = result.getString("subject");
			statement.close();// for memory lack
			return subject;
		}
		statement.close();// for memory lack
		return null;
	}
	
}
