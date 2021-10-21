package servlet.users;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account_dao.UserAccountDAO;
import account_dao.UserAccountRegisterDAO;
import beans.UserAccountBeans;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/sign_up")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users/signup.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub
		// ユーザーのニックネームを取得
    	String nickname = request.getParameter("nickname");
        

        // 受け取った値をビーンズにセット
        UserAccountBeans uab = new UserAccountBeans();
        uab.setNickname(nickname);
        
        // アカウントをDBに登録
        try {
        	UserAccountRegisterDAO uard = new UserAccountRegisterDAO(uab);
        }
        catch (Exception e) {
        	// TODO flash実装
        	request.setCharacterEncoding("UTF-8");
    		response.setContentType("text/html;charset=UTF-8");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("homeout.jsp");
    		dispatcher.forward(request,response);
        }
        
        
        
        // 自動登録された内容をbeansにいれる
        UserAccountDAO uad = new UserAccountDAO();
        UserAccountBeans user = uad.findUserAccount(uab);
        uab.setUid(user.getUid());
        uab.setRegisteredDate(user.getRegisteredDate());
        uab.setIsValidAccount(user.getIsValidAccount());

        // セッションにアカウント情報を保存
        HttpSession session = request.getSession();
        session.setAttribute("user", uab);

        // ユーザーのホーム画面へ遷移
        response.sendRedirect("/LearnSqlServlet/user_home");
	}

}
