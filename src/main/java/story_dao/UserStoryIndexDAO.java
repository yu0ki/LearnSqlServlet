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
	//ここではユーザーがストーリーを一覧表示するときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ストーリー一覧を表示する
	
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
            System.out.println(sql);

            // 並べ替え用一時記憶リスト
            List<UserStoryBeans> preUSB =  new ArrayList<>();
            
            while (rs.next()) {
                // 見つかった情報を戻り値にセット
            	UserStoryBeans usb = new UserStoryBeans();
                usb.setTitle(rs.getString("title"));
                usb.setSentence(rs.getString("sentence"));  
                usb.setEid(rs.getInt("eid"));  
                usb.setNextTitle(rs.getString("next_title"));  
                
             // さらに閲覧状態を取得
	            String sql_for_is_opened = "SELECT is_opened FROM view_stories WHERE title = ? AND uid = ?";
                PreparedStatement ps_for_is_opened = con.prepareStatement(sql_for_is_opened);
                ps_for_is_opened.setString(1, usb.getTitle());
                ps_for_is_opened.setInt(2, uid);
                ResultSet rs_for_is_opened = ps_for_is_opened.executeQuery();
                System.out.println(sql_for_is_opened);
                if (rs_for_is_opened.next()) {
                	usb.setIsOpened(rs_for_is_opened.getBoolean("is_opened"));
                	
                } else {
                	// 初閲覧（閲覧記録がない）場合は初期値を設定
                	usb.setIsOpened(false);
                }
                
             // ここからはストーリーを最新話から順に並べる作業
                
             // まずは一番最後のストーリー(next_title == null)を探す
                if (usb.getNextTitle() == null) {
                	returnUSB.add(usb);
                } else {
                	preUSB.add(usb);
                }        
                
            }  
            
            // 次にpreUSBが空になるまで
            // returnUSBの要素のタイトルと等しいNextTitleを持つpreUSBの要素を探しては
            // returnUSBの該当の要素の直後に移動させることを繰り返す
            while(preUSB.size() > 0) {
            	for(int i = 0; i < returnUSB.size(); i++) {
            		for(int j = 0; j < preUSB.size(); j++) {
            			if (returnUSB.get(i).getTitle().equals(preUSB.get(j).getNextTitle())) {
            				returnUSB.add(i+1, preUSB.get(j));
            				preUSB.remove(j);
            			}
            		}
            	}
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
        return returnUSB;
    }
	
}
