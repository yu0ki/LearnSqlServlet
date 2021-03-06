package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAccountBeans;
import beans.AdminAnnouncementBeans;

public class AdminAnnouncementEditDAO {
	//ここでは管理者が告知を編集するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	   //  告知を編集し、編集履歴を残す関数
		
	    public static AdminAnnouncementBeans editAnnouncement(AdminAnnouncementBeans aanb, AdminAccountBeans aab, String new_sentence, String new_title) {
	        // 戻り値の用意
	    	AdminAnnouncementBeans returnAANB =  new AdminAnnouncementBeans();
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

				// 告知を編集し、その編集履歴をつけるsql
	            String sql = "BEGIN; UPDATE announcements SET aid = ?, sentence = ?, title = ? WHERE aid = ?;"
	            		+ "INSERT INTO announcement_creation_editing (aid, admin_number, responsibility) VALUES (?, ?, ?::content); COMMIT;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setInt(1, aanb.getAid());
	            ps.setString(2, new_sentence);
	            ps.setString(3, new_title);
	            
	            ps.setInt(4, aanb.getAid());
	            ps.setInt(5, aanb.getAid());
	            ps.setString(6, aab.getAdminNumber());
	            ps.setString(7, aab.getResponsibility());
	            
	            int r = ps.executeUpdate();
	            System.out.println(sql);

	           
	            // 戻り値をbeansにセット
	            if (r != 0) {
		            returnAANB.setSentence(new_sentence);
		            returnAANB.setAid(aanb.getAid());
		            returnAANB.setTitle(new_title);
		            returnAANB.setAdminNumber(aab.getAdminNumber());
		            returnAANB.setResponsibility(aab.getResponsibility());
		            
		            // 最終編集者・日時を取得し、beansに格納
		            ps = con.prepareStatement("SELECT MAX(editing_date) as max_editing_date FROM announcement_creation_editing "
		            		+ "WHERE aid = ? AND admin_number = ? AND responsibility = ?::content");
		            ps.setInt(1, aanb.getAid());
		            ps.setString(2, aab.getAdminNumber());
		            ps.setString(3, aab.getResponsibility());
		            ResultSet rs = ps.executeQuery();
		            if(rs.next()) {
		            	returnAANB.setEditingDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            }
	            } else {
//	            	// announcementが見つからなかった場合はnullを返す
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
	        return returnAANB;
	    }
	    
	   
}
