package concurrent.delayqueue;

import java.util.Date;

public class ExamVO {
	private String name;
	private String no;
	private String timeStr;
	
	public ExamVO(String name, String no, long time) {
		super();
		this.name = name;
		this.no = no;
		timeStr = new Date(System.currentTimeMillis() + time).toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public String toString() {
		return "ExamVO [name=" + name + ", no=" + no + "]"+ ", timeStr=" + timeStr + "]";
	}
	
	
}
