package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminAnnouncementDeleteDAO {
	//ここでは管理者が告知を削除するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
		// 告知削除関数
	    public static void deleteAnnouncement(int aid) {
	        
	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

				// 告知を物理削除
	            String sql = "DELETE FROM announcements WHERE aid = ?;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setInt(1, aid);
	            
	            
	            ps.executeUpdate();
	            System.out.println(sql);
	         
	            
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
