package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.AdminAccountBeans;

public class AdminAccountDAO {
//	ここでは、管理者によるログイン処理時に管理者アカウントを探す
//	findAdminAccountメソッドを実装する。
	
	


	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ログインアカウントを探す
    public AdminAccountBeans findAdminAccount(AdminAccountBeans aab) {

        // 戻り値の用意
    	AdminAccountBeans returnAAb = new AdminAccountBeans();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT admin_number, responsibility, contact, name FROM admins NATURAL JOIN admin_names WHERE admin_number = ? AND responsibility = ?::content";
            PreparedStatement ps= con.prepareStatement(sql);

           
            ps.setString(1, aab.getAdminNumber());
            System.out.println(aab.getAdminNumber());
            ps.setString(2, aab.getResponsibility());
            System.out.println(aab.getResponsibility());
//            System.out.println(sql);

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
                returnAAb.setAdminNumber(rs.getString("admin_number"));
                returnAAb.setResponsibility(rs.getString("responsibility"));
                returnAAb.setContact(rs.getString("contact"));
                returnAAb.setName(rs.getString("name"));
                
            } else {
                // アカウントがなければnullを返す
                return null;
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
        return returnAAb;
    }
	

}
