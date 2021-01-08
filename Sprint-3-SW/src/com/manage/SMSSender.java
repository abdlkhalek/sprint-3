package com.manage;

import java.sql.SQLException;

public class SMSSender implements Sender{

	private DataConnector data = new DataConnector();
	/**
	 *	<h1>constructor</h1>
	 *	a public constructor to be accessed in main method
	 *	@throws ClassNotFoundException
	 *  @throws SQLException
	 */
	public SMSSender() throws ClassNotFoundException, SQLException{
		data.connection = data.getConnection();
	}
	
	
	/**
	 * <h1>send function</h1>
	 * a function to mock of send an email
	 * @param type,notificationType (which type stands for sms or mail , and notificationType stands for which type of notification wanna send)
	 * @return true if notification sent false if not. 
	 * @throws SQLException 
	 */
	@Override
	public boolean send( String notificationType) throws SQLException {
		if (MailConnector.types.contains(notificationType)) {
				data.quarry = "DELETE FROM sms WHERE subject=?";
				data.statement = data.connection.prepareStatement(data.quarry);
				data.statement.setString(1, notificationType);
				if (data.statement.execute()) {
					return true;
				}
		}
		return false;
	} // end of function.


}
