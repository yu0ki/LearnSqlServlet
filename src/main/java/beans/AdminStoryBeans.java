package beans;

import java.time.OffsetDateTime;

public class AdminStoryBeans {
	// 管理者がストーリーをいじるために必要なデータを全部含んだBeansを作る
	
	private String title;
	private String sentence;
	private int eid;
	private String next_title;
	
	private String admin_number;
	private String responsibility;
	private String contact;
	private OffsetDateTime editing_date;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public int getEid() {
		return eid;
	}
	
	public void setEid(int eid) {
		this.eid = eid;
	}
	
	public String getNextTitle() {
		return next_title;
	}
	
	public void setNextTitle(String next_title) {
		this.next_title = next_title;
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
	
	public OffsetDateTime getEdtingDate() {
        return editing_date;
    }
	
    public void setEdtingDate(Object editing_date) {    	
    	this.editing_date = (OffsetDateTime) editing_date;
    }

}
