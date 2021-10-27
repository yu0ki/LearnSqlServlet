package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.AdminAnnouncementBeans;

public class AdminAnnouncementIndexDAO {
	//ここでは管理者が告知をいじるときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // 告知一覧を表示する
	// 告知の題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
	
    public List<AdminAnnouncementBeans> findAllAnnouncement() {

        // 戻り値の用意
    	List<AdminAnnouncementBeans> returnAANB =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT * FROM announcements";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            
           
         
            
            
            while (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
            	AdminAnnouncementBeans aanb = new AdminAnnouncementBeans();
                aanb.setAid(rs.getInt("aid"));
                aanb.setSentence(rs.getString("sentence"));    
                aanb.setTitle(rs.getString("title"));  
                aanb.setPublicationDate(rs.getObject("publication_date", OffsetDateTime.class));
                
                returnAANB.add(aanb);
                
                
                
            }
            
          
            
            
//            告知が存在しない場合nullを返す
            if (returnAANB.size() == 0) {
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
        return returnAANB;
    }
	
}
