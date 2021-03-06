package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminStoryBeans;

public class AdminStoryShowDAO {
	//ここでは管理者がストーリーを詳細表示するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // AdminStoryBeansを埋めるべくsqlを走らせる関数
		
	    public static AdminStoryBeans findStory(String title) {
//	    	System.out.println(title);
	        // 戻り値の用意
	    	AdminStoryBeans returnASB =  new AdminStoryBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "SELECT * FROM stories JOIN story_creation_editing USING (title)"
	            		+ "WHERE title = ? AND editing_date = (SELECT MAX (editing_date) FROM story_creation_editing WHERE title = ?)";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setString(1, title);
	            ps.setString(2, title);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);
	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnASB.setTitle(rs.getString("title"));
		            returnASB.setSentence(rs.getString("sentence"));
		            returnASB.setEid(rs.getInt("eid"));
		            returnASB.setNextTitle(rs.getString("next_title"));
		            returnASB.setAdminNumber(rs.getString("admin_number"));
		            returnASB.setResponsibility(rs.getString("responsibility"));
		            returnASB.setEditingDate(rs.getObject("editing_date", OffsetDateTime.class));
	            } else {
	            	// storyが見つからなかった場合はnullを返す
	            	return null;
	            }
	            
	            
	            
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
	        return returnASB;
	    }
	    
	   
}
