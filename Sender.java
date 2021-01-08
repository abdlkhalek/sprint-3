package com.manage;

import java.sql.SQLException;

public interface Sender {
	
	public boolean send(String notificationType) throws ClassNotFoundException, SQLException;
}
