package servlet.users;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account_dao.UserAccountDeleteDAO;
import beans.UserAccountBeans;

/**
 * Servlet implementation class AccountDeleteServlet
 */
@WebServlet("/confirm_account_delete")
public class AccountDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users/confirm_account_delete.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 現在ログインしているユーザーの情報を取得
				HttpSession session = request.getSession(false);
				UserAccountBeans uab = (UserAccountBeans) session.getAttribute("user");
				
				// DAOでアカウントを論理削除
				try{
					UserAccountDeleteDAO aadd = new UserAccountDeleteDAO(uab);
				} 
				catch (Exception e) {
					response.sendRedirect("/LearnSqlServlet/user_home");
					return;
				}
				
				// セッションを消去しログアウトさせてからログイン前ホームに遷移
				 session.invalidate();

			     response.sendRedirect("/LearnSqlServlet/home");
	}

}
