package cn.edu.lingnan.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 项目编码过滤器
 */
public class EncodingFilter implements Filter {
	public void destroy() {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		//设置请求编码格式
		request.setCharacterEncoding("utf-8");
		//设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		//过滤放行
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
