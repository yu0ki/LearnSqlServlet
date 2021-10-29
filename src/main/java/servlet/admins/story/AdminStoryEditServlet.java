
package servlet.admins.story;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AdminAccountBeans;
import beans.AdminStoryBeans;
import story_dao.AdminStoryEditDAO;
import story_dao.AdminStoryShowDAO;

/**
 * Servlet implementation class AdminStoryEditServlet
 */
@WebServlet("/admins/story/edit")
public class AdminStoryEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStoryEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/story/edit.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// urlのパラメーターから更新対象のストーリータイトル(旧バージョン)取得
		String title = request.getParameter("title");	
//		System.out.println("ases dopost title = " + title);
		
		// ユーザーによる入力値
		String new_title = request.getParameter("new_title");
		String new_sentence = request.getParameter("new_sentence");
		String new_next_title = request.getParameter("new_next_title");
		int new_eid = 0;
		
			try{ 
				new_eid = Integer.parseInt(request.getParameter("new_eid"));
			} catch (Exception e){
			}
		
		
		// 貰ったタイトルからasb作成
		AdminStoryBeans asb = AdminStoryShowDAO.findStory(title);
		// sessionからaab取得
		HttpSession session = request.getSession(false);
		AdminAccountBeans aab = (AdminAccountBeans) session.getAttribute("admin");	
		// 更新作業 by DAO
		try {
			AdminStoryEditDAO.editStory(asb, aab, new_title, new_sentence, new_eid, new_next_title);
		}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/admins/story/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
//		System.out.println("/LearnSqlServlet/admins/story/show.jsp?title=" + new_title);
		
		// redirectのときに文字化けするのを防ぐ処理
				// UTF-8でコーディングされているものを、iso-8859-1に変換
				byte[] tmp = new String(new_title).getBytes("UTF-8");
			    String str = new String(tmp, "ISO-8859-1");
//			    System.out.println(str);
			    
			    
			    
			    
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/users/story/show.jsp");
//				dispatcher.forward(request,response);
				response.sendRedirect("/LearnSqlServlet/admins/story/show.jsp?title="+str);
	}

}
