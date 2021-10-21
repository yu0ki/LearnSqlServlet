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
		
public  AdminAccountDeleteDAO(AdminAccountBeans ab) throws Exception {

	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

		// adminsの管理者番号は ON UPDATE CASCADEなので、admin_namesを更新すると同時に更新されるはず。
        String sql = "UPDATE admins SET is_valid_account = false WHERE admin_number = ? AND responsibility = ?::content";
        
        
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1, ab.getAdminNumber());
        ps.setString(2, ab.getResponsibility());

    

        int r = ps.executeUpdate();
        
        
        

//        if(r != 0) {
            System.out.println("論理削除成功！");
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
