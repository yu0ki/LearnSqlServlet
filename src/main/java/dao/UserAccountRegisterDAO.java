package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.UserAccountBeans;

public class UserAccountRegisterDAO {
	
	// データベース接続に使用する情報
		private String _hostname = "localhost";
		private String _dbname = "sampledb";
		private String _username = "postgres";
		private String _password = "postgres";
		
public  UserAccountRegisterDAO(UserAccountBeans ab) throws Exception {

	Connection con = null;
	try {
    	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
				+ ":5432/" + _dbname, _username, _password);

        String sql = "INSERT INTO users (nickname) VALUES (?)";
        PreparedStatement ps= con.prepareStatement(sql);

        ps.setString(1, ab.getNickname());

        int r = ps.executeUpdate();

//        if(r != 0) {
//        rはinsert, delete, update以外のときは0を返す。今回はbegin commitで囲んだので、0になってしまっているようだ
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
