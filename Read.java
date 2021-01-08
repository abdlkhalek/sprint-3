package com.manage;

import java.sql.SQLException;

public interface Read {
	public void read() throws SQLException;
	public boolean read(int id) throws SQLException;
}
