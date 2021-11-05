
package servlet.users.exercise;

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
import exercise_dao.AnswerCheckDAO;
import exercise_dao.UserExerciseShowDAO;

/**
 * Servlet implementation class AdminExerciseEditServlet
 */
@WebServlet("/users/exercise/show")
public class UserExerciseShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserExerciseShowServlet() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/exercise/show.jsp");
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
		
		int eid = Integer.parseInt(request.getParameter("eid"));
		int uid = ((UserAccountBeans)(request.getSession(false).getAttribute("user"))).getUid();
		// ueb作成・入力値をセット
		UserExerciseBeans ueb = UserExerciseShowDAO.findExercise(eid, uid);
			
		
		// 採点 by DAO
		Map<String, Object> result_map = AnswerCheckDAO.answerCheck(my_answer, ueb, uid);
		for (String key : result_map.keySet()) {
	        System.out.println(key + ":" + result_map.get(key));
	    }
		
		request.getSession(false).setAttribute("result_map", result_map);
		
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/exercise/show.jsp");
//		dispatcher.forward(request,response);
		response.sendRedirect("/LearnSqlServlet/users/exercise/show.jsp?eid="+eid);
	}

}
