package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.UserStoryBeans;

public class UserStoryIndexDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ストーリー一覧を表示する
	// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
	
    public List<UserStoryBeans> findAllStory(int uid) {

        // 戻り値の用意
    	List<UserStoryBeans> returnUSB =  new ArrayList<>();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT * FROM stories";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // 並べ替え用一時記憶リスト
            List<UserStoryBeans> preUSB =  new ArrayList<>();
            
            while (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
            	UserStoryBeans usb = new UserStoryBeans();
                usb.setTitle(rs.getString("title"));
                usb.setSentence(rs.getString("sentence"));  
                usb.setEid(rs.getInt("eid"));  
                usb.setNextTitle(rs.getString("next_title"));  
                
                // 閲覧状態をセット
                String sql_for_is_opened = "SELECT is_opened FROM view_stories WHERE title = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setString(1, usb.getTitle());
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                if (rs_for_is_opened.next()) {
                	System.out.println(usb.getTitle());
                	usb.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                } else {
                	usb.setIsOpened(false);
                }
                
                
                // returnUSBの中身は、ストーリーを最終章から順番に格納させる。
                // 以下で頑張ってソート
                if (usb.getNextTitle() == null) {
                	returnUSB.add(usb);
//                	System.out.println(usb.getTitle());   
                }
                else if (returnUSB.size() == 0) {
                	preUSB.add(usb);
                }
                else if (usb.getNextTitle().equals(returnUSB.get(returnUSB.size()-1).getTitle())) {
                	returnUSB.add(usb);
//                	System.out.println(usb.getTitle()); 
                } else {
                	boolean stopflag = false;
                	for (int i = 0; i < preUSB.size(); i++) {
                		if (preUSB.get(i).getNextTitle() == usb.getTitle()) {
                			returnUSB.add(preUSB.get(i));
//                			System.out.println(usb.getTitle()); 
                			preUSB.remove(i);
                			stopflag = true;
                			break;
                		}
                	}
                	if (!stopflag) {
                		preUSB.add(usb);
//                		System.out.println("pre : " + usb.getTitle());
//                		System.out.println("「"+usb.getNextTitle()+"」" + "「"+returnUSB.get(returnUSB.size()-1).getTitle() + "」");
                		
                	}
                }
                
            }
            
            if (preUSB.size() != 0) {
            	returnUSB.addAll(preUSB);
            }
            
            
            
//            ストーリーが存在しない場合nullを返す
//            if (returnUSB.size() == 0) {
//            	return null;
//            }
            
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
        return returnUSB;
    }
	
}
