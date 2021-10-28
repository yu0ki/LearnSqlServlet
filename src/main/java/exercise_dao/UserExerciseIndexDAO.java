package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UserExerciseBeans;

public class UserExerciseIndexDAO {
	//ここではユーザーが問題をいじるときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // 問題一覧を表示する
	
    public List<UserExerciseBeans> findAllExercise() {

        // 戻り値の用意
    	List<UserExerciseBeans> returnUEB =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT * FROM exercises";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            
           
         
            
            
            while (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
            	UserExerciseBeans ueb = new UserExerciseBeans();
                ueb.setEid(rs.getInt("eid"));
                ueb.setSentence(rs.getString("sentence"));    
                ueb.setAnswer(rs.getString("answer"));  
                
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
