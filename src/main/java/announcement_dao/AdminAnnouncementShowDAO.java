package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAnnouncementBeans;

public class AdminAnnouncementShowDAO {
	//ここでは管理者が告知をいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // ある告知のbeansの項目を埋めるべく詳細情報を取得
		// 告知の題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static AdminAnnouncementBeans findAnnouncement(int aid) {
	        // 戻り値の用意
	    	AdminAnnouncementBeans returnAANB =  new AdminAnnouncementBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "SELECT * FROM announcements JOIN announcement_creation_editing USING (aid)"
	            		+ "WHERE aid = ? AND editing_date = (SELECT MAX (editing_date) FROM announcement_creation_editing WHERE aid = ?)";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, aid);
	            ps.setInt(2, aid);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);

	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnAANB.setSentence(rs.getString("sentence"));
		            returnAANB.setAid(rs.getInt("aid"));
		            returnAANB.setTitle(rs.getString("title"));
		            returnAANB.setAdminNumber(rs.getString("admin_number"));
		            returnAANB.setResponsibility(rs.getString("responsibility"));
		            returnAANB.setEditingDate(rs.getObject("editing_date", OffsetDateTime.class));
	            } else {
	            	// 告知が見つからなかった場合はnullを返す
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
