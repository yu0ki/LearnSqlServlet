package beans;

public class UserExerciseBeans {
	// ユーザーが問題をいじるために必要なデータを全部含んだBeansを作る
	
	private int eid;
	private String sentence;
	private String answer;
	
	// 閲覧状態を取得
	private boolean is_opened;
	
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
	
	public boolean getIsOpened() {
		return is_opened;
	}
	
	public void setIsOpened(boolean is_opened) {
		this.is_opened = is_opened;
	}

}
