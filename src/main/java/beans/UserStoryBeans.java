package beans;

public class UserStoryBeans {
	// ユーザーがストーリーをいじるために必要なデータを全部含んだBeansを作る
	
	private String title;
	private String sentence;
	private int eid;
	private String next_title;
	
	// このストーリーの現在ログインしているユーザーの閲覧履歴情報
	private boolean is_opened;
	private boolean is_focused;
	
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
	
	public boolean getIsOpened() {
		return is_opened;
	}
	
	public void setIsOpened(boolean is_opened) {
		this.is_opened = is_opened;
	}
	
	public boolean getIsFocused() {
		return is_focused;
	}
	
	public void setIsFocused(boolean is_focused) {
		this.is_focused = is_focused;
	}

}
