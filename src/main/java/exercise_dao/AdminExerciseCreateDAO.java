package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAccountBeans;
import beans.AdminExerciseBeans;

public class AdminExerciseCreateDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // ストーリー一覧を表示する
		// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static AdminExerciseBeans createExercise(AdminExerciseBeans aeb, AdminAccountBeans aab, String new_sentence, String new_answer) {
	        // 戻り値の用意
	    	AdminExerciseBeans returnAEB =  new AdminExerciseBeans();
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	           
	            
	            // 今生成しているeidが何番になるかを取得
	            String sql_sub = "SELECT nextval('exercises_eid_seq');";
	            PreparedStatement ps_sub = con.prepareStatement(sql_sub);
	            ResultSet rs_sub = ps_sub.executeQuery();
	            
	            int next_eid = 0;
	            while (rs_sub.next()) {
	            	next_eid = rs_sub.getInt("nextval");
	            }
	            
	            // nextvalしたことで進んでしまった連番型をひとつ戻し、新規作成記録を作るsqlを書く
	            
	            String sql = "alter sequence exercises_eid_seq restart with " + next_eid + ";"
	            		+ "BEGIN; INSERT INTO exercises (sentence, answer) VALUES (?, ?);"
	            		+ "INSERT INTO exercise_creation_editing (eid, admin_number, responsibility) VALUES (?, ?, ?::content); COMMIT;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setString(1, new_sentence);
	            ps.setString(2, new_answer);
	            
	            if (next_eid == 0) {
	            	System.out.println("nextval失敗");	   
	            } else {
	            	ps.setInt(3, next_eid);
	            }
	            
	            ps.setString(4, aab.getAdminNumber());
	            ps.setString(5, aab.getResponsibility());
	            
	            int r = ps.executeUpdate();

	           
	            // 戻り値をbeansにセット
	            if (r == 1) {
		            returnAEB.setSentence(new_sentence);
		            returnAEB.setEid(next_eid);
		            returnAEB.setAnswer(new_answer);
		            returnAEB.setAdminNumber(aab.getAdminNumber());
		            returnAEB.setResponsibility(aab.getResponsibility());
		            
		            // 最新の編集時刻を取得
		            ps = con.prepareStatement("SELECT MAX(editing_date) as max_editing_date FROM exercise_creation_editing "
		            		+ "WHERE eid = ? AND admin_number = ? AND responsibility = ?::content");
		            ps.setInt(1, next_eid);
		            ps.setString(2, aab.getAdminNumber());
		            ps.setString(3, aab.getResponsibility());
		            ResultSet rs = ps.executeQuery();
		            if(rs.next()) {
		            	returnAEB.setEditingDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            }
	            } else {
	            	// exerciseが見つからなかった場合はnullを返す
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
