package attendance;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckTable extends JFrame implements ActionListener {
	
	JTable table;
	JScrollPane scroll;
	Vector vc, v;
	CardCheckDB checkDB = new CardCheckDB();
	Container panel = getContentPane();
	JButton bt[] = new JButton[3];
	JButton bt1;
	ImageIcon icon;
	
	public CheckTable() {
		// TODO Auto-generated constructor stub
		checkUI();
		bt[1].setEnabled(false);
		bt[1].setVisible(false);
	}
	public CheckTable(String name) {
		checkUI();
		bt[0].setEnabled(false);
		bt[0].setVisible(false);
		
	}
	
	public void checkUI() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(400, 100, 600, 300);
		setLayout(null);
		
		vc = checkDB.checkList();
		v = column();
		DefaultTableModel model = new DefaultTableModel(vc, v);
		
		bt[0] = new JButton("관리자");
		bt[1] = new JButton("삭제"); 
		bt[2] = new JButton("취소"); 
		
		icon = new ImageIcon("src/images/back.jpg");
		Image icon1 = icon.getImage();
		Image icon2 = icon1.getScaledInstance(40, 50, Image.SCALE_AREA_AVERAGING);
		ImageIcon icon = new ImageIcon(icon2);
		bt1 = new JButton("<", icon);
		bt1.setBounds(5, 5, 30, 35);
		add(bt1);
		bt1.addActionListener(this);
		
		bt[0].setBounds(450, 60, 100, 50);
		bt[1].setBounds(450, 60, 100, 50);
		bt[2].setBounds(450, 130, 100, 50);
		for(int i = 0; i < bt.length; i++) {
			add(bt[i]);
			bt[i].addActionListener(this);
		}
		
		table = new JTable(model);
		
		
		scroll = new JScrollPane(table);
		scroll.setBounds(40, 20, 400, 200);
		
//		DefaultTableCellRenderer renderer =  
//                (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
//		renderer.setHorizontalAlignment(SwingConstants.CENTER);
//		table.getTableHeader().setDefaultRenderer(renderer);

		
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
		
		panel.add(scroll);
		
		
		setVisible(true);
	}
	
	public Vector column() {
		Vector col = new Vector();
		col.add("번호");
		col.add("이름");
		col.add("입실 시간");
		col.add("퇴실 시간");
		col.add("날짜");
		
		return col;
		
	}
	public static void main(String[] args) {
		new CheckTable();
	}
	
	public void refreshCheck() {		// 새로운 정보를 출력
		CardCheckDB ad = new CardCheckDB();
		DefaultTableModel model = new DefaultTableModel(ad.checkList(), column());
		table.setModel(model);
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
	}
	public void selectList(String name) {		// 선택한 열의 입실관리
		CardCheckDB ccd = new CardCheckDB();
		
		DefaultTableModel model = new DefaultTableModel(ccd.selectCheckList(name), column());
		table.setModel(model);
		table.getColumn("번호").setWidth(0);
		table.getColumn("번호").setMinWidth(0);
		table.getColumn("번호").setMaxWidth(0);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		
		if(ob == bt[0]) {
			new ManagerLogin();
			dispose();
		}else if(ob == bt[1]) {
			try {
			int delete = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			if (delete == JOptionPane.OK_OPTION) {
				deleteCheck();
				compareDel();
				refreshCheck();
			}else
				JOptionPane.showMessageDialog(this, "취소하셨습니다.");
			}catch (ArrayIndexOutOfBoundsException e4) {
				JOptionPane.showMessageDialog(this, "대상을 선택해주세요.");
			}
			
		}else if(ob == bt[2]) {
			dispose();
		}else if(ob == bt1) {
			new CardCheck();
			
			dispose();
		}
		
	}
	
//	public int selectRow() {
//		int r = table.getSelectedRow();
//		int num = (int)table.getValueAt(r, 0);
//		
//		return num;	
//	}
	
	private void deleteCheck() {
		CheckInfo ci = checkView();
		CardCheckDB ccd = new CardCheckDB();
		
		boolean ok = ccd.deleteCheck(ci);
		
		if(ok) JOptionPane.showMessageDialog(this, "삭제가 완료되었습니다.");
		else JOptionPane.showMessageDialog(this, "삭제가 실패하였습니다.");
	}
	
	public CheckInfo checkView() {
		CheckInfo ci = new CheckInfo();
//		int num = selectRow();
		
		int r = table.getSelectedRow();
		int num = (int)table.getValueAt(r, 0);
		String name = (String)table.getValueAt(r, 1);
		String enter = (String)table.getValueAt(r, 2);
		String leave = (String)table.getValueAt(r, 3);
		String dates = (String)table.getValueAt(r, 4);
		
		
		ci.setNum(num);
		ci.setName(name);
		ci.setEnter(enter);
		ci.setLeave(leave);
		ci.setDates(dates);
		
		return ci;
	}
	
	public void compareDel() {
		AttendAbsenceDB aad = new AttendAbsenceDB();
		
		int r = table.getSelectedRow();
		
		String name = (String)table.getValueAt(r, 1);
		String enter = (String)table.getValueAt(r, 2);
		String leave = (String)table.getValueAt(r, 3);

		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String att = "09:40:00";
		String att1 = "14:30:00";
		String att2 = "18:20:00";

		try {
			Date att_t = date.parse(att);
			Date att1_t = date.parse(att1);
			Date att2_t = date.parse(att2);

			Date en_t = date.parse(enter);
			Date le_t = date.parse(leave);
			Calendar cal = Calendar.getInstance();
			Date ba_5 = date.parse(enter);
			cal.setTime(ba_5);
			cal.add(Calendar.HOUR, 5); // 5시간 더하기
			Date ba_t = cal.getTime();

			if (en_t.getTime() < att_t.getTime()) {
				if (le_t.getTime() >= att2_t.getTime()) {
					aad.UpdateSubAtt(name);
					System.out.println("출석!");
				} else if (le_t.getTime() < att2_t.getTime() && le_t.getTime() >= att1_t.getTime()) {
					aad.UpdateSubLeave(name);
					System.out.println("조퇴!");
				} else if (le_t.getTime() < att1_t.getTime()) {
					aad.UpdateSubAbs(name);
					System.out.println("결석!");
				}
			} else if (en_t.getTime() >= att_t.getTime() && en_t.getTime() < att1_t.getTime()) {
				if (le_t.getTime() >= att2_t.getTime()) {
					aad.UpdateSubLate(name);
					System.out.println("지각!");
				} else if (le_t.getTime() >= ba_t.getTime()) {
					aad.UpdateSubLate(name);
					aad.UpdateSubLeave(name);
					System.out.println("지각 & 조퇴!");
				} else if (le_t.getTime() < ba_t.getTime()) {
					aad.UpdateSubAbs(name);
					System.out.println("결석!");
				}

			} else if (en_t.getTime() >= att1_t.getTime()) {
				aad.UpdateSubAbs(name);
				System.out.println("결석!");
			}
			

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(this, "퇴실하지 않으셨습니다.");
		}
		aad.UpdateApper(name);
		aad.UpdateAcasal(name);
		aad.UpdateEmpsal(name);
		aad.UpdateTosal(name);
	
	
	}
	
	

}
