package beans;

//import java.io.Serializable;

//public class UserAccountBeans implements Serializable {


// このクラスはユーザーのアカウントに関する情報を保持するbenasを定義している

public class AdminAccountBeans {
	private String admin_number;
    private String responsibility;
    private String contact;

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
   

}
