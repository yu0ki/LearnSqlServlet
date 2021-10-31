
package servlet.users.announcement;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import announcement_dao.UserAnnouncementViewsDAO;
import beans.UserAccountBeans;

/**
 * Servlet implementation class AdminAnnouncementEditServlet
 */
@WebServlet("/users/announcement/show")
public class UserAnnouncementShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAnnouncementShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// パラメーターを取得
		int aid = Integer.parseInt(request.getParameter("aid"));
		int uid = ((UserAccountBeans) request.getSession(false).getAttribute("user")).getUid();
		boolean new_is_opened = Boolean.parseBoolean(request.getParameter("new_is_opened"));	
		
		// 閲覧履歴をつける
		UserAnnouncementViewsDAO.setViewAnnouncement(aid, uid, (new_is_opened));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/announcement/show.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
