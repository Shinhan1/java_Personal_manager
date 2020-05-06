package attendance;

import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

// 등록 창
public class Registration extends JFrame implements ActionListener {
	JButton bt1, bt2, bt3, bt4, bt5;
	JLabel lb[] = new JLabel[10];
	JTextField tf[] = new JTextField[9];
	ImageIcon icon1, icon2;
	JLabel il;
	JFileChooser choice = new JFileChooser();
	Container contentPane;
	File savefile;
	String str;
	JRadioButton jrbs[] = new JRadioButton[2];
	String str1[] = { "남", "여" };
	String str2[] = { "사장", "부장", "과장", "대리", "사원", "인턴", "학생" };
	JComboBox<String> jcb = new JComboBox<String>(str2);
	int num1;

	public Registration() {
		// TODO Auto-generated constructor stub
		super("Attendace And Absense");
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		RegisterUI();
		bt4.setEnabled(false);
		bt4.setVisible(false);
		bt5.setEnabled(false);
		bt5.setVisible(false);

	}

	public Registration(int name, PersonnelManagement pm) throws NullPointerException {
		RegisterUI();
		bt1.setEnabled(false);
		bt1.setVisible(false);

		AttendeeDB ad = new AttendeeDB();
		AttendeeInformation ai = ad.AttendeeInfo(name);
		System.out.println(ai);
		viewData(ai);
		
		this.num1 = name;	

	}

	public void RegisterUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(700, 300, 500, 700);
		setLayout(null);
		contentPane = getContentPane();
		il = new JLabel();


		lb[0] = new JLabel("등록"); // 등록 라벨

		icon1 = new ImageIcon("src/images/person.png"); // 이미지 넣기

		JPanel Button = new JPanel();
		bt1 = new JButton("등록");
		bt2 = new JButton("취소");
		bt3 = new JButton("Person", icon1); // 버튼 생성
		bt4 = new JButton("수정");
		bt5 = new JButton("삭제");
		Button.add(bt1);
		Button.add(bt2);
		Button.add(bt4);
		Button.add(bt5);

		bt1.setBounds(70, 580, 100, 50);
		bt2.setBounds(310, 580, 100, 50);
		bt3.setBounds(280, 80, 170, 190); // 버튼 위치
		jcb.setBounds(100, 180, 170, 40);
		bt4.setBounds(70, 580, 100, 50);
		bt5.setBounds(190, 580, 100, 50);

		lb[0].setBounds(200, 10, 120, 50);

		lb[0].setFont(new Font("고딕", Font.BOLD, 40)); // 폰트

		for (int i = 0; i < 2; i++) {
			tf[i] = new JTextField();
			tf[i].setBounds(100, 30 + ((i + 1) * 50), 170, 40);
			add(tf[i]);
		}

		for (int i = 4; i < tf.length; i++) {
			tf[i] = new JTextField();
			tf[i].setBounds(100, 30 + ((i + 1) * 50), 350, 40);
			add(tf[i]);

		}
		lb[1] = new JLabel("이름");
		lb[2] = new JLabel("생년월일");
		lb[3] = new JLabel("직급");
		lb[4] = new JLabel("성별");
		lb[5] = new JLabel("주소");
		lb[6] = new JLabel("전화번호");
		lb[7] = new JLabel("이메일");
		lb[8] = new JLabel("등록 날짜");
		lb[9] = new JLabel("소개");

		ButtonGroup group = new ButtonGroup();

		for (int i = 0; i < 2; i++) {
			jrbs[i] = new JRadioButton(str1[i]);
			jrbs[i].setOpaque(false);
			group.add(jrbs[i]);
			jrbs[i].setBounds(50 + (i + 1) * 50, 230, 50, 40);

			add(jrbs[i]);

		}

		for (int i = 1; i < lb.length; i++) {
			lb[i].setBounds(20, 30 + (i * 50), 70, 40);
			add(lb[i]);
		}
		jrbs[0].setSelected(true);

		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);

		add(lb[0]);
		add(bt1);
		add(bt2);
		add(bt4);
		add(bt3);
		add(bt5);
		contentPane.add(il);
		add(jcb);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();

		if (ob == bt1) {				// 등록 버튼
			String name = tf[0].getText();
			if (tf[0].getText() != null) {

				insertAttendee();
				Attend();
				dispose();

			} else {
				JOptionPane.showMessageDialog(this, "이름은 꼭 입력해주세요.");
				return;

			}

		} else if (ob == bt2) {			// 취소 버튼
			System.out.println("취소");
			dispose();

		} else if (ob == bt3) {			// 이미지 버튼
			imageshow();

		} else if (ob == bt4) {			// 수정 버튼
			updateAttendee();
			dispose();
		} else if (ob == bt5) {			// 삭제 버튼
			int delete = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			if (delete == JOptionPane.OK_OPTION)
				deleteAttendee();
			else
				JOptionPane.showMessageDialog(this, "취소하셨습니다.");
			dispose();
		}
		new PersonnelManagement().refreshInfo();

	}

	private void imageshow() {
		choice.setCurrentDirectory(new File("C:\\Users\\goott7-15\\Pictures\\Saved Pictures"));

		int value = choice.showOpenDialog(null);
		if (value != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았으셨습니다.", "주의", JOptionPane.WARNING_MESSAGE);
			return;
		} else {
			savefile = choice.getSelectedFile();
			str = savefile.toString();
			bt3.setIcon(new ImageIcon(str));

		}

	}

	private void viewData(AttendeeInformation atInfo) throws NullPointerException {

		int num = atInfo.getNum();
		String name = atInfo.getName();
		String birth = atInfo.getBirth();
		String job = atInfo.getJob();
		String gen = atInfo.getGender();
		String addr = atInfo.getAddr();
		String phnum = atInfo.getPhnum();
		String email = atInfo.getEmail();
		String date = atInfo.getDate();
		String intro = atInfo.getIntro();

		tf[0].setText(name);
		tf[1].setText(birth);
		jcb.setSelectedItem(job);
		if (gen.equals("남")) {
			jrbs[0].setSelected(true);
		} else if (gen.equals("여")) {
			jrbs[1].setSelected(true);
		}

		tf[4].setText(addr);
		tf[5].setText(phnum);
		tf[6].setText(email);
		tf[7].setText(date);
		tf[8].setText(intro);

	} // 화면에 출력

	private void insertAttendee() { // 추가

		AttendeeInformation ai = getViewData();
		AttendeeDB ad = new AttendeeDB();

		boolean ok = ad.addAttendee(ai);
		if (ok) {
			JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");

		} else {
			JOptionPane.showMessageDialog(this, "가입이 정상적으로 처리되지 않았습니다.");
		}

	}

	private void updateAttendee() { // 수정

		AttendeeInformation ai = getViewData();
		AttendeeDB ad = new AttendeeDB();
		boolean ok = ad.updateAttendee(ai);

		if (ok) {
			JOptionPane.showMessageDialog(this, "수정이 완료되었습니다.");
		} else
			JOptionPane.showMessageDialog(this, "수정이 완료되지 않았습니다.\n값을 확인하세요.");

	}

	private void deleteAttendee() {
		
		AttendeeInformation ai = getViewData();
		AttendeeDB ad = new AttendeeDB();
		boolean ok = ad.deleteAttendee(ai);

		if (ok) {
			JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.");
		} else
			JOptionPane.showMessageDialog(this, "삭제가 실패하였습니다.");

	}

	public AttendeeInformation getViewData() {
		AttendeeInformation ai = new AttendeeInformation();
		AttendeeDB ad = new AttendeeDB();
		
		System.out.println(num1);
		
		
		String name = tf[0].getText();
		String birth = tf[1].getText();
		String job = (String) jcb.getSelectedItem();
		String addr = tf[4].getText();
		String phnum = tf[5].getText();
		String email = tf[6].getText();
		String date = tf[7].getText();
		String intro = tf[8].getText();
		String gender = "";
		if (jrbs[0].isSelected()) {
			gender = "남";
		} else if (jrbs[1].isSelected()) {
			gender = "여";
		}

		ai.setNum(num1);
		ai.setName(name);
		ai.setBirth(birth);
		ai.setJob(job);
		ai.setGender(gender);
		ai.setAddr(addr);
		ai.setPhnum(phnum);
		ai.setEmail(email);
		ai.setDate(date);
		ai.setIntro(intro);

		return ai;
	}
	
	public void Attend() {
		AttendAbsenceInfo aai = attendView();
		AttendAbsenceDB aad = new AttendAbsenceDB();
		
		boolean ok = aad.Attend(aai);
		
		
		if(ok) JOptionPane.showMessageDialog(this, "출결 등록 성공");
		else JOptionPane.showMessageDialog(this, "출결 등록 실패");
	}
	
	public AttendAbsenceInfo attendView() {
		
		AttendAbsenceInfo aai = new AttendAbsenceInfo();
		
		String name = tf[0].getText();
		
		aai.setName(name);
		
		return aai;
	}

}
