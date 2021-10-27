
package servlet.admins.announcement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import announcement_dao.AdminAnnouncementCreateDAO;
import beans.AdminAccountBeans;
import beans.AdminAnnouncementBeans;

/**
 * Servlet implementation class AdminAnnouncementEditServlet
 */
@WebServlet("/admins/announcement/create")
public class AdminAnnouncementCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAnnouncementCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/announcement/create.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		// ユーザーによる入力値
		String new_sentence = request.getParameter("new_sentence");
		String new_title = request.getParameter("new_title");
		
		// aanb作成・入力値をセット
				AdminAnnouncementBeans aanb = new AdminAnnouncementBeans();
				aanb.setSentence(new_sentence);
				aanb.setTitle(new_title);
				
		
		
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminAnnouncementCreateDAO.createAnnouncement(aanb, aab, new_sentence, new_title);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/announcement/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
//		System.out.println("/LearnSqlServlet/admins/announcement/show.jsp?title=" + new_title);
		
		response.sendRedirect("/LearnSqlServlet/admins/announcement/index.jsp");
	}

}
