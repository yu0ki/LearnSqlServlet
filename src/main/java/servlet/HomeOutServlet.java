package servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class HomeOutServlet
 */
@WebServlet("/HomeOutServlet")
public class HomeOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeOutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String _hostname = null;
	private String _dbname = null;
	private String _username = null;
	private String _password = null;

	public void init() throws ServletException {
		// iniファイルから自分のデータベース情報を読み込む
		String iniFilePath = getServletConfig().getServletContext()
				.getRealPath("WEB-INF/le4db.ini");
		try {
			FileInputStream fis = new FileInputStream(iniFilePath);
			Properties prop = new Properties();
			prop.load(fis);
			_hostname = prop.getProperty("hostname");
			_dbname = prop.getProperty("dbname");
			_username = prop.getProperty("username");
			_password = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("homeout.jsp");
		dispatcher.forward(request,response);
		
		
//		jsp使わないバージョン
//		文字化けしたため、setContentTypeをgetWriteの前に移動
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		
////		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//
//		out.println("<html>");
//		out.println("<body>");
//
//		out.println("<h3>検索</h3>");
//		out.println("<form action=\"search\" method=\"GET\">");
//		out.println("商品名： ");
//		out.println("<input type=\"text\" name=\"search_name\"/>");
//		out.println("<br/>");
//		out.println("<input type=\"submit\" value=\"検索\"/>");
//		out.println("</form>");
//
//		out.println("<h3>一覧</h3>");
//		Connection conn = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.postgresql.Driver");
//			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
//					+ ":5432/" + _dbname, _username, _password);
//			stmt = conn.createStatement();
//
//			out.println("<table border=\"1\">");
//			out.println("<tr><th>商品ID</th><th>商品名</th><th>価格</th></tr>");
//
//			ResultSet rs = stmt.executeQuery("SELECT * FROM products");
//			while (rs.next()) {
//				int pid = rs.getInt("pid");
//				String name = rs.getString("name");
//				int price = rs.getInt("price");
//
//				out.println("<tr>");
//				out.println("<td><a href=\"item?pid=" + pid + "\">" + pid
//						+ "</a></td>");
//				out.println("<td>" + name + "</td>");
//				out.println("<td>" + price + "</td>");
//				out.println("</tr>");
//			}
//			rs.close();
//
//			out.println("</table>");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		out.println("<h3>追加</h3>");
//		out.println("<form action=\"add\" method=\"GET\">");
//		out.println("商品名： ");
//		out.println("<input type=\"text\" name=\"add_name\"/>");
//		out.println("<br/>");
//		out.println("価格： ");
//		out.println("<input type=\"text\" name=\"add_price\"/>");
//		out.println("<br/>");
//		out.println("<input type=\"submit\" value=\"追加\"/>");
//		out.println("</form>");
//
//		out.println("</body>");
//		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
