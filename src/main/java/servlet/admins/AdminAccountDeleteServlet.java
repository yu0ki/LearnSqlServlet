package servlet.admins;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account_dao.AdminAccountDeleteDAO;
import beans.AdminAccountBeans;

/**
 * Servlet implementation class AdminAccountDeleteServlet
 */
@WebServlet("/admins/confirm_account_delete")
public class AdminAccountDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAccountDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/confirm_account_delete.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		// 現在ログインしている管理者の情報を取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");
		
		// DAOでアカウントを論理削除
		try{
			AdminAccountDeleteDAO aadd = new AdminAccountDeleteDAO(aab);
		} 
		catch (Exception e) {
			response.sendRedirect("/LearnSqlServlet/admins/home");
			return;
		}
		
		// セッションを消去しログアウトさせてからログイン前ホームに遷移
		 session.invalidate();

	     response.sendRedirect("/LearnSqlServlet/home");
		
		
	}

}
