package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkDAO {
	//ここではブックマークを付ける・外す動作を実装する
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    public static void bookmarkExercise(int eid, int uid) {

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "SELECT * FROM bookmarks WHERE eid = ? AND uid = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, eid);
	            ps.setInt(2, uid);
	            ResultSet rs = ps.executeQuery();

	           
	            // 戻り値が空でない場合（= ブックマークがされている場合）、ブックマークを外す(レコードを削除)
	            // 戻り値が空の場合、ブックマークを付ける
	            if (rs.next()) {
		            String delete_bookmark = "DELETE FROM bookmarks WHERE eid = ? AND uid = ?";
		            System.out.println(delete_bookmark);
		            PreparedStatement ps_delete = con.prepareStatement(delete_bookmark);
		            ps_delete.setInt(1, eid);
		            ps_delete.setInt(2, uid);
		            ps_delete.executeUpdate();
	            } else {
	            	String insert_bookmark = "INSERT INTO bookmarks (eid, uid) VALUES (?, ?)";
	            	System.out.println(insert_bookmark);
		            PreparedStatement ps_insert = con.prepareStatement(insert_bookmark);
		            ps_insert.setInt(1, eid);
		            ps_insert.setInt(2, uid);
		            ps_insert.executeUpdate();
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
	    }
	    
	   
}
