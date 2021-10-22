package servlet.admins;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account_dao.AdminAccountEditDAO;
import beans.AdminAccountBeans;

/**
 * Servlet implementation class AdminAccountEditServlet
 */
@WebServlet("/admins/account/edit")
public class AdminAccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAccountEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/account_edit.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String new_admin_number = request.getParameter("admin_number");
		String new_name = request.getParameter("name");
		String new_responsibility = request.getParameter("responsibility");
		String new_contact = request.getParameter("contact");
		
		AdminAccountBeans aab = (AdminAccountBeans) request.getSession(false).getAttribute("admin");
		
		try {
			AdminAccountEditDAO aaed = new AdminAccountEditDAO(aab, new_admin_number, new_name, new_responsibility, new_contact);
		}
		catch (Exception e) {
			//更新失敗時はターミナルに表示
			System.out.println("更新失敗");
		}
		finally {
			response.sendRedirect("/LearnSqlServlet/admins/home");
		}
	}

}
