package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAnswerLogDAO {
	//ここではユーザーの問題への解答履歴一覧を取得するときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // 解答履歴一覧を取得する関数
		
	    public static List<List<String>> findAnswerLog(int eid, int uid) {
	    	
	    	// 返り値を準備
	    	List<List<String>> returnLog = new ArrayList<>();

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);
				
				// このユーザーが問題を解いた記録があれば取得
	            String sql = "SELECT * FROM answerings WHERE eid = ? AND uid = ? ORDER BY (challenge_date) ASC";
	            PreparedStatement ps= con.prepareStatement(sql);
	            ps.setInt(1, eid);
	            ps.setInt(2, uid);
	            ResultSet rs = ps.executeQuery();
	            System.out.println(sql);

	           
	            // 取得したデータをリストに格納
	            while (rs.next()) {
	                // 見つかった情報を戻り値にセット
	            	List<String> tmp = new ArrayList<>();
	            	if (rs.getBoolean("is_correct")) {
	            		tmp.add("true");
	            	} else {
	            		tmp.add("false");
	            	}
	            	tmp.add(rs.getString("answer"));
	            	tmp.add(rs.getObject("challenge_date", OffsetDateTime.class).toString());
	            	
	            	
	            	returnLog.add(tmp);
	            }
	                
	            
	            
	           
	            
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
	        
	        return returnLog;
	    }
	    
	   
}
