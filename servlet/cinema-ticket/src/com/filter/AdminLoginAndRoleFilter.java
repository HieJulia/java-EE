package com.filter;

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
 * Servlet Filter implementation class LoginAndRoleFilter
 */
@WebFilter(urlPatterns= {"/MovieManage/*","/MovieManageServlet","/AddMovieServlet","/AddNewMovieServlet","/DetailsServlet",
		"/DeleteServlet"})
public class AdminLoginAndRoleFilter implements Filter {
// filter with password : webfilter : 
    /**
     * Default constructor. 
     */
    public AdminLoginAndRoleFilter() {
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
		System.out.println("管理员权限过滤器");
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session=req.getSession();// get session 
		// allow admin
		if(session.getAttribute("userName")!=null&&session.getAttribute("role").equals("admin")) {
			chain.doFilter(request, response);			
		}
		else {
			req.setAttribute("msg", "尚无访问该页面权限");
			req.getRequestDispatcher("/Login.jsp").forward(req, resp);// redirect back to login.jsp page 
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("权限过滤器");
	}

}


