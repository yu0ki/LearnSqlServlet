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
					
					// 採点して結果をis_correctに格納(正解のときtrue)
			    	boolean is_correct = true;
			    	
			    	// ユーザーの入力を実行
		            PreparedStatement my_ps= con.prepareStatement(my_answer);
		            ResultSet my_rs = my_ps.executeQuery();
		            // my_rsの内容を「実行結果」画面に返すために、2次元配列result_arrayに格納する
		            ArrayList<ArrayList<String>> result_array = new ArrayList<>();
		            // answer_rsの内容も、2次元配列result_arrayに格納する
		            ArrayList<ArrayList<String>> answer_array = new ArrayList<>();
		            
		            // 模範解答を実行
		            PreparedStatement answer_ps = con.prepareStatement(ueb.getAnswer());
		            ResultSet answer_rs = answer_ps.executeQuery();
		            
		            // 答えに含まれる属性一覧取得
		            ArrayList<String> fields = new ArrayList<>();
		            ResultSetMetaData rsmd= answer_rs.getMetaData();
		            
		            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
		            	   fields.add(rsmd.getColumnName(i));
		            }
		            
		            // 自分の解答に含まれる属性一覧取得
		            ArrayList<String> my_fields = new ArrayList<>();
		            ResultSetMetaData my_rsmd= my_rs.getMetaData();
		            
		            for (int i = 1; i <= my_rsmd.getColumnCount(); i++) {
		            	   my_fields.add(my_rsmd.getColumnName(i));
		            }
		            
		         
		            
		         // まずは二つの結果の属性値一覧が一致するかをチェック
	            	// 1. fieldsがmy_fieldsに含まれるかチェック
	            	ArrayList<String> field_checker = new ArrayList<String>();
	            	 for(String a : fields){
	                     for(String b : my_fields){
	                         if(a.equals(b)){
	                             field_checker.add(a);
	                             break;
	                         }
	                     }
	                 }
	            	 
	            	 if(!field_checker.equals(fields)) {
	            		 is_correct = false;
	            		 System.out.println("1");
	            	 }
	            	 
	            	 field_checker.clear();
	            	 // 2. my_fieldsがfieldsに含まれるかチェック
	            	 for(String a : my_fields){
	                     for(String b : fields){
	                         if(a.equals(b)){
	                             field_checker.add(a);
	                             break;
	                         }
	                     }
	                 }
	            	 
	            	 if(!field_checker.equals(my_fields)) {
	            		 is_correct = false; 
	            		 System.out.println("2");
	            	 }
	            	 
	            	 
	            	 
	            	 // is_correctがfalseになっていない場合(属性値集合が等しかった場合)
	            	 if (is_correct) {
	            		// result_arrayの1行目にmy_fieldsをセット
	 		            result_array.add(my_fields);
	 		            
	 		        // answer_arrayの1行目にmy_fieldsをセット
			            answer_array.add(my_fields);
	            		 
		            	 // 模範解答の実行結果をArrayListに保持
		            	 // このとき、のちの比較のために属性一覧にはmy_fieldsを使う
		            	 while(answer_rs.next()) {
				            	// result_arrayに入れる用のリスト
				             	ArrayList<String> tmp = new ArrayList<>();
				            	for(int i = 0; i < my_fields.size(); i++) {
				            		tmp.add(answer_rs.getObject(my_fields.get(i)).toString());
				            	}
				            	answer_array.add(tmp);
				          }
		            	 
		            	 
				         // ユーザーの実行結果をArrayListに保存
		           		 while(my_rs.next()) {
			            	// result_arrayに入れる用のリスト
			             	ArrayList<String> tmp = new ArrayList<>();
			            	for(int i = 0; i < my_fields.size(); i++) {
			            		tmp.add(my_rs.getObject(my_fields.get(i)).toString());
			            	}
			            	result_array.add(tmp);
			             } 
		           		 
		           		 // 2つのリストを比較
		           		 if (answer_array.size() == result_array.size()) {
		           			 for(int i = 0; i < result_array.size(); i++) {
		           				 if (is_correct) {
		           					 break;
		           				 }
		           				 
		           				 for (int j = 0; j < result_array.get(i).size(); j++) {
		           					 if (!result_array.get(i).get(i).equals(answer_array.get(i).get(j))) {
		           						 is_correct = false;
		           						 System.out.println("3");
		           						 System.out.println(result_array);
		           						 System.out.println(answer_array);
		           						 break;
		           					 }
		           				 }
		           			 }
		           		 } else {
		           			 is_correct = false;
		           			 System.out.println("4");
		           		 }
	            	 }
	            	
		            
		           
		            


		           
		            // 戻り値をmapにセット
			            returnMap.put("is_correct", is_correct);
			            returnMap.put("result", result_array);
			            returnMap.put("my_answer", my_answer);
//		         
		            
		            // 問題の解答状況を更新
		            // 最新の解答履歴だけbeansに保存することになっていたbeansも更新
		            String sql_for_answerings = "INSERT INTO answerings (eid, uid, answer, is_correct) VALUES (?, ?, ?, ?); ";
//		            		+ "SELECT * FROM answerings "
//		            		+ "WHERE eid = ? AND uid = ? AND challenge_date = (SELECT MAX(challenge_date) FROM answerings WHERE eid = ?  AND uid = ?)";
	                PreparedStatement ps_for_answerings = con.prepareStatement(sql_for_answerings);
	                ps_for_answerings.setInt(1, ueb.getEid());
	                ps_for_answerings.setInt(2, uid);
	                ps_for_answerings.setString(3, my_answer);
	                ps_for_answerings.setBoolean(4, is_correct);
	                
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
		            returnMap.put("my_answer", my_answer);
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
