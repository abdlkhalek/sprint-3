package com.manage;

import java.sql.SQLException;

public interface Create {
	
	public boolean create(NotificationModel notification) throws SQLException;
}
