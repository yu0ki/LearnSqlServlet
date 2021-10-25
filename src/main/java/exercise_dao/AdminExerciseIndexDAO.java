package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.AdminExerciseBeans;

public class AdminExerciseIndexDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ストーリー一覧を表示する
	// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
	
    public List<AdminExerciseBeans> findAllExercise() {

        // 戻り値の用意
    	List<AdminExerciseBeans> returnAEB =  new ArrayList<>();

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
            	AdminExerciseBeans aeb = new AdminExerciseBeans();
                aeb.setEid(rs.getInt("eid"));
                aeb.setSentence(rs.getString("sentence"));    
                aeb.setAnswer(rs.getString("answer"));  
                
                returnAEB.add(aeb);
                
                
                
            }
            
          
            
            
//            ストーリーが存在しない場合nullを返す
            if (returnAEB.size() == 0) {
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
        return returnAEB;
    }
	
}
