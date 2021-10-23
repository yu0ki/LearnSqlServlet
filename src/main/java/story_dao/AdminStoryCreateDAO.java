package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAccountBeans;
import beans.AdminStoryBeans;

public class AdminStoryCreateDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // ストーリー一覧を表示する
		// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static AdminStoryBeans createStory(AdminStoryBeans asb, AdminAccountBeans aab, String new_title, String new_sentence, int new_eid, String new_next_title) {
	        // 戻り値の用意
	    	AdminStoryBeans returnASB =  new AdminStoryBeans();
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "BEGIN; INSERT INTO stories (title, sentence, eid, next_title) VALUES (?, ?, ?, ?);"
	            		+ "INSERT INTO story_creation_editing (title, admin_number, responsibility) VALUES (?, ?, ?::content); COMMIT;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setString(1, new_title);
	            ps.setString(2, new_sentence);
	            
	            if (new_eid != 0) {
	            	ps.setInt(3, new_eid);
	            } else {
	            	ps.setObject(3, null);
	            }
	            
	            if (new_next_title.equals("あとで決める")) {
	            	ps.setObject(4, null);
	            } else {
	            	ps.setString(4, new_next_title);
	            }
	            
	            ps.setString(5, new_title);
	            ps.setString(6, aab.getAdminNumber());
	            ps.setString(7, aab.getResponsibility());
	            
	            int r = ps.executeUpdate();

	           
	            // 戻り値をbeansにセット
//	            if (rs == 1) {
		            returnASB.setTitle(new_title);
		            returnASB.setSentence(new_sentence);
		            returnASB.setEid(new_eid);
		            returnASB.setNextTitle(new_next_title);
		            returnASB.setAdminNumber(aab.getAdminNumber());
		            returnASB.setResponsibility(aab.getResponsibility());
		            
		            ps = con.prepareStatement("SELECT MAX(editing_date) as max_editing_date FROM story_creation_editing "
		            		+ "WHERE title = ? AND admin_number = ? AND responsibility = ?::content");
		            ps.setString(1, new_title);
		            ps.setString(2, aab.getAdminNumber());
		            ps.setString(3, aab.getResponsibility());
		            ResultSet rs = ps.executeQuery();
		            if(rs.next()) {
		            	returnASB.setEditingDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            }
//	            } else {
//	            	// storyが見つからなかった場合はnullを返す
//	            	return null;
//	            }
	            
	            
	            
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
