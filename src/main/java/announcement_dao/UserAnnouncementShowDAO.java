package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.UserAnnouncementBeans;

public class UserAnnouncementShowDAO {
	//ここではユーザーが告知詳細情報を得るとき使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // UserAnnouncementBeansの情報を全て埋めるべくSQLを走らせる関数
		
	    public static UserAnnouncementBeans findAnnouncement(int aid, int uid) {
	        // 戻り値の用意
	    	UserAnnouncementBeans returnUAB =  new UserAnnouncementBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);
				
				// まずは表示したい告知を取得
	            String sql = "SELECT * FROM announcements WHERE aid = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, aid);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);

	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnUAB.setAid(rs.getInt("aid"));
		            returnUAB.setSentence(rs.getString("sentence"));
		            returnUAB.setTitle(rs.getString("title"));
		            returnUAB.setPublicationDate(rs.getObject("publication_date", OffsetDateTime.class));
	            } else {
	            	// announcementが見つからなかった場合はnullを返す
	            	return null;
	            }
	            
	            // さらに閲覧状態を取得
	            String sql_for_is_opened = "SELECT is_opened FROM view_announcements WHERE aid = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setInt(1, returnUAB.getAid());
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                System.out.println(sql_for_is_opened);
                
                if (rs_for_is_opened.next()) {
                	// この告知を一度でも表示したことがある場合、閲覧状態(is_opened)を取得
                	returnUAB.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                	
                } else {
                	// 初閲覧（閲覧記録がない）場合は初期値を設定
                	returnUAB.setIsOpened(false);
                }
//	            
	            
	            
//	            
	            
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
	        return returnUAB;
	    }
	    
	   
}
