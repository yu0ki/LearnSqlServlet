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
import beans.UserAccountBeans;
/**
 * Servlet implementation class AccountCheck
 */
@WebServlet("/log_in")
public class LoginServlet extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/users/login.jsp");
		dispatcher.forward(request,response);
    	

       
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
    	
    	// ログインを要求してきたユーザーが正しいユーザーかどうかを確認する。
    	// そのためにまずは、ユーザーが打ち込んできたニックネームを取得する。
    	String nickname = request.getParameter("nickname");
    	
    	// 次に、取得したニックネームをUserAccountBeansに保持
    	UserAccountBeans uab = new UserAccountBeans();
    	uab.setNickname(nickname);
    	
    	// アカウントの有無を検索
        // 検索したアカウント情報を取得
        UserAccountDAO ua_dao = new UserAccountDAO();
        UserAccountBeans returnUAb = ua_dao.findUserAccount(uab);
        
        // アカウントが登録済みである　かつ　退会済みでない
//        System.out.println(returnUAb != null);
//        System.out.println(returnUAb.getIsValidAccount());
        if(returnUAb != null && returnUAb.getIsValidAccount()){
        	// ユーザーが正しい時
        	// 新たなセッションを生成してログイン
        	HttpSession session = request.getSession(true);
            session.setAttribute("user", returnUAb);
//            System.out.println("session.getAttribute(user) = " + session.getAttribute("user"));
            
//            RequestDispatcher rd = request.getRequestDispatcher("/user_home");
//            rd.forward(request, response);
            response.sendRedirect("/LearnSqlServlet/user_home");
        } else {
        	// 認証失敗
//            RequestDispatcher rd = request.getRequestDispatcher("/home");
//            rd.forward(request, response);
        	response.sendRedirect("/LearnSqlServlet/home");
        }
    	
    	
    }
}

