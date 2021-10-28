package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserExerciseBeans;

public class UserExerciseShowDAO {
	//ここでは管理者が問題をいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // 問題一覧を表示する
		// 問題の題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static UserExerciseBeans findExercise(int eid, int uid) {
	        // 戻り値の用意
	    	UserExerciseBeans returnUEB =  new UserExerciseBeans();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "SELECT * FROM exercises WHERE eid = ?";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, eid);
	            ResultSet rs = ps.executeQuery();

	           
	            // 戻り値をbeansにセット
	            if (rs.next()) {
		            returnUEB.setSentence(rs.getString("sentence"));
		            returnUEB.setEid(rs.getInt("eid"));
		            returnUEB.setAnswer(rs.getString("answer"));
	            } else {
	            	// 問題が見つからなかった場合はnullを返す
	            	return null;
	            }
	            
	            // 問題の閲覧状態を取得
	            String sql_for_is_opened = "SELECT is_opened FROM view_exercises WHERE eid = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setInt(1, eid);
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                if (rs_for_is_opened.next()) {
                	returnUEB.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                } else {
                	returnUEB.setIsOpened(false);
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
	        return returnUEB;
	    }
	    
	   
}
