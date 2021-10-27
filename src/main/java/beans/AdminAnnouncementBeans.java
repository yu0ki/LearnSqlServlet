package beans;

import java.time.OffsetDateTime;

public class AdminAnnouncementBeans {
	// 管理者が問題をいじるために必要なデータを全部含んだBeansを作る
	
	private int aid;

	private String title;
	private String sentence;
	
	private OffsetDateTime publication_date;
	
	// この問題を最後に編集した管理者のデータを保持
	private String admin_number;
	private String responsibility;
	private String contact;
	private OffsetDateTime editing_date;
	
	public int getAid() {
		return aid;
	}
	
	public void setAid(int aid) {
		this.aid = aid;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public OffsetDateTime getPublicationDate() {
        return publication_date;
    }
	
    public void setPublicationDate(Object publication_date) {    	
    	this.publication_date = (OffsetDateTime) publication_date;
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
