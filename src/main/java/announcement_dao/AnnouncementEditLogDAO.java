package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementEditLogDAO {
	//ここでは管理者がストーリー詳細ページで確認する編集履歴一覧を取得する
	
	// データベース接続に使用する情報
	private static String _hostname = "localhost";
	private static String _dbname = "sampledb";
	private static String _username = "postgres";
	private static String _password = "postgres";
	
    // 2次元配列で返す
	
    public static List<List<String>> findAnnouncementLog(int aid) {

        // 戻り値の用意
    	List<List<String>> returnLog =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT name, responsibility, contact, is_valid_account, editing_date "
            		+ "FROM (announcement_creation_editing LEFT JOIN admins USING (admin_number, responsibility)) LEFT JOIN admin_names USING (admin_number) "
            		+ "WHERE aid = ? ORDER BY editing_date ASC";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();

           
            
            while (rs.next()) {
                // 見つかった情報を戻り値にセット
            	List<String> tmp = new ArrayList<>();
            	tmp.add(rs.getString("name"));
            	tmp.add(rs.getString("responsibility"));
            	tmp.add(rs.getString("contact"));
            	if (rs.getBoolean("is_valid_account")) {
            		tmp.add("true");
            	} else {
            		tmp.add("false");
            	}
            	tmp.add(rs.getObject("editing_date", OffsetDateTime.class).toString());
            	
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
