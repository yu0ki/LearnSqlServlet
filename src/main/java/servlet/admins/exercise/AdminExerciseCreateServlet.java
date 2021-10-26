
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
import exercise_dao.AdminExerciseCreateDAO;

/**
 * Servlet implementation class AdminExerciseEditServlet
 */
@WebServlet("/admins/exercise/create")
public class AdminExerciseCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminExerciseCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/exercise/create.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		// ユーザーによる入力値
		String new_sentence = request.getParameter("new_sentence");
		String new_answer = request.getParameter("new_answer");
		
		// aeb作成・入力値をセット
				AdminExerciseBeans aeb = new AdminExerciseBeans();
				aeb.setSentence(new_sentence);
				aeb.setAnswer(new_answer);
				
		
		
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminExerciseCreateDAO.createExercise(aeb, aab, new_sentence, new_answer);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/exercise/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
//		System.out.println("/LearnSqlServlet/admins/exercise/show.jsp?title=" + new_title);
		
		response.sendRedirect("/LearnSqlServlet/admins/exercise/index.jsp");
	}

}
