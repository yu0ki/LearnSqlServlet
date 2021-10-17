package enum_db;

public enum Content {
	Story("ストーリー"), Exercise("問題"), Announcement("告知");
	
	private String content;

	Content(String content) {
		// TODO 自動生成されたコンストラクター・スタブ
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
