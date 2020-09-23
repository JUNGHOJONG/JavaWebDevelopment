package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	LinkedList<Connection> connList = new LinkedList<>();
	public DBConnectionPool(String driver, String url,
			String username, String password) throws Exception {
		Class.forName(driver);
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection() throws Exception{
		if(connList.size() > 0) {
			Connection connection = connList.remove(0);
			if(connection.isValid(10)) {
				return connection;
			}
		}
		return DriverManager.getConnection(url, username, password);
	}
	
	public void returnConnection(Connection connection) throws Exception{
		connList.addFirst(connection);
	}

	public void closeAll() {
		for( Connection c : connList ) {
			try { c.close(); } catch (Exception e) {}
		}
	}
}
