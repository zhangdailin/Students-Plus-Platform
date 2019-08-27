package com.czy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private final String Driver = "com.mysql.jdbc.Driver";

	private final String URL = "jdbc:mysql://localhost:3306/daige";

	private final String USER = "root";

	private final String PASSWORD = "Zhang1996";

	private Connection conn = null;

	public DBConnection() throws SQLException {
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public Connection getConnection() {
		return conn;
	}

	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
