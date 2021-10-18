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
import dao.AdminAccountRegisterDAO;

/**
 * Servlet implementation class AdminSignInServlet
 */
@WebServlet("/admins/sign_up")
public class AdminSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSignUpServlet() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admins/signup.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub
		// 管理者の管理者番号、氏名、担当内容、連絡先を取得
		String admin_number = request.getParameter("admin_number");
    	String name = request.getParameter("name");
    	String responsibility = request.getParameter("responsibility");
    	String contact = request.getParameter("contact");
        

        // 受け取った値をビーンズにセット
        AdminAccountBeans aab = new AdminAccountBeans();
        aab.setAdminNumber(admin_number);
        aab.setName(name);
        aab.setResponsibility(responsibility);
        aab.setContact(contact);
        
        // アカウントをDBに登録
        try {
        	AdminAccountRegisterDAO aard = new AdminAccountRegisterDAO(aab);
        }
        catch (Exception e) {
        	// TODO flash実装
        	request.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html;charset=UTF-8");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("homeout.jsp");
    		dispatcher.forward(request,response);
        }
        
        
        
        

        // セッションにアカウント情報を保存
        HttpSession session = request.getSession();
        session.setAttribute("admin", aab);

        // ユーザーのホーム画面へ遷移
        response.sendRedirect("/LearnSqlServlet/admins/home");
	}

}
