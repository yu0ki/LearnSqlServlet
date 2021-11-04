package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminStoryDeleteDAO {
	//ここでは管理者がストーリーを削除するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
		
	    public static void deleteStory(String title) {
	        
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

				// ストーリーを物理削除
	            String sql = "DELETE FROM stories WHERE title = ?;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setString(1, title);
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
