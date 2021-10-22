package story_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.AdminStoryBeans;

public class AdminStoryIndexDAO {
	//ここでは管理者がストーリーをいじるときに使うデータアクセス機能一覧を作る。
	
	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ストーリー一覧を表示する
	// ストーリーの題名と最終編集者(名前と管理者番号と担当内容と連絡先)とその日時を取得
	
    public List<AdminStoryBeans> findAllStory() {

        // 戻り値の用意
    	List<AdminStoryBeans> returnASB =  new ArrayList<>();

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
            List<AdminStoryBeans> preASB =  new ArrayList<>();
            
            while (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
            	AdminStoryBeans asb = new AdminStoryBeans();
                asb.setTitle(rs.getString("title"));
                asb.setSentence(rs.getString("sentence"));  
                asb.setEid(rs.getInt("eid"));  
                asb.setNextTitle(rs.getString("next_title"));  
                
                // returnASBの中身は、ストーリーを最終章から順番に格納させる。
                // 以下で頑張ってソート
                if (asb.getNextTitle() == null) {
                	returnASB.add(asb);
                	System.out.println(asb.getTitle());                }
                else if (asb.getNextTitle().equals(returnASB.get(returnASB.size()-1).getTitle())) {
                	returnASB.add(asb);
                	System.out.println(asb.getTitle()); 
                } else {
                	boolean stopflag = false;
                	for (int i = 0; i < preASB.size(); i++) {
                		if (preASB.get(i).getNextTitle() == asb.getTitle()) {
                			returnASB.add(preASB.get(i));
                			System.out.println(asb.getTitle()); 
                			preASB.remove(i);
                			stopflag = true;
                			break;
                		}
                	}
                	if (!stopflag) {
                		preASB.add(asb);
//                		System.out.println("pre : " + asb.getTitle());
//                		System.out.println("「"+asb.getNextTitle()+"」" + "「"+returnASB.get(returnASB.size()-1).getTitle() + "」");
                		
                	}
                }
                
            }
            
            if (preASB.size() != 0) {
            	returnASB.addAll(preASB);
            }
            
            
            
//            ストーリーが存在しない場合nullを返す
//            if (returnASB.size() == 0) {
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
        return returnASB;
    }
	
}
