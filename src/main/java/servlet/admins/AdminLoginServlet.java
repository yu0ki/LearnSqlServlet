package servlet.admins;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminAccountBeans;
import dao.AdminAccountDAO;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/admins/log_in")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admins/login.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub
		// ログインを要求してきた管理者が正しいユーザーかどうかを確認する。
    	// そのためにまずは、管理者が打ち込んできた管理者番号、担当を取得する。
    	String admin_number = request.getParameter("admin_number");
    	String responsibility = request.getParameter("responsibility");   
    	
    	
    	// 次に、取得したデータをAdminAccountBeansに保持
    	AdminAccountBeans aab = new AdminAccountBeans();
    	aab.setAdminNumber(admin_number);
    	aab.setResponsibility(responsibility);
    	
    	
    	
    	// アカウントの有無を検索
        // 検索したアカウント情報を取得
        AdminAccountDAO aa_dao = new AdminAccountDAO();
        AdminAccountBeans returnAAb = aa_dao.findAdminAccount(aab);
//        System.out.println(returnAAb == null);
       
        
        // アカウントが存在する　かつ　退会済みでない
        if(returnAAb != null && returnAAb.getIsValidAccount()) {
        	// ユーザーが正しい時
        	// 新たなセッションを生成してログイン
        	HttpSession session = request.getSession(true);
            session.setAttribute("admin", returnAAb);
            
//            RequestDispatcher rd = request.getRequestDispatcher("/user_home");
//            rd.forward(request, response);
            response.sendRedirect("/LearnSqlServlet/admins/home");
        } else {
        	// 認証失敗
//            RequestDispatcher rd = request.getRequestDispatcher("/home");
//            rd.forward(request, response);
        	response.sendRedirect("/LearnSqlServlet/home");
        }
	}

}
