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
 * 前台过滤器，未登录不能进行购物车操作，不能进行订单操作
 * @author 魏智辉
 *
 */
public class LoginFilter implements Filter {
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse respone, FilterChain chain) 
			throws IOException, ServletException {
		/**
		 * 获取session中的User
		 * 判断是否为null
		 *  >如果为null;保存错误信息，转发到msg.jsp
		 *  >如果不为null;放行
		 */
		HttpServletRequest req=(HttpServletRequest) request;
		Object user=req.getSession().getAttribute("sessionUser");
		if(user!=null) {
			chain.doFilter(req, respone);
		}else {
			respone.getWriter().write( "你还没有登录，请先<a href='/goods/jsps/user/login.jsp' target='_parent'>登录</a>");
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
