package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAnnouncementViewsDAO {
	//ここではユーザーの告知閲覧履歴を管理するデータアクセス機能を作る
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		

		
	    public static void setViewAnnouncement(int aid, int uid, boolean is_opened) {

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);
				
				// まずは、このユーザーがこの告知を見た閲覧記録があれば取得
	            String sql = "SELECT * FROM view_announcements WHERE aid = ? AND uid = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, aid);
	            ps.setInt(2, uid);
	            ResultSet rs = ps.executeQuery();

	           
	            // 閲覧記録がなければ作成
	            // あればis_opened(カラム名)をis_opened(引数)でアップデート
	            String sql_for_is_opened;
	            PreparedStatement ps_for_is_opened;
	            if (!rs.next()) {
	            	sql_for_is_opened = "INSERT INTO view_announcements (is_opened, uid, aid) VALUES (?, ?, ?)";
	            	ps_for_is_opened = con.prepareStatement(sql_for_is_opened);

	             	ps_for_is_opened.setBoolean(1, true);
	             	ps_for_is_opened.setInt(2, uid);
	             	ps_for_is_opened.setInt(3, aid);
	            } else {
	            	sql_for_is_opened = "UPDATE view_announcements SET is_opened = ? WHERE uid = ? AND aid = ?";
	            	ps_for_is_opened = con.prepareStatement(sql_for_is_opened);

	             	ps_for_is_opened.setBoolean(1, is_opened);
	             	ps_for_is_opened.setInt(2, uid);
	             	ps_for_is_opened.setInt(3, aid);
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
