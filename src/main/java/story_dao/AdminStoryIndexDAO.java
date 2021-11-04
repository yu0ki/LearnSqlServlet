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
	//ここでは管理者がストーリーを一覧表示するときに使うデータアクセス機能一覧を作る。
	
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
            System.out.println(sql);
            
            // 並べ替え用一時記憶リスト
            List<AdminStoryBeans> preASB =  new ArrayList<>();
            
// 
//            
//       見つけたストーリーは最終章から順に並べるので、ResultSetから値を取り出しつつ並べ替えも行う     
//            
            while(rs.next()) {
            	// 見つかったアカウント情報を戻り値にセット
            	AdminStoryBeans asb = new AdminStoryBeans();
                asb.setTitle(rs.getString("title"));
                asb.setSentence(rs.getString("sentence"));  
                asb.setEid(rs.getInt("eid"));  
                asb.setNextTitle(rs.getString("next_title"));  
                
            	// まずは一番最後のストーリー(next_title == null)を探す
                if (asb.getNextTitle() == null) {
                	returnASB.add(asb);
                } else {
                	preASB.add(asb);
                }         	
            }
            
            // 次にpreASBが空になるまで
            // returnASBの要素のタイトルと等しいNextTitleを持つpreASBの要素を探しては
            // returnASBの該当の要素の直後に移動させることを繰り返す
            while(preASB.size() > 0) {
            	for(int i = 0; i < returnASB.size(); i++) {
            		for(int j = 0; j < preASB.size(); j++) {
            			if (returnASB.get(i).getTitle().equals(preASB.get(j).getNextTitle())) {
            				returnASB.add(i+1, preASB.get(j));
            				preASB.remove(j);
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
        return returnASB;
    }
	
}
