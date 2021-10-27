
package servlet.admins.announcement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import announcement_dao.AdminAnnouncementEditDAO;
import announcement_dao.AdminAnnouncementShowDAO;
import beans.AdminAccountBeans;
import beans.AdminAnnouncementBeans;

/**
 * Servlet implementation class AdminAnnouncementEditServlet
 */
@WebServlet("/admins/announcement/edit")
public class AdminAnnouncementEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAnnouncementEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/announcement/edit.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// urlのパラメーターから更新対象の問題id取得
		int aid = Integer.parseInt(request.getParameter("aid"));	
//		System.out.println("ases dopost title = " + title);
		
		// ユーザーによる入力値
		String new_sentence = request.getParameter("new_sentence");
		String new_title = request.getParameter("new_title");
		
		
		// 貰ったaidからaanb作成
		AdminAnnouncementBeans aanb = AdminAnnouncementShowDAO.findAnnouncement(aid);
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminAnnouncementEditDAO.editAnnouncement(aanb, aab, new_sentence, new_title);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/announcement/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		response.sendRedirect("/LearnSqlServlet/admins/announcement/index.jsp");
	}

}
