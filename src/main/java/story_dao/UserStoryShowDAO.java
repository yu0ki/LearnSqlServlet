package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserStoryBeans;

public class UserStoryShowDAO {
	//ここではユーザーがストーリー詳細情報を得るときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
		
		// UserStoryBeansの情報を全て埋めるべくSQLを走らせる関数
		
	    public static UserStoryBeans findStory(String title, int uid) {
	        // 戻り値の用意
	    	UserStoryBeans returnUSB =  new UserStoryBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);
				
				// まずはストーリーを取得
	            String sql = "SELECT * FROM stories WHERE title = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setString(1, title);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);

	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnUSB.setTitle(rs.getString("title"));
		            returnUSB.setSentence(rs.getString("sentence"));
		            returnUSB.setEid(rs.getInt("eid"));
		            returnUSB.setNextTitle(rs.getString("next_title"));
	            } else {
	            	// storyが見つからなかった場合はnullを返す
	            	return null;
	            }
	            
	            // さらに閲覧状態を取得
	            String sql_for_is_opened = "SELECT is_opened FROM view_stories WHERE title = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setString(1, title);
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                System.out.println(sql_for_is_opened);
                if (rs_for_is_opened.next()) {
                	returnUSB.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                	
                } else {
                	// 初閲覧（閲覧記録がない）場合は初期値を設定
                	returnUSB.setIsOpened(false);
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
	        return returnUSB;
	    }
	    
	   
}
