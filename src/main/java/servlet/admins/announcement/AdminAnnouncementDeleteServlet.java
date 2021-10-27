package servlet.admins.announcement;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import announcement_dao.AdminAnnouncementDeleteDAO;

/**
 * Servlet implementation class AdminAnnouncementDeleteServlet
 */
@WebServlet("/admins/announcement/delete")
public class AdminAnnouncementDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAnnouncementDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int aid = Integer.parseInt(request.getParameter("aid"));
		try {
			AdminAnnouncementDeleteDAO.deleteAnnouncement(aid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("/LearnSqlServlet/admins/announcement/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
