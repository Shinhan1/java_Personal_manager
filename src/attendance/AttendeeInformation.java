package attendance;
/*
SQL> CREATE TABLE GT_MEMBER(
  1  NUM NUMBER(10) NOT NULL,
  2  NAME VARCHAR2(20),
  3  BIRTH VARCHAR2(20),
  4  JOB VARCHAR2(20),
  5  GENDER VARCHAR2(10),
  6  ADDR VARCHAR2(40),
  7  PHNUM VARCHAR2(20),
  8  EMAIL VARCHAR2(40),
  9  DAT VARCHAR2(20),
 10  INTRO VARCHAR2(2000));
 
 CREATE SEQUENCE PM_MEM_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 1
NOCACHE
NOCYCLE;

*/

public class AttendeeInformation {		// 사원의 정보를 저장
	private String Name,birth, job, gender, addr, phnum, email, date, intro;
	private int num;
	
//	public AttendeeInformation(String name, String birth, String job, String gen, String addr, String phnum,
//			String email, String date, String intro) {
//		super();
//		this.Name = name;
//		this.birth = birth;
//		this.job = job;
//		this.gen = gen;
//		this.addr = addr;
//		this.phnum = phnum;
//		this.email = email;
//		this.date = date;
//		this.intro = intro;
//	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhnum() {
		return phnum;
	}

	public void setPhnum(String phNum) {
		this.phnum = phNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	

}
