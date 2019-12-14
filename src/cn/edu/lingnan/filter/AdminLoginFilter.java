package cn.edu.lingnan.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 后台过滤器
 * @author Administrator
 *
 */
public class AdminLoginFilter implements Filter {
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		Object admin=req.getSession().getAttribute("admin");
		if(admin==null) {
			request.setAttribute("msg", "您还没有登录,请登录");
			request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, response);
		}else {
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
