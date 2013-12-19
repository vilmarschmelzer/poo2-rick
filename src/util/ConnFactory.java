package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnFactory {
	public static Connection getConnetion() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		//"jdbc:postgresql://hostname:port/dbname","username", "password"
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/rick","postgres","root");
	}
	private static void close(Connection con,Statement stmt, ResultSet rs) throws SQLException{
		if(con!=null)
			con.close();
		if(stmt!=null)
			stmt.close();
		if(rs!=null)
			rs.close();
	}
	public static void closeConnection(Connection con,Statement stmt, ResultSet rs) throws SQLException{
		close(con,stmt,rs);
	}
	public static void closeConnection(Connection con,Statement stmt) throws SQLException{
		close(con,stmt,null);
		
	}
	public static void closeConnection(Connection con) throws SQLException{
		close(con,null,null);
	}
}