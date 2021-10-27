package announcement_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import beans.AdminAccountBeans;
import beans.AdminAnnouncementBeans;

public class AdminAnnouncementCreateDAO {
	//ここでは管理者が告知をいじるときに使うデータアクセス機能一覧を作る。
	
		// データベース接続に使用する情報
		private static String _hostname = "localhost";
		private static String _dbname = "sampledb";
		private static String _username = "postgres";
		private static String _password = "postgres";
		
	    // 告知一覧を表示する
		// 告知の題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
		
	    public static AdminAnnouncementBeans createAnnouncement(AdminAnnouncementBeans aanb, AdminAccountBeans aab, String new_sentence, String new_title) {
	        // 戻り値の用意
	    	AdminAnnouncementBeans returnAANB =  new AdminAnnouncementBeans();
	    	
	    	

	        // データベースへ接続
	    	Connection con = null;
	        try {
	        	Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
						+ ":5432/" + _dbname, _username, _password);

	           
	            
	            // 今生成しているaidが何番になるかを取得
	            String sql_sub = "SELECT nextval('announcements_aid_seq');";
	            PreparedStatement ps_sub = con.prepareStatement(sql_sub);
	            ResultSet rs_sub = ps_sub.executeQuery();
	            
	            int next_aid = 0;
	            while (rs_sub.next()) {
	            	next_aid = rs_sub.getInt("nextval");
	            }
	            
	            // nextvalしたことで進んでしまった連番型をひとつ戻し、新規作成記録を作るsqlを書く
	            
	            String sql = "alter sequence announcements_aid_seq restart with " + next_aid + ";"
	            		+ "BEGIN; INSERT INTO announcements (sentence, title) VALUES (?, ?);"
	            		+ "INSERT INTO announcement_creation_editing (aid, admin_number, responsibility) VALUES (?, ?, ?::content); COMMIT;";
	            PreparedStatement ps= con.prepareStatement(sql);
	            
	            ps.setString(1, new_sentence);
	            ps.setString(2, new_title);
	            
	            if (next_aid == 0) {
	            	System.out.println("nextval失敗");	   
	            } else {
	            	ps.setInt(3, next_aid);
	            }
	            
	            ps.setString(4, aab.getAdminNumber());
	            ps.setString(5, aab.getResponsibility());
	            
	            int r = ps.executeUpdate();

	           
	            // 戻り値をbeansにセット
	            if (r == 1) {
		            returnAANB.setSentence(new_sentence);
		            returnAANB.setAid(next_aid);
		            returnAANB.setTitle(new_title);
		            returnAANB.setAdminNumber(aab.getAdminNumber());
		            returnAANB.setResponsibility(aab.getResponsibility());
		            
		            // 最新の編集時刻を取得
		            ps = con.prepareStatement("SELECT MAX(editing_date) as max_editing_date FROM announcement_creation_editing "
		            		+ "WHERE aid = ? AND admin_number = ? AND responsibility = ?::content");
		            ps.setInt(1, next_aid);
		            ps.setString(2, aab.getAdminNumber());
		            ps.setString(3, aab.getResponsibility());
		            ResultSet rs = ps.executeQuery();
		            if(rs.next()) {
		            	returnAANB.setEditingDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            	returnAANB.setPublicationDate(rs.getObject("max_editing_date", OffsetDateTime.class));
		            }
	            } else {
	            	// announcementが見つからなかった場合はnullを返す
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
	        return returnAANB;
	    }
	    
	   
}
