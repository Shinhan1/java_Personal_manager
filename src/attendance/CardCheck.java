package attendance;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sun.net.www.content.image.jpeg;

public class CardCheck extends JFrame implements ActionListener {

	JButton bt[] = new JButton[4];
	JLabel label, label1, label2;
	JTextField text, hour, minute, second;
//	JComboBox jcb[] = new JComboBox[6];
//	String[] str1 = {"2020", "2019", "2018", "2017", "2016"};
	JComboBox<String> year, month, day;
	ArrayList<String> yearlist, monthlist, daylist;
	JLabel lb[] = new JLabel[3];

	Calendar oCalendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기

	public CardCheck() {
		// TODO Auto-generated constructor stub
		super("입/퇴실 Check");
		CreateUI();
	}

	public void CreateUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400, 300, 400, 300);
		setLayout(null);

		bt[0] = new JButton("입실");
		bt[1] = new JButton("퇴실");
		bt[2] = new JButton("DB");
		bt[3] = new JButton("취소");

		label = new JLabel("이름");
		label1 = new JLabel();
		label2 = new JLabel("현재 시각");

		label1.setFont(new Font("Gothic", Font.BOLD, 30));
		label2.setFont(new Font("Gothic", Font.BOLD, 20));
		label1.setBounds(50, 60, 350, 40);
		label2.setBounds(150, 20, 350, 40);
		add(label1);
		add(label2);

		CardCheckThread tt = new CardCheckThread(label1);
		Thread te = new Thread(tt);

		te.start();

		label.setBounds(50, 130, 50, 40);

		text = new JTextField();
		text.setBounds(100, 130, 200, 40);

		for (int i = 0; i < bt.length; i++) {
			bt[i].setBounds(40 + i * 70, 200, 60, 40);

			bt[i].addActionListener(this);
			add(bt[i]);
		}

		add(label);
		add(text);

		setVisible(true);
	}

	public static void main(String[] args) {
		new CardCheck();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		CardCheckDB cc = new CardCheckDB();

		if (ob == bt[0]) {
			CheckInfo();

		} else if (ob == bt[1]) {
			leaveCheck();
			try {
				compareCheck();
			} catch (NullPointerException e3) {
				System.out.println("");
			}

		} else if (ob == bt[2]) {
			new CheckTable().refreshCheck();
			System.out.println(cc.enterCheck());
			System.out.println(cc.leaveCheck());

			dispose();
		} else if (ob == bt[3]) {
			System.out.println("취소");
			System.exit(0);
		}

	}

	public void compareCheck() {
		CardCheckDB ccd = new CardCheckDB();
		AttendAbsenceDB aad = new AttendAbsenceDB();

		String enter, leave;
		

		enter = ccd.enterCheck();
		leave = ccd.leaveCheck();
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String att = "09:40:00";
		String att1 = "14:30:00";
		String att2 = "18:20:00";

		try {
			Date att_t = date.parse(att);
			Date att1_t = date.parse(att1);
			Date att2_t = date.parse(att2);

//			for (int i = 0; i < enter.size(); i++) {
//			Object ob = enter.get(enter.size());
//			String en = (String) ob;
//			System.out.println(en);

			Date en_t = date.parse(enter);
//			Object ob1 = leave.get(enter.size());
//			String le = (String) ob1;
//				System.out.println(le);
			Date le_t = date.parse(leave);
			Calendar cal = Calendar.getInstance();
			Date ba_5 = date.parse(enter);
			cal.setTime(ba_5);
			cal.add(Calendar.HOUR, 5); // 5시간 더하기
			Date ba_t = cal.getTime();
//				System.out.println(ba_t.getTime());

			if (en_t.getTime() < att_t.getTime()) {
				if (le_t.getTime() >= att2_t.getTime()) {
					aad.UpdateAttendCount(text.getText());
//					JOptionPane.showMessageDialog(this, "출석입니다.");
					System.out.println("출석!");
				} else if (le_t.getTime() < att2_t.getTime() && le_t.getTime() >= att1_t.getTime()) {
					aad.UpdateLeaveCount(text.getText());
//					JOptionPane.showMessageDialog(this, "조퇴입니다.");
					System.out.println("조퇴!");
				} else if (le_t.getTime() < att1_t.getTime()) {
					aad.UpdateAbsenceCount(text.getText());
//					JOptionPane.showMessageDialog(this, "결석입니다.");
					System.out.println("결석!");
				} else {
					aad.UpdateAbsenceCount(text.getText());
				}
			} else if (en_t.getTime() >= att_t.getTime() && en_t.getTime() < att1_t.getTime()) {
				if (le_t.getTime() >= att2_t.getTime()) {
					aad.UpdateLateCount(text.getText());
//					JOptionPane.showMessageDialog(this, "지각입니다.");
					System.out.println("지각!");
				} else if (le_t.getTime() >= ba_t.getTime()) {
					aad.UpdateLateCount(text.getText());
					aad.UpdateLeaveCount(text.getText());
//					JOptionPane.showMessageDialog(this, "지각, 조퇴입니다.");
					System.out.println("지각 & 조퇴!");
				} else if (le_t.getTime() < ba_t.getTime()) {
					aad.UpdateAbsenceCount(text.getText());
//					JOptionPane.showMessageDialog(this, "결석입니다.");
					System.out.println("결석!");
				}
			} else if (en_t.getTime() >= att1_t.getTime()) {
				aad.UpdateAbsenceCount(text.getText());
//				JOptionPane.showMessageDialog(this, "결석입니다.");
				System.out.println("결석!");
			} else if (en_t == null) { 
				System.out.println("실패");
			}

		} catch (

		ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e1) {
			System.out.println("아직 퇴실하지 않으셨습니다.\n퇴실해주세요.");
		}
		String name = text.getText();
		
		aad.UpdateApper(name);
		aad.UpdateRemain(name);
		
		aad.UpdateAcasal(name);
		aad.UpdateEmpsal(name);
		aad.UpdateTosal(name);

	}

	public String Date() {
		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
		String dt = date.format(d);

		return dt;
	}

	public String enterTime() {
		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String dt = date.format(d);

		return dt;
	}

	public String leaveTime() {
		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String dt = date.format(d);

		return dt;
	}

	public CheckInfo CheckInfo() {
		CheckInfo info = new CheckInfo();
		CardCheckDB ccd = new CardCheckDB();
		Vector v = ccd.insertInfo();
		String name = text.getText();
		CheckInfo ci = new CheckInfo();

		if (v.contains(name)) {
			System.out.println("존재하는 회원");
			JOptionPane.showMessageDialog(this, "존재하는 회원입니다.");
			enterCheck();

		} else {
			System.out.println("존재하지않는 회원");
			JOptionPane.showMessageDialog(this, "존재하지 않는 회원입니다.\n관리자에게 문의하세요.");

		}

		return ci;
	}

	public CheckInfo viewCheck() {
		CheckInfo info = new CheckInfo();
		CardCheckDB ccd = new CardCheckDB();
//		CheckInfo();
		String name = text.getText();
		String enter = enterTime();
		String leave = leaveTime();
		String dates = Date();

		info.setName(name);
		info.setEnter(enter);
		info.setLeave(leave);
		info.setDates(dates);

		return info;
	}

	/* 입실 체크 */
	public void enterCheck() {
		CheckInfo ci = viewCheck();
		CardCheckDB check = new CardCheckDB();

		boolean ok = check.enterCard(ci);
		if (ok)
			JOptionPane.showMessageDialog(this, "입실하셨습니다.");
		else
			JOptionPane.showMessageDialog(this, "입실에 실패하셨습니다.");

	}

	/* 퇴실 체크 */
	public void leaveCheck() {
		CheckInfo ci = viewCheck();

		CardCheckDB check = new CardCheckDB();

		boolean ok = check.leaveCard(ci);
		if (ok)
			JOptionPane.showMessageDialog(this, "퇴실하셨습니다.");
		else
			JOptionPane.showMessageDialog(this, "퇴실에 실패하셨습니다.\n입실해주세요");

	}
	
}
