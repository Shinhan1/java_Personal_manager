package attendance;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

// 메인 DB확인
public class PersonnelManagement extends JFrame implements ActionListener {

	JButton jb[] = new JButton[5];
	JButton bt = new JButton("검색");
	JButton bt1 = new JButton("취소");
	JButton bt2 = new JButton("전체 입실");
	JButton bt3 = new JButton("쪽지함");
	JTable table, table1;
	JScrollPane scroll1, scroll2;
	Container panel = getContentPane();
	Container pn = getContentPane();
	JPanel pan = new JPanel();
	JLabel label[] = new JLabel[3];
	JLabel lab = new JLabel("검색");
	ImageIcon icon = new ImageIcon("src/images/goott.jpeg");
	JTextField text = new JTextField();
	Vector emp, empinfo, selectVec, AttAbs, AttAbsInfo;
	AttendeeDB aDB = new AttendeeDB();
	AttendAbsenceDB aaDB = new AttendAbsenceDB();


	public PersonnelManagement() {
		// TODO Auto-generated constructor stub
		super("Personnel Management");
		ManagerUI();

	}

	public void ManagerUI() { // GUI 생성
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(400, 100, 1100, 800);
		setLayout(null);
		empinfo = aDB.getAttendeeList(); // 수강생 정보
		emp = getEmp(); // 수강생 정보
		
		AttAbsInfo = aaDB.getAttAbsList();
		AttAbs = getAttAbs();

		DefaultTableModel model = new DefaultTableModel(empinfo, emp);
		DefaultTableModel model1 = new DefaultTableModel(AttAbsInfo, AttAbs);

		table = new JTable(model); // 수강생 정보
		table1 = new JTable(model1); // 출결 정보
		
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
		
		table1.getColumn("번호").setWidth(0);
		table1.getColumn("번호").setMinWidth(0);
		table1.getColumn("번호").setMaxWidth(0);
		

		scroll1 = new JScrollPane(table); // 수강생 테이블
		scroll2 = new JScrollPane(table1); // 출결 테이블

		scroll1.setBounds(40, 230, 1000, 100); // 수강생 테이블
		scroll2.setBounds(40, 480, 1000, 100); // 출결 테이블

		text.setBounds(100, 340, 760, 30);
		add(text); // 검색 텍스트
		bt.setBounds(870, 340, 80, 30);
		add(bt); // 검색 버튼
		bt1.setBounds(960, 340, 80, 30);
		add(bt1); // 취소 버튼 (검색 후 다시 되돌리는 버튼)
		lab.setBounds(50, 340, 50, 30);
		add(lab); // 검색 라벨
		bt2.setBounds(380, 660, 100, 50);
		add(bt2);
		bt3.setBounds(920, 30, 100, 50);
		add(bt3);

		label[0] = new JLabel("인사 정보"); // 인사 정보 라벨
		label[1] = new JLabel("출결 관리"); // 출결 관리 라벨
		label[2] = new JLabel(icon); // goott 이미지 라벨

		label[0].setBounds(20, 180, 100, 40); // 인사 정보 라벨
		label[1].setBounds(20, 430, 100, 40); // 출결 관리 라벨
		label[2].setBounds(370, 10, 350, 150); // goott 이미지 라벨

		for (int i = 0; i < label.length; i++) { // 라벨 컨테이너에 추가
			add(label[i]);
		}

		jb[0] = new JButton("입실 관리"); // 출결 관리 버튼
		jb[1] = new JButton("출결 관리"); // 추가 버튼
		jb[2] = new JButton("추가"); // 수정 버튼
		jb[3] = new JButton("수정"); // 취소 버튼
		jb[4] = new JButton("취소");

		panel.add(scroll1); // 패널에 수강생 정보 테이블 생성
		panel.add(scroll2); // 패널에 출결 관리 정보 테이블 생성

		for (int i = 0; i < jb.length; i++) { // 버튼에 대한 for문
			jb[i].setBounds((380 + (i + 1) * 110), 660, 100, 50); // 버튼 위치
			pn.add("South", jb[i]); // 버튼을 아래쪽 패널에 생성
			jb[i].addActionListener(this); // 버튼을 누르면 이벤트

		}

		bt.addActionListener(this); // 검색 버튼 이벤트
		bt1.addActionListener(this); // 취소 버튼 이벤트 (검색 후 다시 되돌아오는 버튼)
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		new PersonnelManagement();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		String named = null;
//		AttendAbsenceDB aad = new AttendAbsenceDB();
		

		if (ob == jb[0]) { // 선택 입실 관리 버튼 이벤트
			try {
				System.out.println("입실 관리");
				int r = table.getSelectedRow();
				String name = (String)table.getValueAt(r, 1);
				new CheckTable(name).selectList(name);
			} catch(ArrayIndexOutOfBoundsException e3) {
				JOptionPane.showMessageDialog(null, "대상을 선택하세요.");
			}
			
		} else if (ob == jb[1]) { // 출결관리 버튼 이벤트
			refreshAA();
			System.out.println("출결 관리");
		} else if (ob == jb[2]) { // 추가 버튼 이벤트
			new Registration(); // Registration 클래스 호출
			dispose();
			

		} else if (ob == jb[3]) { // 수정 버튼 이벤트
			try { // 예외처리
				int r = table.getSelectedRow(); // JTable 중 Row하나를 선택
				System.out.println(r); // 확인을 위한 출력
				int name = (int) table.getValueAt(r, 0); // Jtable의 r번째 0번을 String형으로 name에 저장
				// System.out.println(name); // 확인을 위한 name 출력
				System.out.println(name);
				new Registration(name, this); // Registration 생성자 오버로딩 호출
				dispose(); // exit
			} catch (ArrayIndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "수정할 대상을 선택하세요");
			} catch (NullPointerException e2) {
				System.out.println();
				dispose();
			}

		} else if (ob == jb[4]) {	// 취소 버튼
			System.out.println("취소");
			System.exit(0);

		} else if (ob == bt) { // 검색 버튼 이벤트
			refreshSelect();
		} else if (ob == bt1) { // 취소 버튼 이벤트 (검색 후 다시 되돌리기 버튼)
			refreshInfo();
		} else if (ob == bt2) {
			new CheckTable(named);
		} else if (ob == bt3) {
			new Inquiry();
		}

	}

	public Vector getEmp() { // JTable의 컬럼명을 메소드로 선언
		Vector col = new Vector(); // JTable에 쉽게 입력하기 위해 Vector 선언
		col.add("번호");
		col.add("이름");
		col.add("생년월일");
		col.add("직급");
		col.add("성별");
		col.add("주소");
		col.add("전화번호");
		col.add("이메일");
		col.add("등록일");
		col.add("소개");

		return col; // Vector col 리턴
	}
	
	public Vector getAttAbs() {
		Vector col = new Vector();
		col.add("번호");
		col.add("이름");
		col.add("출석");
		col.add("결석");
		col.add("지각");
		col.add("조퇴");
		col.add("외출");
		col.add("출결");
		col.add("학원 월급");
		col.add("고용부 월급");
		col.add("총 월급");
		col.add("평일 일수");
		col.add("남은 일수");
		
		return col;
	}

	public void refreshInfo() { // 새로운 정보를 출력
		AttendeeDB ad = new AttendeeDB();
		DefaultTableModel model = new DefaultTableModel(
				ad.getAttendeeList(), getEmp());
		table.setModel(model);
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
	}

	public void refreshSelect() { // 검색한 정보를 출력
		AttendeeDB ad = new AttendeeDB();
		AttendeeInformation ai = new AttendeeInformation();
		DefaultTableModel model = new DefaultTableModel(
				ad.selectAttendee(text.getText(), ai.getNum()), getEmp());
		table.setModel(model);
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
	}
	
	public void refreshAA() {	// 출결관리 테이블 새로고침
		AttendAbsenceDB aad = new AttendAbsenceDB();
//		aad.UpdateAttendCount();
		
		DefaultTableModel model = new DefaultTableModel(aad.getAttAbsList(), getAttAbs());
		table1.setModel(model);
		table1.getColumn("번호").setWidth(0);
		table1.getColumn("번호").setMinWidth(0);
		table1.getColumn("번호").setMaxWidth(0);
		
	}
	
	

	

}
