package account_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.AdminAccountBeans;

public class AdminAccountRegisterDAO {
	
	// データベース接続に使用する情報
		private String _hostname = "localhost";
		private String _dbname = "sampledb";
		private String _username = "postgres";
		private String _password = "postgres";
		
public  AdminAccountRegisterDAO(AdminAccountBeans ab) throws Exception {

	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

        String sql = "";
        
        
//        ここで、新規登録者の名前が既にadmin_namesに登録されているかどうかを確認する。
        // これは、ある管理者が別の担当内容で既にアカウントを登録していた時に発生する
        String sql2 = "SELECT * FROM admin_names WHERE admin_number = \' " + ab.getAdminNumber() + "\'"; 
        System.out.println(sql2);
        Statement stmt = con.createStatement(); 
        ResultSet rs2 = stmt.executeQuery(sql2);
        
       

        // 管理者がまだ別の担当内容でもアカウントを持っていない場合のみ、admin_namesに名前を登録
        if (!rs2.next()) {
        	sql += "INSERT INTO admin_names VALUES ( \'" + ab.getAdminNumber() + "\',  \'" + ab.getName() + "\') ;";
    	}
        
        // adminsテーブルに情報を登録
        sql += " INSERT INTO admins (admin_number, responsibility, contact) VALUES (?, ?::content, ?); ";
        
        PreparedStatement ps= con.prepareStatement(sql);
        System.out.println(sql);
        ps.setString(1, ab.getAdminNumber());
        ps.setString(2, ab.getResponsibility());
        ps.setString(3, ab.getContact());

    

        int r = ps.executeUpdate();

        if(r != 0) {
            System.out.println("新規登録成功！");
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
