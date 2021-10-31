package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import beans.UserAnnouncementBeans;

public class UserAnnouncementIndexDAO {
	//ここではユーザーが告知一覧を見るのに必要なデータを取得する。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // 問題一覧を表示する
	
    public List<UserAnnouncementBeans> findAllAnnouncement(int uid) {

        // 戻り値の用意
    	List<UserAnnouncementBeans> returnUAB =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

			// 告知一覧を最新のものから順に取得
            String sql = "SELECT * FROM announcements ORDER BY publication_date DESC";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            
           
         
            
            
            while (rs.next()) {
                // 見つかった情報を戻り値にセット
            	UserAnnouncementBeans uab = new UserAnnouncementBeans();
                uab.setAid(rs.getInt("aid"));
                uab.setSentence(rs.getString("sentence"));    
                uab.setTitle(rs.getString("title"));
                uab.setPublicationDate(rs.getObject("publication_date", OffsetDateTime.class));
                
             // さらに閲覧状態を取得
	            String sql_for_is_opened = "SELECT is_opened FROM view_announcements WHERE aid = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setInt(1, uab.getAid());
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                if (rs_for_is_opened.next()) {
                	uab.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                	if (!rs_for_is_opened.getBoolean("is_opened")) {
                		uab.setIsFocused(true);
                	} else {
                		uab.setIsFocused(false);
                	}
                } else {
                	// 初閲覧（閲覧記録がない）場合は初期値を設定
                	uab.setIsOpened(false);
                	uab.setIsFocused(false);
                }
//	            
                
                returnUAB.add(uab);
                
                
                
            }
            
          
            
            
//            問題が存在しない場合nullを返す
            if (returnUAB.size() == 0) {
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
        return returnUAB;
    }
	
}
