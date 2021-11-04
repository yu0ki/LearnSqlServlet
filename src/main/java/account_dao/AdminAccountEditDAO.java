package account_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.AdminAccountBeans;

public class AdminAccountEditDAO {
	
	// データベース接続に使用する情報
		private String _hostname = "localhost";
		private String _dbname = "sampledb";
		private String _username = "postgres";
		private String _password = "postgres";
		
public  AdminAccountEditDAO(AdminAccountBeans ab, String new_admin_number, String new_name, String new_responsibility, String new_contact) throws Exception {

	// データベースに接続
	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

		// adminsの管理者番号は ON UPDATE CASCADEなので、admin_namesを更新すると同時に更新されるはずなので、
		// 2個目のUDPATEでは更新していない
        String sql = "BEGIN; UPDATE admin_names SET admin_number = ?, name = ? WHERE admin_number = ?; ";
        sql += "UPDATE admins SET responsibility = ?::content, contact = ? where admin_number = ? AND responsibility = ?::content; COMMIT;";
        
        
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1, new_admin_number);
        ps.setString(2, new_name);
        ps.setString(3, ab.getAdminNumber());
        ps.setString(4, new_responsibility);
        ps.setString(5, new_contact);
        ps.setString(6, ab.getAdminNumber());
        ps.setString(7, ab.getResponsibility());

    

        int r = ps.executeUpdate();
        System.out.println(sql);
        
        
        // benasの中身を変更
        ab.setAdminNumber(new_admin_number);
        ab.setName(new_name);
        ab.setResponsibility(new_responsibility);
        ab.setContact(new_contact);

        if(r != 0) {
            System.out.println("更新成功！");
        } else {
            System.out.println("新規登録失敗");
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
