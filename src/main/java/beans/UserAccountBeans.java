package beans;

import java.time.OffsetDateTime;

//import java.io.Serializable;

//public class UserAccountBeans implements Serializable {


// このクラスはユーザーのアカウントに関する情報を保持するbenasを定義している

public class UserAccountBeans {
	private int uid;
    private String nickname;
    OffsetDateTime registered_date;
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
    
    public OffsetDateTime getRegisteredDate() {
        return registered_date;
    }
    public void setRegisteredDate(Object registered_date) {
    	if (registered_date instanceof OffsetDateTime) {
        	this.registered_date = (OffsetDateTime) registered_date;
    	}
    }
    
    public boolean getIsValidAccount() {
        return is_valid_account;
    }
    public void setIsValidAccount(boolean is_valid_account) {
        this.is_valid_account = is_valid_account;
    }
   

}
