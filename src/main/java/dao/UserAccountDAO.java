package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserAccountBeans;

public class UserAccountDAO {
//	ここでは、ユーザーによるログイン処理時にユーザーアカウントを探す
//	findUserAccountメソッドを実装する。
	
	


	// データベース接続に使用する情報
	private String _hostname = "localhost";
	private String _dbname = "sampledb";
	private String _username = "postgres";
	private String _password = "postgres";
	
    // ログインアカウントを探す
    public UserAccountBeans findAccount(UserAccountBeans uab) {

        // 戻り値の用意
    	UserAccountBeans returnUAb = new UserAccountBeans();

        // データベースへ接続
    	Connection con = null;
        try {
        	Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);

            String sql = "SELECT uid, nickname, registration_date, is_valid_account FROM users WHERE nickname = ?";
            PreparedStatement ps= con.prepareStatement(sql);

           
            ps.setString(1, uab.getNickname());

            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                // 見つかったアカウント情報を戻り値にセット
                returnUAb.setUid(rs.getInt("uid"));
                returnUAb.setNickname(rs.getString("nickname"));
                returnUAb.setRegisrationDate(rs.getObject("registration_date"));
                
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
        return returnUAb;
    }
	

}
