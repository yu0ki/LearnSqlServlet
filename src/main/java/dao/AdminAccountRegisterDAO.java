package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

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

        String sql = "BEGIN; ";
        
        
//        ここで、新規登録者の名前が既にadmin_namesに登録されているかどうかを確認する。
        String sql2 = "SELECT * FROM admin_names WHERE admin_number = \' " + ab.getAdminNumber() + "\'"; 
        Statement stmt = con.createStatement(); 
        ResultSet rs2 = stmt.executeQuery(sql2);
        
        List<String> existing_admin = Arrays.asList();
        while(rs2.next()) {
        	existing_admin.add(rs2.getString("admin_number"));
        }
//          if (existing_admin.isEmpty()) {
//        	  System.out.println("true");
//          }
       
        
        if (!existing_admin.isEmpty()) {
        	sql += "INSERT INTO admin_names VALUES ( \'" + ab.getAdminNumber() + "\',  \'" + ab.getName() + "\') ;";
//        	PreparedStatement ps_sub = con.prepareStatement(sql);
//        	try {
//        		int r_sub = ps_sub.executeUpdate();
//        		if(r_sub == 0) {
//        			throw new Exception("admin_name登録失敗");
//        		}
//        	}
//        	catch (Exception e) {
//        		
//        	}
        } 
        
        sql += " INSERT INTO admins VALUES (?, ?::content, ?); COMMIT;";
        
        PreparedStatement ps= con.prepareStatement(sql);
        ps.setString(1, ab.getAdminNumber());
        ps.setString(2, ab.getResponsibility());
        ps.setString(3, ab.getContact());
        
//        PreparedStatement ps= con.prepareStatement(sql);

    

        int r = ps.executeUpdate();

//        if(r != 0) {
            System.out.println("新規登録成功！");
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
