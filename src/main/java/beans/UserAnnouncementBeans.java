package beans;

import java.time.OffsetDateTime;

public class UserAnnouncementBeans {
	// ユーザーが告知をいじるために必要なデータを全部含んだBeansを作る
	
	private int aid;

	private String title;
	private String sentence;
	
	private OffsetDateTime publication_date;
	
	// この告知の現在ログインしているユーザーの閲覧履歴情報
	// is_openedはこの告知が1度でもアクセスされたことがあればtrue
	private boolean is_opened;
	// is_focusedはこの告知にフラグが立っているときにtrue
	// データベース上でフラグが立っているというのは、閲覧履歴があるのにis_openedカラムがfalseの場合に相当
	private boolean is_focused;
	
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
