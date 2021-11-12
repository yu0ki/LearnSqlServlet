package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.UserExerciseBeans;

public class UserExerciseIndexDAO {
	//ここではユーザーが問題を一覧表示するときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // 問題一覧を表示する
	
    public List<UserExerciseBeans> findAllExercise(int display_option, int uid) {

        // 戻り値の用意
    	List<UserExerciseBeans> returnUEB =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			
			

			// display_flagによって検索内容を変更
			String sql;
			if (display_option == 0) {
				// 普通に一覧表示
				sql = "SELECT * FROM exercises ORDER BY eid ASC";
			} else if (display_option == 1) {
				// 並びを逆順にして一覧表示
				sql = "SELECT * FROM exercises ORDER BY eid DESC";
			} else if (display_option == 2) {
				// 最新の解答履歴で不正解だった問題
				sql = "SELECT * FROM exercises WHERE eid IN (SELECT eid FROM answerings WHERE uid = " +
						uid + ")  ORDER BY eid ASC;";
			} else if (display_option == 3) {
				// 不正解だった問題 + ブックマークした問題
				sql = " (SELECT eid, sentence, exercises.answer FROM exercises LEFT JOIN answerings USING (eid) WHERE eid IN (SELECT eid FROM answerings WHERE uid = "
						+ uid +") AND is_correct = false ORDER BY eid ASC)"
								+ " UNION  (SELECT eid, sentence, answer FROM exercises RIGHT JOIN bookmarks USING (eid) WHERE uid =" +  uid + " ORDER BY eid ASC);";
			} else {
				sql = "SELECT * FROM exercises;";
			}
			
			System.out.println(sql);
			PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            
           
         
            
            
            while (rs.next()) {
                // 見つかった情報を戻り値にセット
            	UserExerciseBeans ueb = new UserExerciseBeans();
                ueb.setEid(rs.getInt("eid"));
                ueb.setSentence(rs.getString("sentence"));    
                ueb.setAnswer(rs.getString("answer"));  
                
                
                
                // 問題の解答状況を取得
	            // 最新の解答履歴だけbeansに保存することにする
	            String sql_for_answerings = "SELECT * FROM answerings "
	            		+ "WHERE eid = ? AND uid = ? AND challenge_date = (SELECT MAX(challenge_date) FROM answerings WHERE eid = ?  AND uid = ?)";
                PreparedStatement ps_for_answerings = con.prepareStatement(sql_for_answerings);
                ps_for_answerings.setInt(1, ueb.getEid());
                ps_for_answerings.setInt(2, uid);
                ps_for_answerings.setInt(3, ueb.getEid());
                ps_for_answerings.setInt(4, uid);
                ResultSet rs_for_answerings = ps_for_answerings.executeQuery();
                System.out.println(sql_for_answerings);
                if (rs_for_answerings.next()) {
                	ueb.setChallengeDate(rs_for_answerings.getObject("challenge_date", OffsetDateTime.class));
                	ueb.setMyAnswer(rs_for_answerings.getString("answer"));
                	ueb.setIsCorrect(rs_for_answerings.getBoolean("is_correct"));
                } 
                
                // ブックマークされているかどうかを取得
	            String sql_for_bookmark = "SELECT * FROM bookmarks WHERE eid = ? AND uid = ?;";
	            PreparedStatement ps_for_bookmark = con.prepareStatement(sql_for_bookmark);
	            ps_for_bookmark.setInt(1, ueb.getEid());
                ps_for_bookmark.setInt(2, uid);
                ResultSet rs_for_bookmark = ps_for_bookmark.executeQuery();
                System.out.println(sql_for_bookmark);
                ueb.setIsBookmarked(rs_for_bookmark.next());     
                
                returnUEB.add(ueb);
                
            }
            
          
            
            
//            問題が存在しない場合nullを返す
            if (returnUEB.size() == 0) {
            	return null;
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
        return returnUEB;
    }
	
}
