package attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/*
SQL> create table cardcheck
  2  (name varchar2(20),
  3  enter varchar2(30),
  4  leave varchar2(30);
 */
public class CardCheckDB {

	
	public CheckInfo checkIf(int num) {
		CheckInfo ci = new CheckInfo();
		
		Connection con = MakeConn.getConnection();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select num, name, to_date(enter, 'yyyy/MM/dd HH:mi:ss'), to_date(leave, 'yyyy/MM/dd HH:mi:ss') from cardcheck ");
		sb.append("where num = ?");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, num);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				ci.setNum(rs.getInt("num"));
				ci.setName(rs.getString("name"));
				ci.setEnter(rs.getString("enter"));
				ci.setLeave(rs.getString("leave"));
				ci.setDates(rs.getString("dates"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ci;
	}
	/* 입실 시간 체크 */
	public boolean enterCard(CheckInfo info) {
		Connection con = MakeConn.getConnection();
		boolean ok = false;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into cardcheck( ");
		sb.append("num, name, enter, dates) ");
		sb.append("values(check_seq.nextval, ?, ?, ? )");
//		sb.append("values(check_seq.nextval, ?, to_date(sysdate, 'yyyy/MM/dd HH24:mi:ss') )");
		
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, info.getName());
			ps.setString(2, info.getEnter());
			ps.setString(3, info.getDates());
			

			
			
			int save = ps.executeUpdate();
			
			if (save > 0) {
				System.out.println("성공");
				ok = true;
			}
			else System.out.println("실패");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ok;
		
	}
	/* 리스트 출력 */
	public Vector checkList() {
		
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select * from cardcheck ");
		sb.append("order by num asc");
		
		try {
			ps = con.prepareStatement(sb.toString());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String enter = rs.getString("enter");
				String leave = rs.getString("leave");
				String dates = rs.getString("dates");
				
				Vector vc = new Vector();
				
				vc.add(num);
				vc.add(name);
				vc.add(enter);
				vc.add(leave);
				vc.add(dates);
				
				data.add(vc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return data;
	}
	/* 퇴실 시간 체크 */
	public boolean leaveCard(CheckInfo ci) {
		boolean ok = false;
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update cardcheck ");
//		sb.append("set leave = to_date(sysdate, 'yyyy/MM/dd HH:mi:ss') ");
		sb.append("set leave = ? ");
		sb.append("where dates = ? and name = ?");
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, ci.getLeave());
			ps.setString(2, ci.getDates());
			ps.setString(3, ci.getName());
			
			int save = ps.executeUpdate();
			
			if(save > 0) ok = true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ok;
	}
	/* 삭제 */
	public boolean deleteCheck(CheckInfo ci) {
		boolean ok = false;
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		
		sb.append("delete from cardcheck ");
		sb.append("where num = ?");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, ci.getNum());
			int save = ps.executeUpdate();
			if(save > 0) ok = true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("오류 발생");
		}finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ok;
		
	}
	
	/* 퇴실 시간 */
	
	public String enterCheck() {
		
//		Vector v = new Vector();
		String enter = "";
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("select enter from cardcheck ");
		sb.append("order by num asc");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				enter = rs.getString("enter");
				
				
			}
			
			System.out.println(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return enter;
		
	}
	/* 퇴실 시간 */
	public String leaveCheck() {
		
//		Vector v = new Vector();
		String leave = "";
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("select leave from cardcheck ");
		sb.append("order by num asc ");
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				leave = rs.getString("leave");
				
//				v.add(leave);
			}
			
			System.out.println(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return leave;
		
	}
	/* 선택된 리스트 출력 */
	public Vector selectCheckList(String nam) {
		
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select * from cardcheck ");
		sb.append("where name = ? ");
		sb.append("order by num asc");
		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, nam);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String enter = rs.getString("enter");
				String leave = rs.getString("leave");
				String dates = rs.getString("dates");
				
				Vector vc = new Vector();
				
				vc.add(num);
				vc.add(name);
				vc.add(enter);
				vc.add(leave);
				vc.add(dates);
				
				data.add(vc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return data;
	}
	
	public Vector compareEnterInfo(CheckInfo ci) {
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select enter from cardcheck ");
		sb.append("where name = ? and dates = ? ");

		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, ci.getEnter());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String enter = rs.getString("enter");
//				String leave = rs.getString("leave");
				
//				Vector v = new Vector();
//				v.add(enter);
//				v.add(leave);
				data.add(enter);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public Vector compareLeaveInfo(CheckInfo ci) {
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select leave from cardcheck ");
		sb.append("where name = ? and dates = ? ");

		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, ci.getLeave());
			rs = ps.executeQuery();
			
			while(rs.next()) {
//				String enter = rs.getString("enter");
				String leave = rs.getString("leave");
				
//				Vector v = new Vector();
//				v.add(enter);
//				v.add(leave);
				data.add(leave);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public Vector insertInfo() {
		
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select name from gt_member ");
//		sb.append("where name in ( ");
//		sb.append("select name from cardcheck) ");
//		sb.append("order by num asc");
		
		try {
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				String name = rs.getString("name");
				
//				Vector v = new Vector();
//				v.add(name);
				data.add(name);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return data;
	}
	public Vector AttendName() {
		
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		sb.append("select name from cardcheck ");
//		sb.append("where name in ( ");
//		sb.append("select name from cardcheck) ");
//		sb.append("order by num asc");
		
		try {
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			
			
			while(rs.next()) {
				String name = rs.getString("name");
				
				data.add(name);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	

}
