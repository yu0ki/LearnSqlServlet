package account_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.AdminAccountBeans;

public class AdminAccountDeleteDAO {
	
	// データベース接続に使用する情報
		private String _hostname = "localhost";
		private String _dbname = "sampledb";
		private String _username = "postgres";
		private String _password = "postgres";
		
// 管理者のアカウントを削除する関数
public  AdminAccountDeleteDAO(AdminAccountBeans ab) throws Exception {

	// データベース接続
	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

		// adminsテーブルのis_valid_accountカラムをfalseに更新して論理削除
        String sql = "UPDATE admins SET is_valid_account = false WHERE admin_number = ? AND responsibility = ?::content";
        
        
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1, ab.getAdminNumber());
        ps.setString(2, ab.getResponsibility());

    

        int r = ps.executeUpdate();
        System.out.println(sql);
        
        
        

        if(r != 0) {
            System.out.println("論理削除成功！");
        } else {
            System.out.println("論理削除失敗");
        }

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
