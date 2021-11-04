package enum_db;

// adminsテーブルのcontentカラムにはenum型が使われている。
// javaでそれを表現するため、enum型のクラスContentを作成

public enum Content {
	// enumで管理されているのはこの3種の役職
	Story("ストーリー"), Exercise("問題"), Announcement("告知");
	
	private String content;

	Content(String content) {
		this.content = content;
	}
	
	public void setContent(String content) {
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    //String型で受け取ったcontentを判定してContent型で返す
    public static Content getByString(String content) {
        for(Content con:Content.values()) {
            if(con.getContent().equals(content)) {
                return con;
            }
        }
        return null;
    }

}
