package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStoryViewsDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // ストーリー一覧を表示する
		// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static void setViewStory(String title, int uid, boolean is_opened) {

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);
				
				// まずは、このユーザーがこのストーリーを見た閲覧記録があれば取得
	            String sql = "SELECT * FROM view_stories WHERE title = ? AND uid = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setString(1, title);
	            ps.setInt(2, uid);
	            ResultSet rs = ps.executeQuery();

	           
	            // 閲覧記録がなければ作成
	            // あればis_opened(カラム名)をis_opened(引数)でアップデート
	            String sql_for_is_opened;
	            PreparedStatement ps_for_is_opened;
	            if (!rs.next()) {
	            	sql_for_is_opened = "INSERT INTO view_stories (is_opened, uid, title) VALUES (?, ?, ?)";
	            	ps_for_is_opened = con.prepareStatement(sql_for_is_opened);

	             	ps_for_is_opened.setBoolean(1, true);
	             	ps_for_is_opened.setInt(2, uid);
	             	ps_for_is_opened.setString(3, title);
	            } else {
	            	sql_for_is_opened = "UPDATE view_stories SET is_opened = ? WHERE uid = ? AND title = ?";
	            	ps_for_is_opened = con.prepareStatement(sql_for_is_opened);

	            	ps_for_is_opened.setBoolean(1, is_opened);
	            	ps_for_is_opened.setInt(2, uid);
	            	ps_for_is_opened.setString(3, title);
	            }
	            
	            System.out.println(sql_for_is_opened);

	            ps_for_is_opened.executeUpdate();
	            
	            
	           
	            
	        } catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	    }
	    
	   
}
