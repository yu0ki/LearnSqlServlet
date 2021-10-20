package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter(urlPatterns= {"/admins/home"})
public class AdminFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AdminFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
//		request.setCharacterEncoding("UTF-8");
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		
		if ( session == null) {
//			System.out.println("direct access");
			// シークレットブラウズでやると分かりやすい。
			// 普通のブラウジングだと一回接続成功するとセッションが一定時間保たれてしまう
			System.out.println("direct access for admin");
			((HttpServletResponse) response).sendRedirect("/LearnSqlServlet/admins/log_in");
			return;
			
		} else if (session.getAttribute("admin") == null){
//			System.out.println((session.getAttribute("user")));
			System.out.println("You are not an admin.");
			((HttpServletRequest) request).getSession(false).invalidate();
			((HttpServletResponse) response).sendRedirect("/LearnSqlServlet/admins/log_in");
			return;
		} 
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
