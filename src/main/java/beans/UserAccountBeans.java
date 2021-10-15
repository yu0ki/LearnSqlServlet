package beans;

//import java.io.Serializable;

//public class UserAccountBeans implements Serializable {


// このクラスはユーザーのアカウントに関する情報のうち、
// ログインやセッション管理などに必要なuidとnicknameをもつbeansです。

public class UserAccountBeans {
	private int uid;
    private String nickname;

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
    

}
