package com.manage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MailConnector extends DataConnector implements Create,Delete,Update,Read {

	static ArrayList<String> types = new ArrayList<>(); // to store notification that we been created.
	/**
	* a constructor that will connect with the database.
	 * @throws SQLException.
	 * @throws ClassNotFoundException.
	*/
	public MailConnector() throws ClassNotFoundException, SQLException {
		connection = getConnection();
		quarry = "SELECT * FROM mails";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			types.add(result.getString("subject"));
		}
	}
	@Override
	public void read() throws SQLException {
		quarry = "SELECT * FROM mails";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			System.out.println(result.getInt("id")
							   + " - " + result.getString("subject") +
							   "\n" + result.getString("content")
					);
		}
	}

	@Override
	public boolean read(int id) throws SQLException {
		quarry = "SELECT subject,content FROM mails WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			System.out.println(id + " - " +  result.getString("subject")+"==>>" + result.getString("content"));
			return true;
		}
		return false;
	}

	@Override
	public boolean update(int id,String subject, String content) throws SQLException {
		quarry = "SELECT * FROM mails";
		statement = connection.prepareStatement(quarry);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			int a= result.getInt("id");
			if ( a == id) {
				quarry = "UPDATE mails SET subject = ? , content = ?" + "WHERE id = ?";
				statement = connection.prepareStatement(quarry);
				statement.setString(1, subject); // for content
				statement.setString(2, content); // for subject
				statement.setInt(3, id);
				statement.executeUpdate();
				return true; // if exist.
			}
		}
		return false; // if does not exist.
	}

	@Override
	public boolean delete(int id) throws SQLException {
		String gotSubject = getSubject(id);
		quarry = "DELETE FROM mails WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		if (statement.execute()) {
			types.remove(types.indexOf(gotSubject));
			statement.close(); // for memory lack
//			getTypes();
			return true;
		}
		return false;
	}

	@Override
	public boolean create(NotificationModel notification) throws SQLException {
		if (types.contains(notification.getSubject())) {
			return false;
		}
		quarry = "INSERT INTO MAILS(id,subject,content)" + "VALUES(?,?,?)" ;
		statement = connection.prepareStatement(quarry);
		statement.setInt(1,notification.getId());
		statement.setString(2,notification.getSubject());
		statement.setString(3,notification.getContent());
		statement.executeUpdate();
		statement.close();
		types.add(notification.getSubject());
		return true;
	}
	
	public String getSubject(int id) throws SQLException{
		quarry = "SELECT subject,content FROM mails WHERE id=?";
		statement = connection.prepareStatement(quarry);
		statement.setInt(1, id);
		ResultSet result = statement.executeQuery();
		if(result.next()) {
			return result.getString("subject");
		}
		return null;
	}
	
}
