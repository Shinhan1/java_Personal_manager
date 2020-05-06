package attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Vector;

import javax.swing.JOptionPane;

// 수강생 DB

public class AttendeeDB {

	public AttendeeInformation AttendeeInfo(int num) { // 수강생 정보 출력
		AttendeeInformation info = new AttendeeInformation();		// AttendeeInformation형 info 선언

		Connection conn = null;			// 연결

		conn = MakeConn.getConnection();		
		StringBuffer sb = new StringBuffer();
		sb.append("select * from GT_Member ");			// SQL문
		sb.append("where num = ? ");
		PreparedStatement ps = null;
		ResultSet rs = null;

		
		try {
			ps = conn.prepareStatement(sb.toString());	// SQL문 전달
			ps.setInt(1, num);		// 첫번째 ? 위치에 name 전달

			rs = ps.executeQuery();		// 실행

			if (rs.next()) {			// rs문
				info.setNum(rs.getInt("num"));
				info.setName(rs.getString("name"));				// AttendeeInformation에 setter로 데이터베이스에 있는 컬럼들 전달
				info.setBirth(rs.getString("birth"));
				info.setJob(rs.getString("job"));
				info.setGender(rs.getString("gender"));
				info.setAddr(rs.getString("addr"));
				info.setPhnum(rs.getString("phnum"));
				info.setEmail(rs.getString("email"));
				info.setDate(rs.getString("dat"));
				info.setIntro(rs.getString("intro"));			// AttendeeInformation에 setter로 데이터베이스에 있는 컬럼들 전달

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

	// Jtable에 넣기 위한 vector 선언
	public Vector getAttendeeList() {

		Vector data = new Vector();

		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;

		sb.append("select * from GT_Member ");			// select 문
		sb.append("order by num asc");

		try {
			ps = con.prepareStatement(sb.toString());
			rs = ps.executeQuery(); // 실행

			while (rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String birth = rs.getString("birth");
				String job = rs.getString("job");
				String gender = rs.getString("gender");
				String addr = rs.getString("addr");
				String phnum = rs.getString("phnum");
				String email = rs.getString("email");
				String date = rs.getString("dat");
				String intro = rs.getString("intro");

				Vector row = new Vector(); // jtable에 저장하기 위해 Vector를 사용
				row.add(num);
				row.add(name);
				row.add(birth);
				row.add(job);
				row.add(gender);
				row.add(addr);
				row.add(phnum);
				row.add(email);
				row.add(date);
				row.add(intro);

				data.add(row);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
		

	}

	/* 회원 등록 */
	public boolean addAttendee(AttendeeInformation info) {
		boolean ok = false;
		int save;

		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("insert into GT_Member( ");
		sb.append("num, name, birth, job, gender, addr, phnum, ");
		sb.append("email, dat, intro) ");
		sb.append("values(pm_memno_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, info.getName());
			ps.setString(2, info.getBirth());
			ps.setString(3, info.getJob());
			ps.setString(4, info.getGender());
			ps.setString(5, info.getAddr());
			ps.setString(6, info.getPhnum());
			ps.setString(7, info.getEmail());
			ps.setString(8, info.getDate());
			ps.setString(9, info.getIntro());
			
			
			

			save = ps.executeUpdate();

			if (save > 0) {
				System.out.println("가입 성공");
				ok = true;

			} else {
				System.out.println("가입 실패");
			}

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

	/* 회원 수정 */
	public boolean updateAttendee(AttendeeInformation ai) {
		boolean ok = false;

		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update GT_member ");
		sb.append("set name = ?, birth = ?, job = ?, gender = ?, ");
		sb.append("addr = ?, phnum = ?, email = ?, dat = ?, intro = ? ");
		sb.append("where num = ? ");
		/*
		 * SQL> update gt_member
  			2  set name = '안녕', birth = '123', job = 'asd', gender = '500', addr = '가산동', phnum = '1234', email = 'tlsgks', dat = '2019', intro = '안녕'
  			3  where name = 'asd';
		 */
		
		PreparedStatement ps = null;

		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, ai.getName());
			ps.setString(2, ai.getBirth());
			ps.setString(3, ai.getJob());
			ps.setString(4, ai.getGender());
			ps.setString(5, ai.getAddr());
			ps.setString(6, ai.getPhnum());
			ps.setString(7, ai.getEmail());
			ps.setString(8, ai.getDate());
			ps.setString(9, ai.getIntro());
			ps.setInt(10, ai.getNum());

			int save = ps.executeUpdate(); // 실행 -> 수정
			// 1 ~ n : 성공, 0 : 실패
			//System.out.println(ps);
			//System.out.println(save);

			if (save > 0) ok = true;

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
	/* 회원 삭제 */
	public boolean deleteAttendee(AttendeeInformation ai) {
		boolean ok = false;
		deleteAttAbs();
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();

		sb.append("delete from GT_Member ");
		sb.append("where num = ?");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, ai.getNum());
			int save = ps.executeUpdate();
			if (save > 0) ok = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("오류가 발생하였습니다.");
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
	
	/* gt_member 삭제 시 attabs 테이블의 같은 사람 정보 삭제 */
	public void deleteAttAbs() {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();

		sb.append("delete from attabs ");
		sb.append("where name in (select name ");
		sb.append("from gt_member) ");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sb.toString());
			int save = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("오류가 발생하였습니다.");
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
	
	/* 회원 검색 */
	public Vector selectAttendee(String select, int n) {
		Vector data = new Vector();
		
		Connection con = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from gt_member ");
		sb.append("where num = ? or name = ? or birth = ? or job = ? or gender = ? or ");
		sb.append("addr = ? or phnum = ? or email = ? or dat = ? or intro = ? ");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, n);
			ps.setString(2, select);
			ps.setString(3, select);
			ps.setString(4, select);
			ps.setString(5, select);
			ps.setString(6, select);
			ps.setString(7, select);
			ps.setString(8, select);
			ps.setString(9, select);
			ps.setString(10, select);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String birth = rs.getString("birth");
				String job = rs.getString("job");
				String gender = rs.getString("gender");
				String addr = rs.getString("addr");
				String phnum = rs.getString("phnum");
				String email = rs.getString("email");
				String dat = rs.getString("dat");
				String intro = rs.getString("intro");
				
				Vector vec = new Vector();
				vec.add(num);
				vec.add(name);
				vec.add(birth);
				vec.add(job);
				vec.add(gender);
				vec.add(addr);
				vec.add(phnum);
				vec.add(email);
				vec.add(dat);
				vec.add(intro);
				
				data.add(vec);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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