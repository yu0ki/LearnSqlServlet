package account_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.UserAccountBeans;

public class UserAccountEditDAO {
	
	// データベース接続に使用する情報
		private String _hostname = "localhost";
		private String _dbname = "sampledb";
		private String _username = "postgres";
		private String _password = "postgres";
		
public  UserAccountEditDAO(UserAccountBeans ab, String new_nickname) throws Exception {

	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

		// adminsの管理者番号は ON UPDATE CASCADEなので、admin_namesを更新すると同時に更新されるはず。
        String sql = "UPDATE users SET nickname = ? WHERE uid = ?; ";
        
        
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1, new_nickname);
        ps.setInt(2, ab.getUid());

    

        int r = ps.executeUpdate();
        
        
        // benasの中身を変更
        ab.setNickname(new_nickname);

//        if(r != 0) {
            System.out.println("更新成功！");
//        } else {
//            System.out.println("新規登録失敗");
//        }

	} catch (Exception e) {
		e.printStackTrace();
		throw e;
		
	} finally {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
}
