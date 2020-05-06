package attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// 로그인 여부 확인

public class ManagerLogin extends JFrame implements ActionListener {

	JButton jbt1, jbt2;
	JLabel jlb1, jlb2, lab;
	JTextField jtf1;
	JPasswordField jpf;
	ImageIcon icon, ic;

	public ManagerLogin() {
		// TODO Auto-generated constructor stub
		super("java login test");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(1100, 300, 400, 300);
		setLayout(null);
		icon = new ImageIcon("src/images/login.jpg");
		Image icon1 = icon.getImage();
		Image icon2 = icon1.getScaledInstance(100, 90, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(icon2);
		ic = new ImageIcon("src/images/goott2.jpeg");
		Image icon3 = ic.getImage();
		Image icon4 = icon3.getScaledInstance(130, 80, Image.SCALE_SMOOTH);
		ImageIcon ic = new ImageIcon(icon4);

		jbt1 = new JButton(icon);
		jbt2 = new JButton("문의하기");
//		jbt1.setBorderPainted(false);
		jbt1.setContentAreaFilled(false);
		jbt1.setFocusPainted(false);
		jbt1.setOpaque(false);

		jbt1.setBounds(250, 100, 100, 90);
		jbt2.setBounds(250, 200, 100, 30);

		add(jbt1);
		add(jbt2);

		jlb1 = new JLabel("I D");
		jlb2 = new JLabel("P W");
		lab = new JLabel(ic);

		jlb1.setBounds(30, 100, 70, 40);
		jlb2.setBounds(30, 150, 70, 40);
		lab.setBounds(130, 10, 130, 80);

		add(jlb1);
		add(jlb2);
		add(lab);

		jtf1 = new JTextField(30);
		jpf = new JPasswordField(30);

		jtf1.setBounds(80, 100, 150, 40);
		jpf.setBounds(80, 150, 150, 40);

		add(jtf1);
		add(jpf);

		jbt1.addActionListener(this);
		jbt2.addActionListener(this);

		setVisible(true);
	}

//	public static void main(String[] args) {
//		new ManagerLogin();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if (ob == jbt1) {  
			// dbms에 접근해서 id, pw가 일치하는지 확인(비교)
			// System.out.println("click");

			String id = jtf1.getText();
			String pw = jpf.getText();

			// System.out.println("id : " + id + ", pw : " + pw);

			// dbms에 접근해서 id, pw가 일치하는지 확인(비교)
			Connection conn = MakeConn.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append("select * from managerlogin ");
			sb.append("where id = ? and pw = ? ");

			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, id);
				pstmt.setString(2, pw);

				rs = pstmt.executeQuery();

				// 간단한 회원여부 check
				if (rs.next()) {
					new PersonnelManagement();
					dispose();
					System.out.println(rs.getString(3) + "님 어서오세요");

				} else {
					JOptionPane.showMessageDialog(this, "아이디 혹은 비밀번호가 틀렸습니다.\n관리자에게 문의하세요.");
					System.out.println("아이디 혹은 비밀번호가 틀렸습니다.\n관리자에게 문의하세요.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (ob == jbt2) {
			String name = "";
			new Inquiry(name);
			

		}

	}

	

}
