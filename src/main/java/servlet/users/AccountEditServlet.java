package servlet.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import account_dao.UserAccountEditDAO;
import beans.UserAccountBeans;

/**
 * Servlet implementation class AdminAccountEditServlet
 */
@WebServlet("/account/edit")
public class AccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String new_nickname = request.getParameter("nickname");
		
		
		UserAccountBeans uab = (UserAccountBeans) request.getSession(false).getAttribute("user");
		
		try {
			UserAccountEditDAO aaed = new UserAccountEditDAO(uab, new_nickname);
		}
		catch (Exception e) {
			//更新失敗時はターミナルに表示
			System.out.println("更新失敗");
		}
		finally {
			response.sendRedirect("/LearnSqlServlet/user_home");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
