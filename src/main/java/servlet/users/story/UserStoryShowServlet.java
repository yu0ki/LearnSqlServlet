
package servlet.users.story;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserAccountBeans;
import beans.UserExerciseBeans;
import beans.UserStoryBeans;
import exercise_dao.AnswerCheckDAO;
import exercise_dao.UserExerciseShowDAO;
import story_dao.UserStoryShowDAO;

/**
 * Servlet implementation class AdminStoryEditServlet
 */
@WebServlet("/users/story/show")
public class UserStoryShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserStoryShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		System.out.println("通過しました1");
//		if (request.getSession(false).getAttribute("result_map") != null) {
//			request.getSession(false).removeAttribute("result_map");
//			System.out.println("通過しました2");
//		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/story/show.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 採点結果をjspに受け渡すためにsession に保存
				// もし以前にも問題を解いていた場合はgetに飛ばす
//				if (request.getSession(false).getAttribute("result_map") != null) {
//					doGet(request,response);
//					return;
//				}
				
		
		
		// ユーザーによる入力値
		String my_answer = request.getParameter("my_answer");
		
		// urlパラメータなど
		String title = request.getParameter("title");
		int uid = ((UserAccountBeans)(request.getSession(false).getAttribute("user"))).getUid();
		// 該当ストーリー検索
		UserStoryBeans usb = UserStoryShowDAO.findStory(title, uid);
		
		int eid = usb.getEid();
		
		// ueb作成
		UserExerciseBeans ueb = UserExerciseShowDAO.findExercise(eid, uid);
			
		
		// 採点 by DAO
		Map<String, Object> result_map = AnswerCheckDAO.answerCheck(my_answer, ueb, uid);
		for (String key : result_map.keySet()) {
	        System.out.println(key + ":" + result_map.get(key));
	    }
		
		request.getSession(false).setAttribute("result_map", result_map);
		
		
		// redirectのときに文字化けするのを防ぐ処理
		// UTF-8でコーディングされているものを、iso-8859-1に変換
		byte[] tmp = new String(title).getBytes("UTF-8");
	    String str = new String(tmp, "ISO-8859-1");
//	    System.out.println(str);
	    
	    
	    
	    
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/story/show.jsp");
//		dispatcher.forward(request,response);
		response.sendRedirect("/LearnSqlServlet/users/story/show.jsp?title="+str);
	}
	
	

}
