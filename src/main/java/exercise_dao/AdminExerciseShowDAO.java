package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminExerciseBeans;

public class AdminExerciseShowDAO {
	//ここでは管理者が問題を詳細表示するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // AdminExerciseBeansの情報を全て埋めるべくsqlを走らせる関数
		
	    public static AdminExerciseBeans findExercise(int eid) {
	        // 戻り値の用意
	    	AdminExerciseBeans returnAEB =  new AdminExerciseBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "SELECT * FROM exercises JOIN exercise_creation_editing USING (eid)"
	            		+ "WHERE eid = ? AND editing_date = (SELECT MAX (editing_date) FROM exercise_creation_editing WHERE eid = ?)";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, eid);
	            ps.setInt(2, eid);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);

	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnAEB.setSentence(rs.getString("sentence"));
		            returnAEB.setEid(rs.getInt("eid"));
		            returnAEB.setAnswer(rs.getString("answer"));
		            returnAEB.setAdminNumber(rs.getString("admin_number"));
		            returnAEB.setResponsibility(rs.getString("responsibility"));
		            returnAEB.setEditingDate(rs.getObject("editing_date", OffsetDateTime.class));
	            } else {
	            	// 問題が見つからなかった場合はnullを返す
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
	        return returnAEB;
	    }
	    
	   
}
