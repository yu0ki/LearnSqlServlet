
package servlet.admins.exercise;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminAccountBeans;
import beans.AdminExerciseBeans;
import exercise_dao.AdminExerciseEditDAO;
import exercise_dao.AdminExerciseShowDAO;

/**
 * Servlet implementation class AdminExerciseEditServlet
 */
@WebServlet("/admins/exercise/edit")
public class AdminExerciseEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminExerciseEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/exercise/edit.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// urlのパラメーターから更新対象の問題id取得
		int eid = Integer.parseInt(request.getParameter("eid"));	
//		System.out.println("ases dopost title = " + title);
		
		// ユーザーによる入力値
		String new_sentence = request.getParameter("new_sentence");
		String new_answer = request.getParameter("new_answer");
		
		
		// 貰ったeidからaeb作成
		AdminExerciseBeans aeb = AdminExerciseShowDAO.findExercise(eid);
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminExerciseEditDAO.editExercise(aeb, aab, new_sentence, new_answer);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/exercise/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		response.sendRedirect("/LearnSqlServlet/admins/exercise/index.jsp");
	}

}
