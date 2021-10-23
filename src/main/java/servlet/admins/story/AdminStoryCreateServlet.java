
package servlet.admins.story;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminAccountBeans;
import beans.AdminStoryBeans;
import story_dao.AdminStoryCreateDAO;

/**
 * Servlet implementation class AdminStoryEditServlet
 */
@WebServlet("/admins/story/create")
public class AdminStoryCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStoryCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/story/create.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		// ユーザーによる入力値
		String new_title = request.getParameter("new_title");
		String new_sentence = request.getParameter("new_sentence");
		String new_next_title = request.getParameter("new_next_title");
		int new_eid = 0;
		
		// asb作成・入力値をセット
				AdminStoryBeans asb = new AdminStoryBeans();
				asb.setTitle(new_title);
				asb.setSentence(new_sentence);
				asb.setNextTitle(new_next_title);
				
		try {
			new_eid = Integer.parseInt(request.getParameter("new_eid"));
			asb.setEid(new_eid);
		} catch (Exception e) {
			// parseIntできなかったときは何もセットしない
			
		}
		
		
		
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminStoryCreateDAO.createStory(asb, aab, new_title, new_sentence, new_eid, new_next_title);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/story/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
//		System.out.println("/LearnSqlServlet/admins/story/show.jsp?title=" + new_title);
		
		response.sendRedirect("/LearnSqlServlet/admins/story/index.jsp");
	}

}
