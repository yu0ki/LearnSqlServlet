package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAccountBeans;
import beans.AdminExerciseBeans;

public class AdminExerciseEditDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // ストーリー一覧を表示する
		// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static AdminExerciseBeans editExercise(AdminExerciseBeans aeb, AdminAccountBeans aab, String new_sentence, String new_answer) {
	        // 戻り値の用意
	    	AdminExerciseBeans returnAEB =  new AdminExerciseBeans();
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	            String sql = "BEGIN; UPDATE exercises SET eid = ?, sentence = ?, answer = ? WHERE eid = ?;"
	            		+ "INSERT INTO exercise_creation_editing (eid, admin_number, responsibility) VALUES (?, ?, ?::content); COMMIT;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setInt(1, aeb.getEid());
	            ps.setString(2, new_sentence);
	            ps.setString(3, new_answer);
	            
	            ps.setInt(4, aeb.getEid());
	            ps.setInt(5, aeb.getEid());
	            ps.setString(6, aab.getAdminNumber());
	            ps.setString(7, aab.getResponsibility());
	            
	            int r = ps.executeUpdate();

	           
	            // 戻り値をbeansにセット
	            if (r == 1) {
		            returnAEB.setSentence(new_sentence);
		            returnAEB.setEid(aeb.getEid());
		            returnAEB.setAnswer(new_answer);
		            returnAEB.setAdminNumber(aab.getAdminNumber());
		            returnAEB.setResponsibility(aab.getResponsibility());
		            
		            ps = con.prepareStatement("SELECT MAX(editing_date) as max_editing_date FROM exercise_creation_editing "
		            		+ "WHERE eid = ? AND admin_number = ? AND responsibility = ?::content");
		            ps.setInt(1, aeb.getEid());
		            ps.setString(2, aab.getAdminNumber());
		            ps.setString(3, aab.getResponsibility());
		            ResultSet rs = ps.executeQuery();
		            if(rs.next()) {
		            	returnAEB.setEditingDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            }
	            } else {
//	            	// exerciseが見つからなかった場合はnullを返す
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
