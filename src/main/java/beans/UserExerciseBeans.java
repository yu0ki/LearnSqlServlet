package beans;

import java.time.OffsetDateTime;

public class UserExerciseBeans {
	// ユーザーが問題をいじるために必要なデータを全部含んだBeansを作る
	
	private int eid;
	private String sentence;
	private String answer;
	
	// 閲覧状態を取得
	private OffsetDateTime challenge_date;
	private String my_answer;
	private boolean is_correct;
	
	// ブックマークされているかを取得
	private boolean is_bookmarked;
	
	
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
	
	public String getMyAnswer() {
		return my_answer;
	}
	
	public void setMyAnswer(String my_answer) {
		this.my_answer = my_answer;
	}
	
	public OffsetDateTime getChallengeDate() {
        return challenge_date;
    }
	
    public void setChallengeDate(Object challenge_date) {    	
    	this.challenge_date = (OffsetDateTime) challenge_date;
    }
	
	public boolean getIsCorrect() {
		return is_correct;
	}
	
	public void setIsCorrect(boolean is_correct) {
		this.is_correct = is_correct;
	}
	
	public boolean getIsBookmarked() {
		return is_bookmarked;
	}
	
	public void setIsBookmarked(boolean is_bookmarked) {
		this.is_bookmarked = is_bookmarked;
	}

}
