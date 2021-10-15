package beans;

import java.time.OffsetDateTime;

//import java.io.Serializable;

//public class UserAccountBeans implements Serializable {


// このクラスはユーザーのアカウントに関する情報を保持するbenasを定義している

public class UserAccountBeans {
	private int uid;
    private String nickname;
    OffsetDateTime registration_date;
    private boolean is_valid_account;

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public OffsetDateTime getRegistrationDate() {
        return registration_date;
    }
    public void setRegisrationDate(Object registration_date) {
    	if (registration_date instanceof OffsetDateTime) {
        	this.registration_date = (OffsetDateTime) registration_date;
    	}
    }
    
    public boolean getIsValidAccount() {
        return is_valid_account;
    }
    public void setIsValidAccount(boolean is_valid_account) {
        this.is_valid_account = is_valid_account;
    }
   

}
