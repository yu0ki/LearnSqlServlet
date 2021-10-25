package beans;

import java.time.OffsetDateTime;

public class AdminExerciseBeans {
	// 管理者が問題をいじるために必要なデータを全部含んだBeansを作る
	
	private int eid;
	private String sentence;
	private String answer;
	
	// この問題を最後に編集した管理者のデータを保持
	private String admin_number;
	private String responsibility;
	private String contact;
	private OffsetDateTime editing_date;
	
	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getAdminNumber() {
		return admin_number;
	}
	
	public void setAdminNumber(String admin_number) {
		this.admin_number = admin_number;
	}
	
	public String getResponsibility() {
		return responsibility;
	}
	
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public OffsetDateTime getEditingDate() {
        return editing_date;
    }
	
    public void setEditingDate(Object editing_date) {    	
    	this.editing_date = (OffsetDateTime) editing_date;
    }

}
