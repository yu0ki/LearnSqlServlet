package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

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
	            
	            // 問題の解答状況を取得
	            // 最新の解答履歴だけbeansに保存することにする
	            String sql_for_answerings = "SELECT * FROM answerings "
	            		+ "WHERE eid = ? AND uid = ? AND challenge_date = (SELECT MAX(challenge_date) FROM answerings WHERE eid = ?  AND uid = ?)";
                PreparedStatement ps_for_answerings = con.prepareStatement(sql_for_answerings);
                ps_for_answerings.setInt(1, eid);
                ps_for_answerings.setInt(2, uid);
                ps_for_answerings.setInt(3, eid);
                ps_for_answerings.setInt(4, uid);
                ResultSet rs_for_answerings = ps_for_answerings.executeQuery();
                if (rs_for_answerings.next()) {
                	returnUEB.setChallengeDate(rs_for_answerings.getObject("challenge_date", OffsetDateTime.class));
                	returnUEB.setMyAnswer(rs_for_answerings.getString("answer"));
                	returnUEB.setIsCorrect(rs_for_answerings.getBoolean("is_correct"));
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
