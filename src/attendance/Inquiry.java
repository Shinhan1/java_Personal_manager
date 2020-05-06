package attendance;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Inquiry extends JFrame implements ActionListener{
	JTextArea text;
	JButton bt1, bt2;
	JLabel label;
	String iq;
	String nam;
	public Inquiry() {
		UI();
	}
	public Inquiry(String name) {
		bt1.setEnabled(false);
		bt1.setVisible(false);
		UI();
		
	}
	
	public void UI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400, 100, 400, 300);
		setLayout(null);
		
		text = new JTextArea();
		label = new JLabel("문의");
		bt1 = new JButton("문의");
		bt2 = new JButton("취소");
		
		
		label.setFont(new Font("Gothic", Font.BOLD, 30));
		
		label.setBounds(150, 10, 100, 30);
		text.setBounds(40, 50, 300, 130);
		
		bt1.setBounds(70, 200, 100, 50);
		bt2.setBounds(200, 200, 100, 50);
		
		add(text); add(label); add(bt1); add(bt2);
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		
		
		setVisible(true);
	}
	public String setTextView() {
		String str = text.getText();
		
		return str;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		
		if(ob == bt1) {
			JOptionPane.showMessageDialog(this, "문의하였습니다.");
			System.out.println("문의");
			msgDB();
			dispose();
		}else if(ob == bt2) {
			dispose();
		}
		
	}
//	public int msgNumDB() {
//		Connection conn = MakeConn.getConnection();
//		StringBuffer sb = new StringBuffer();
//		int num = 0;
//		sb.append("select num from message ");
//		sb.append("where msg = ?");
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = conn.prepareStatement(sb.toString());
//			ps.setString(1, text.getText());
//			rs = ps.executeQuery();
//			
//			if(rs.next()) {
//				num = rs.getInt("num");
//				rs.getString("msg");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			try {
//				rs.close();
//				ps.close();
//				conn.close();
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return num;
//	}
	
	public void msgDB() {
		Connection conn = MakeConn.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("insert into message ");
		sb.append("values(msg_seq.nextval, ?) ");
		
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sb.toString());
			ps. setString(1, text.getText());
			
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
	public static void main(String[] args) {
		new Inquiry();
	}
	
	

}
