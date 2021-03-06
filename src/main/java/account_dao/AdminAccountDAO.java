package account_dao;

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
	private static String _hostname = "localhost";
	private static String _dbname = "sampledb";
	private static String _username = "postgres";
	private static String _password = "postgres";
	
    // ログインアカウントを探す
    public static AdminAccountBeans findAdminAccount(AdminAccountBeans aab) {

        // 戻り値の用意
    	AdminAccountBeans returnAAb = new AdminAccountBeans();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

			// adminsテーブルに該当のアカウントが存在するかどうかを確認
            String sql = "SELECT admin_number, responsibility, contact, is_valid_account, name FROM admins NATURAL JOIN admin_names WHERE admin_number = ? AND responsibility = ?::content";
            PreparedStatement ps= con.prepareStatement(sql);

           
            ps.setString(1, aab.getAdminNumber());
            ps.setString(2, aab.getResponsibility());

            ResultSet rs = ps.executeQuery();
            System.out.println(sql);


            if (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
                returnAAb.setAdminNumber(rs.getString("admin_number"));
                returnAAb.setResponsibility(rs.getString("responsibility"));
                returnAAb.setContact(rs.getString("contact"));
                returnAAb.setName(rs.getString("name"));
                returnAAb.setIsValidAccount(rs.getBoolean("is_valid_account"));
                
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
