package attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.sun.xml.internal.ws.api.pipe.NextAction;

// num, name, attend, absence, late, leave, goout, aapers, acasal, empsal, tosal;

public class AttendAbsenceDB {
	public AttendAbsenceInfo AttAbsInfo(int num) {
		AttendAbsenceInfo info = new AttendAbsenceInfo();

		Connection conn = MakeConn.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("select * from AttAbs ");
		sb.append("where num = ?");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, num);

			rs = ps.executeQuery();

			if (rs.next()) {
				info.setNum(rs.getInt("num"));
				info.setName(rs.getString("name"));
				info.setAttend(rs.getInt("attend"));
				info.setAbsence(rs.getInt("absence"));
				info.setLate(rs.getInt("late"));
				info.setLeave(rs.getInt("leave"));
				info.setGoout(rs.getInt("goout"));
				info.setAppers(rs.getDouble("appers"));
				info.setAcasal(rs.getInt("acasal"));
				info.setEmpsal(rs.getInt("empsal"));
				info.setTosal(rs.getInt("tosal"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;

	}

	public Vector getAttAbsList() {
		Vector data = new Vector();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from AttAbs ");
		sb.append("order by num asc");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				int attend = rs.getInt("attend");
				int absence = rs.getInt("absence");
				int late = rs.getInt("late");
				int leave = rs.getInt("leave");
				int goout = rs.getInt("goout");
				double appers = rs.getDouble("appers");
				int acasal = rs.getInt("acasal");
				int empsal = rs.getInt("empsal");
				int tosal = rs.getInt("tosal");
				int weekday = rs.getInt("weekday");
				int remainWeekday = rs.getInt("remainWeekday");

				Vector row = new Vector(); // jtable에 저장하기 위해 Vector를 사용
				row.add(num);
				row.add(name);
				row.add(attend);
				row.add(absence);
				row.add(late);
				row.add(leave);
				row.add(goout);
				row.add(appers);
				row.add(acasal);
				row.add(empsal);
				row.add(tosal);
				row.add(weekday);
				row.add(remainWeekday);

				data.add(row);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return data;
	}

	public boolean Attend(AttendAbsenceInfo aai) {
		int wc = weekdayCount();
		Connection conn = MakeConn.getConnection();
		boolean ok = false;
		StringBuffer sb = new StringBuffer();
		sb.append("insert into attabs ");
		sb.append("(num, name, attend, absence, ");
		sb.append("late, leave, goout, appers, ");
		sb.append("acasal, empsal, tosal, weekday, remainweekday) ");
		sb.append("values(AA_seq.nextval, ?, 0, 0, 0, ");
//		sb.append("0, 0, 0.0, 116000, 284000, 400000, ?, ?) ");
		sb.append("0, 0, 0.0, 0, 0, 0, ?, ?) ");

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, aai.getName());
			ps.setInt(2, wc);
			ps.setInt(3, wc);
			
			int save = ps.executeUpdate();
			if (save > 0)
				ok = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

	public int weekdayCount() {
		Connection conn = MakeConn.getConnection();
		int wc = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) CNT ");
		sb.append("FROM (SELECT TO_CHAR(SDT + LEVEL - 1, 'YYYYMMDD') DT ");
		sb.append(", TO_CHAR(SDT + LEVEL - 1, 'D') D ");
		sb.append("FROM (SELECT TO_DATE('20200203', 'YYYYMMDD') SDT ");
		sb.append(", TO_DATE('20200302', 'YYYYMMDD') EDT ");
		sb.append("FROM DUAL) ");
		sb.append("CONNECT BY LEVEL <= EDT - SDT + 1 ");
		sb.append(") A ");
		sb.append(", ( ");
		sb.append("SELECT '20200301' DT1, '20200302' DT, '3.1절', '3.1절 대체 휴일' CMT FROM DUAL ) B ");
		sb.append("WHERE A.DT = B.DT(+) ");
		sb.append("AND A.D NOT IN ('1', '7') ");
		sb.append("AND B.DT IS NULL ");
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				wc = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				rs.close();
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wc;

	}

	public void UpdateAttendCount(String name) {
//		String name = AttendName();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set attend = attend + 1 ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void UpdateAbsenceCount(String name) {
//		String name = AttendName();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set absence = absence + 1 ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void UpdateLateCount(String name) {
		
//		String name = AttendName();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set late = late + 1 ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void UpdateLeaveCount(String name) {
		
//		String name = AttendName();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set leave = leave + 1 ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void UpdateGooutCount(String name) {
		
//		String name = AttendName();

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set goout = goout + 1 ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	public void UpdateApper(String name) {
//		double ac = ApperCount(name);
		
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set appers = round(((attend + late + leave - absence)- floor((late+leave)/3))/weekday*100, 2) ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
//			ps.setDouble(1, ac);
			ps.setString(1, name);
			
			ps.executeUpdate();
			
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
		
		
	}
	
	public void UpdateRemain(String name) {
//		int wc = remainWeekdayCount(name);

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set remainweekday = (weekday - (attend + late + absence + leave)) ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
//			ps.setInt(1, wc);
			ps.setString(1, name);
//			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	public void UpdateAcasal(String name) {
//		int acasal = selectAcasal(name);

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set acasal = (attend+mod((late+leave),3)) * 5800 ");
		sb.append("where name = ? and acasal < 116000 and appers >= 80.0");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
//			ps.setInt(1, acasal);
			ps.setString(1, name);
//			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void UpdateEmpsal(String name) {
//		int empsal = selectEmpsal(name);

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set empsal = (attend+mod(late+leave,3))*14200 ");
		sb.append("where name = ? and empsal < 284000 and appers >= 80.0 ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
//			ps.setInt(1, empsal);
			ps.setString(1, name);
//			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void UpdateTosal(String name) {

		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.delete(0, sb.length());
		sb.append("update attabs ");
		sb.append("set tosal = acasal + empsal ");
		sb.append("where name = ? ");
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			System.out.println(name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void UpdateSubAtt(String name) {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set attend = attend - 1, ");
//		sb.append("appers = round(((attend + late + leave - absence)- floor((late+leave)/3))/weekday*100, 2), ");
		sb.append("remainweekday = remainweekday + 1 ");
		sb.append("where name = ?");
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			
			ps.executeUpdate();
			
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
		
	}
	public void UpdateSubAbs(String name) {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set absence = absence - 1, ");
//		sb.append("appers = round(((attend + late + leave - absence)- floor((late+leave)/3))/weekday*100, 2), ");
		sb.append("remainweekday = remainweekday + 1 ");
		sb.append("where name = ?");
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			
			ps.executeUpdate();
			
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
		
	}
	public void UpdateSubLate(String name) {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set late = late - 1, ");
//		sb.append("appers = round(((attend + late + leave - absence)- floor((late+leave)/3))/weekday*100, 2), ");
		sb.append("remainweekday = remainweekday + 1 ");
		sb.append("where name = ?");
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			
			ps.executeUpdate();
			
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
		
	}
	public void UpdateSubLeave(String name) {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update attabs ");
		sb.append("set leave = leave - 1, ");
//		sb.append("appers = round(((attend + late + leave - absence)- floor((late+leave)/3))/weekday*100, 2), ");
		sb.append("remainweekday = remainweekday + 1 ");
		sb.append("where name = ?");
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, name);
			
			ps.executeUpdate();
			
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
		
	}
	
	

}


