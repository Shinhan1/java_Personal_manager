package attendance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConn {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "GT_PM";
	private static final String PASSWORD = "shin";
	
	static Connection conn = null;
	private static MakeConn mc;
	
	public static MakeConn getInstance() {
		if(mc == null) mc = new MakeConn();
		return mc;
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
}

