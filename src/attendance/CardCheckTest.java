package attendance;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CardCheckTest extends JFrame implements ActionListener {

	JButton bt[] = new JButton[4];
	JLabel label;
	JTextField text, hour, minute, second;
//	JComboBox jcb[] = new JComboBox[6];
//	String[] str1 = {"2020", "2019", "2018", "2017", "2016"};
	JComboBox<String> year, month, day;
	ArrayList<String> yearlist, monthlist, daylist;
	JLabel lb[] = new JLabel[3];

	Calendar oCalendar = Calendar.getInstance(); // 현재 날짜/시간 등의 각종 정보 얻기

	public CardCheckTest() {
		// TODO Auto-generated constructor stub
		super("입/퇴실 Check");
		CreateUI();
	}

	public void CreateUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400, 300, 400, 280);
		setLayout(null);

		bt[0] = new JButton("입실");
		bt[1] = new JButton("퇴실");
		bt[2] = new JButton("DB");
		bt[3] = new JButton("취소");

		calendar();
		label = new JLabel("이름");

		label.setBounds(50, 110, 50, 40);

		text = new JTextField();
		text.setBounds(100, 110, 200, 40);

		for (int i = 0; i < bt.length; i++) {
			bt[i].setBounds(40 + i * 70, 180, 60, 40);

			bt[i].addActionListener(this);
			add(bt[i]);
		}

		add(label);
		add(year);
		add(hour);
		add(month);
		add(minute);
		add(day);
		add(second);
		add(text);

		setVisible(true);
	}

	public static void main(String[] args) {
		new CardCheckTest();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();

		if (ob == bt[0]) {
			enterCheck();

		} else if (ob == bt[1]) {
			leaveCheck();
			compareCheck();
		} else if (ob == bt[2]) {
			System.out.println("취소");
			new CheckTable().refreshCheck();
			dispose();
		} else if (ob == bt[3]) {
			System.out.println("취소");
			dispose();
		}

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
//		StringBuffer sb = new StringBuffer();

		String ho = hour.getText();
		String mi = minute.getText();
		String se = second.getText();
//		sb.append(hour.getText());
//		sb.append(minute.getText());
//		sb.append(second.getText());

		String ful = ho + ":" + mi + ":" + se;

		return ful;
	}

	public String dateTime() {
		String ye = (String) year.getSelectedItem();
		String mo = (String) month.getSelectedItem();
		String da = (String) day.getSelectedItem();

		String ful = ye + "/" + mo + "/" + da;

		return ful;

	}

	public String leaveTime() {
//		Date d = new Date();
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
//		String dt = date.format(d);

		String ho = hour.getText();
		String mi = minute.getText();
		String se = second.getText();
//		sb.append(hour.getText());
//		sb.append(minute.getText());
//		sb.append(second.getText());

		String ful = ho + ":" + mi + ":" + se;

		return ful;
	}

	public CheckInfo viewCheck() {
		CheckInfo info = new CheckInfo();

		String name = text.getText();
		String enter = enterTime();
		String leave = leaveTime();
		String dates = dateTime();

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

	private String addZeroString(int k) {
		String value = Integer.toString(k);
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;
	}

	public void calendar() {
		int toyear = oCalendar.get(Calendar.YEAR);
		int tomonth = oCalendar.get(Calendar.MONTH)+1;
		int today = oCalendar.get(Calendar.DAY_OF_MONTH);

		int ho = oCalendar.get(Calendar.HOUR_OF_DAY);
		int min = oCalendar.get(Calendar.MINUTE);
		int sec = oCalendar.get(Calendar.SECOND);

		yearlist = new ArrayList<String>();

		for (int i = toyear; i >= toyear - 25; i--) {
			yearlist.add(String.valueOf(i));
		}
		year = new JComboBox<String>(yearlist.toArray(new String[yearlist.size()]));
		year.setBounds(70, 20, 70, 30);
		year.setSelectedItem(String.valueOf(toyear));

		monthlist = new ArrayList<String>();

		for (int i = 1; i <= 12; i++) {
			monthlist.add(addZeroString(i));
		}
		month = new JComboBox<String>(monthlist.toArray(new String[monthlist.size()]));
		month.setBounds(150, 20, 70, 30);
		String mcom = tomonth >= 10 ? String.valueOf(tomonth) : "0" + tomonth;
		month.setSelectedItem(mcom);

		daylist = new ArrayList<String>();

		for (int i = 1; i <= 31; i++) {
			daylist.add(addZeroString(i));
		}
		day = new JComboBox<String>(daylist.toArray(new String[daylist.size()]));
		day.setBounds(230, 20, 70, 30);
		String dcom = today >= 10 ? String.valueOf(today) : "0" + today;
		day.setSelectedItem(dcom);

		hour = new JTextField();
		String h = ho >= 10 ? String.valueOf(ho) : "0" + ho;
		minute = new JTextField();
		String m = min >= 10 ? String.valueOf(min) : "0" + min;
		second = new JTextField();
		String s = sec >= 10 ? String.valueOf(sec) : "0" + sec;

		hour.setHorizontalAlignment(JTextField.RIGHT);
		minute.setHorizontalAlignment(JTextField.RIGHT);
		second.setHorizontalAlignment(JTextField.RIGHT);

		hour.setBounds(75, 60, 40, 30);
		minute.setBounds(155, 60, 40, 30);
		second.setBounds(235, 60, 40, 30);

		lb[0] = new JLabel("시");
		lb[1] = new JLabel("분");
		lb[2] = new JLabel("초");
		for (int i = 0; i < lb.length; i++) {
			lb[i].setBounds(120 + (i * 80), 60, 30, 30);
			add(lb[i]);
		}

		hour.setText("" + h);
		minute.setText("" + m);
		second.setText("" + s);

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
//				Object ob = enter.get(i);
//				String en = (String) ob;
//				System.out.println(en);
			Date en_t = date.parse(enter);
//				Object ob1 = leave.get(i);
//				String le = (String) ob1;
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
					System.out.println("출석!");
				} else if (le_t.getTime() < att2_t.getTime() && le_t.getTime() >= att1_t.getTime()) {
					aad.UpdateLeaveCount(text.getText());
					System.out.println("조퇴!");
				} else if (le_t.getTime() < att1_t.getTime()) {
					aad.UpdateAbsenceCount(text.getText());
					System.out.println("결석!");
				}
			} else if (en_t.getTime() >= att_t.getTime() && en_t.getTime() < att1_t.getTime()) {
				if (le_t.getTime() >= att2_t.getTime()) {
					aad.UpdateLateCount(text.getText());
					System.out.println("지각!");
				} else if (le_t.getTime() >= ba_t.getTime()) {
					aad.UpdateLateCount(text.getText());
					aad.UpdateLeaveCount(text.getText());
					System.out.println("지각 & 조퇴!");
				} else if (le_t.getTime() < ba_t.getTime()) {
					aad.UpdateAbsenceCount(text.getText());
					System.out.println("결석!");
				}

			} else if (en_t.getTime() >= att1_t.getTime()) {
				aad.UpdateAbsenceCount(text.getText());
				System.out.println("결석!");
			}
			

		} catch (ParseException e) {
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
	public String nameCheck() {
		String name = text.getText();
		
		return name;
	}

}
