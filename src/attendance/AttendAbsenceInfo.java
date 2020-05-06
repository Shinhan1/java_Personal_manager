package attendance;

// 출결 정보
/*
create table AttAbs
(num number(10) not null,		시퀀스 번호
name varchar2(20),				이름
attend number(10),				출석
absense number(10),				결석
late number(10),				지각
leave number(10),				조퇴
goout number(10),				외출
appers number(10, 2),			출결 퍼센티
acasal number(10),				학원 월급
empsal number(10),				고용부 월급
tosal number(10));				총 월급
*/

public class AttendAbsenceInfo {
	private String name;
	// 이름, 출석, 결석, 지각, 조퇴, 외출, 출결비율, 학원월급, 고용부월급, 총 월급
	private int num, attend, absence, late, leave, goout, acasal, empsal, tosal;
	private double appers;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAttend() {
		return attend;
	}
	public void setAttend(int attend) {
		this.attend = attend;
	}
	public int getAbsence() {
		return absence;
	}
	public void setAbsence(int absence) {
		this.absence = absence;
	}
	public int getLate() {
		return late;
	}
	public void setLate(int late) {
		this.late = late;
	}
	public int getLeave() {
		return leave;
	}
	public void setLeave(int leave) {
		this.leave = leave;
	}
	public int getGoout() {
		return goout;
	}
	public void setGoout(int goout) {
		this.goout = goout;
	}
	public int getAcasal() {
		return acasal;
	}
	public void setAcasal(int acasal) {
		this.acasal = acasal;
	}
	public int getEmpsal() {
		return empsal;
	}
	public void setEmpsal(int empsal) {
		this.empsal = empsal;
	}
	public int getTosal() {
		return tosal;
	}
	public void setTosal(int tosal) {
		this.tosal = tosal;
	}
	public double getAppers() {
		return appers;
	}
	public void setAppers(double appers) {
		this.appers = appers;
	}
	
	

}
