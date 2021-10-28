package exercise_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.UserExerciseBeans;

public class AnswerCheckDAO {
	//ここではユーザーが送ってきた問題への解答を採点する
	
			// データベース接続に使用する情報
			private static String _hostname = "localhost";
			private static String _dbname = "sampledb";
			private static String _username = "postgres";
			private static String _password = "postgres";
			
		    // 採点結果を返却する関数
			// ユーザーが入力したSQLの実行結果をString(key = result)
			// 正誤をboolean(key = is_correct)としてMapに入れて返す
			
		    public static Map<String, Object> answerCheck(String my_answer, UserExerciseBeans ueb, int uid) {
		        // 戻り値の用意
		    	Map<String, Object> returnMap =  new HashMap<>();
		    	
		    	
		    	

		        // データベースへ接続
		    	Connection con = null;
		        try {
		        	Class.forName("org.postgresql.Driver");
					con = DriverManager.getConnection("jdbc:postgresql://" + _hostname
							+ ":5432/" + _dbname, _username, _password);
					
					// 問題用のテーブルは全部一時テーブルで実装
					// 効率は悪いが、アクセスのたびに全テーブルを作る
					// アクセスがあるごとに作成する
					// islandsテーブル
					
					String create_islands = "CREATE TEMP TABLE islands ("
											+ "island_id SERIAL PRIMARY KEY, "
											+ "name text NOT NULL, "
											+ "latitude integer NOT NULL, "
											+ "longitude integer NOT NULL, "
											+ "area integer, "
											+ "perimeter integer, "
											+ "do_people_live boolean NOT NULL, "
											+ "distance_to_manned_island integer, "
											+ "UNIQUE (latitude, longitude)"
											+ ");"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('ハート島', 10, 134, 222, 155, false, 100);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('スペード島', 100, 125, 342, 300, true, 25);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('ダイヤ島', 22, 90, 40, 12, true, 10);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('クローバー島', 30, 151, 365, 49, false, 200);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('春島', 50, 89, 112, 39, true, 30);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('夏島', 43, 100, 20, 8, false, 50);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('秋島', 15, 128, 30, 11, true, 20);"
											+ "INSERT INTO islands (name, latitude, longitude, area, perimeter, do_people_live, distance_to_manned_island) "
											+ "VALUES ('冬島', 55, 140, 222, 50, false, 200);";
					
					PreparedStatement prepare_to_check_ps = con.prepareStatement(create_islands);
					prepare_to_check_ps.executeUpdate();
					
					// 採点して結果をresultに格納(正解のときtrue)
			    	boolean result;
			    	
			    	// ユーザーの入力を実行
		            PreparedStatement my_ps= con.prepareStatement(my_answer);
		            ResultSet my_rs = my_ps.executeQuery();
		            
		            // 模範解答を実行
		            PreparedStatement answer_ps = con.prepareStatement(ueb.getAnswer());
		            ResultSet answer_rs = answer_ps.executeQuery();
		            
		            // 答えに含まれる属性一覧取得
		            List<String> fields = new ArrayList<>();
		            ResultSetMetaData rsmd= answer_rs.getMetaData();
		            
		            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
		            	   fields.add(rsmd.getColumnName(i));
		            }
		            
		            // 2つの実行結果を比較して採点
		            while (true) {
		            	// まずはnext()して読み込んでいない要素があるか確認
		            	boolean my_next = my_rs.next();
		            	boolean answer_next = answer_rs.next();
		            	if (my_next == false && answer_next == false) {
		            		// どちらも次の要素が見つからないなら採点終了
		            		System.out.println("採点1");
		            		result = true;
		            		break;
		            	}
		            	else if (my_next == answer_next) {
		            		// レコードを比較し、正しいことを確認
		            		boolean break_flag = false;
		            		for (int i = 0; i < fields.size(); i++) {
		            			String my = my_rs.getObject(fields.get(i)).toString();
		            			String an = answer_rs.getObject(fields.get(i)).toString();
		            			if (!my.equals(an)) {
		            				System.out.println(my_rs.getObject(fields.get(i)));
		            				System.out.println(answer_rs.getObject(fields.get(i)));
		            				break_flag = true;
		            				break;
		            			} 
		            		}
		            		
		            		System.out.println("採点2");
		            		
		            		if (break_flag) {
		            			result = false;
		            			break;
		            		}
		            		
		            	} else {
		            		// 上記に引っかからない場合、2つの実行結果は同じでない
		            		// よって不正解とみなす
		            		System.out.println("採点3");
		            		result = false;
		            		break;
		            	}
		            }

		           
		            // 戻り値をmapにセット
//		            if (rs.next()) {
			            returnMap.put("is_correct", result);
			            returnMap.put("result", my_rs);
			            returnMap.put("fields", fields);
//		            } else {
		            	// 問題が見つからなかった場合はnullを返す
//		            	return null;
//		            }
		            
		            // 問題の解答状況を更新
		            // 最新の解答履歴だけbeansに保存することになっていたbeansも更新
		            String sql_for_answerings = "INSERT INTO answerings (eid, uid, answer, is_correct) VALUES (?, ?, ?, ?); ";
//		            		+ "SELECT * FROM answerings "
//		            		+ "WHERE eid = ? AND uid = ? AND challenge_date = (SELECT MAX(challenge_date) FROM answerings WHERE eid = ?  AND uid = ?)";
	                PreparedStatement ps_for_answerings = con.prepareStatement(sql_for_answerings);
	                ps_for_answerings.setInt(1, ueb.getEid());
	                ps_for_answerings.setInt(2, uid);
	                ps_for_answerings.setString(3, my_answer);
	                ps_for_answerings.setBoolean(4, result);
	                
	                String sql_for_return = "SELECT * FROM answerings WHERE eid = ? AND uid = ? AND challenge_date = (SELECT MAX(challenge_date) FROM answerings WHERE eid = ?  AND uid = ?)";
	                PreparedStatement ps_for_return = con.prepareStatement(sql_for_return);
	                ps_for_return.setInt(1, ueb.getEid());
	                ps_for_return.setInt(2, uid);
	                ps_for_return.setInt(3, ueb.getEid());
	                ps_for_return.setInt(4, uid);
	                ps_for_answerings.executeUpdate();
	                ResultSet rs_for_return = ps_for_return.executeQuery();
	                if (rs_for_return.next()) {
	                	ueb.setChallengeDate(rs_for_return.getObject("challenge_date", OffsetDateTime.class));
	                	ueb.setMyAnswer(rs_for_return.getString("answer"));
	                	ueb.setIsCorrect(rs_for_return.getBoolean("is_correct"));
	                } 
		            
		            
		            
//		            
		            
		        } catch (Exception e) {
					e.printStackTrace();
					returnMap.put("result", e.getMessage());
					returnMap.put("is_correct", false);
				} finally {
					try {
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		        return returnMap;
		    }

}
