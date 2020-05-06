package attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ManagerDB {
	public static void main(String[] args) {
		// JDBC 표준 입력 API
		
//		MakeConn mc1 = MakeConn.getInstance();
//		MakeConn mc2 = MakeConn.getInstance();
		
//		System.out.println("mc1 : " + mc1);
//		System.out.println("mc2 : " + mc2);
		
		// dbms 연결
		Connection conn = MakeConn.getConnection();
//		System.out.println("conn : " +conn);
		
		String id = "sh5444";
		String pw = "doal12";
		String name = "신한";
		
		// SQL statement
		StringBuffer sb = new StringBuffer();
		sb.append("insert into managerLogin ");
		sb.append("values (?, ?, ?)");
		
		// 문장 객체
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			
			// 실행
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 자원 반납
		finally {
			try {
				pstmt.close();
				conn.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
				
	}

}

