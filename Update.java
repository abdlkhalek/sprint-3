package com.manage;

import java.sql.SQLException;

public interface Update {
	public boolean update(int id,String subject,String content) throws SQLException;
}
