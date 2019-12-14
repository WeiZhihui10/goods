package cn.edu.lingnan.admin.admin.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.lingnan.admin.admin.bean.Admin;
import cn.edu.lingnan.admin.admin.service.AdminService;
import cn.edu.lingnan.admin.admin.service.impl.AdminServiceImpl;

/**
 * admin模块的控制层
 * @author 魏智辉
 *
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//声明AdminService对象
	AdminService adminService=new AdminServiceImpl();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作符
		String oper=req.getParameter("oper");
		//调用操作符对应方法
		if("adminLoginByAdminNameAndAdminPwd".equals(oper)) {
			//调用管理员登录方法
			adminLoginByAdminNameAndAdminPwdServlet(req,resp);
		}else if("adminQuit".equals(oper)) {
			//调用管理员退出的方法
			adminQuitServlet(req,resp);
		}
	}

	/**
	 * 管理员退出登录
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void adminQuitServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//处理请求信息
		req.getSession().invalidate();
		//响应处理结果
		resp.sendRedirect(req.getContextPath()+"/adminjsps/login.jsp");
		return;
	}

	/**
	 * 管理员登录功能方法
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void adminLoginByAdminNameAndAdminPwdServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		//获取数据
		String adminName=req.getParameter("adminName");
		String adminPwd=req.getParameter("adminPwd");
		//处理请求信息
		Admin admin=adminService.findAdminByAdminNameAndAdminPwdDao(adminName, adminPwd);
		//响应处理结果
		if(admin!=null) {
			adminName=URLEncoder.encode(adminName,"utf-8");
			Cookie cookie=new Cookie("adminname",adminName);
			cookie.setMaxAge(3*24*3600);
			cookie.setPath(req.getContextPath()+"/adminjsps/login.jsp");
			resp.addCookie(cookie);
			req.setAttribute("name", adminName);
			req.getSession().setAttribute("admin", admin);
			resp.sendRedirect(req.getContextPath()+"/adminjsps/admin/index.jsp");
			return;
		}else {
			req.setAttribute("msg", "用户名或密码错误");
			req.getRequestDispatcher("/adminjsps/login.jsp").forward(req, resp);
			return;
		}
	}

}
