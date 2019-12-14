package cn.edu.lingnan.user.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.edu.lingnan.user.bean.Address;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.service.AddressService;
import cn.edu.lingnan.user.service.UserService;
import cn.edu.lingnan.user.service.impl.AddressServiceImpl;
import cn.edu.lingnan.user.service.impl.UserServiceImpl;
import cn.edu.lingnan.util.IDGenerator;

/**
 * 用户模块的控制层
 * @author 魏智辉
 *
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserService userService=new UserServiceImpl();
    
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作值，判断请求
		String oper=req.getParameter("oper");
		//调用对应的方法处理请求
		if("regist".equals(oper)) {
			//调用用户注册方法
			userRegistServlet(req,resp);
		}else if("ajaxValidateLoginname".equals(oper)) {
			//调用用户名校验方法
			userAjaxValidateLoginnameServlet(req, resp);
		}else if("ajaxValidateEmail".equals(oper)) {
			//调用邮箱验证方法
			userAjaxValidateEmailServlet(req, resp);
		}else if("ajaxValidateVerifyCode".equals(oper)) {
			//调用验证码校验方法
			userAjaxvalidateVerifyCode(req, resp);
		}else if("login".equals(oper)) {
			//调用用户登录方法
			findUserByNameAndPwdServlet(req,resp);
		}else if("ajaxValidateLoginPwd".equals(oper)) {
			//调用密码校验方法
			userAjaxValidateLoginPwd(req,resp);
		}else if("updateUserPwd".equals(oper)) {
			//调用修改用户密码方法
			updateUserPwdServlet(req,resp);
		}else if("userQuit".equals(oper)) {
			//调用用户退出功能
			userQuitServlet(req,resp);
		}else if("addAddress".equals(oper)) {
			//调用添加地址的方法
			addAddressServlet(req,resp);
		}else if("turnNull".equals(oper)) {
			//修改sessionUser中addressLis的值为null
			addNewAddrServlet(req,resp);
		}
		
	}
	
	/**
	 * 修改sessionUser中addressLis的值为null
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void addNewAddrServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		// 获取请求数据
		User user=(User) req.getSession().getAttribute("sessionUser");
		//处理请求信息
		user.setAddresseList(null);
		//响应处理结果
		req.getSession().setAttribute("sessionUser", user);
		resp.getWriter().write("ok");
	}

	/**
	 * 添加地址
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void addAddressServlet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取数据
		String addr=req.getParameter("address");
		User user=(User) req.getSession().getAttribute("sessionUser");
		//处理请求数据
		Address address=new Address();
		address.setAid(IDGenerator.getUUID());
		address.setAddress(addr);
		address.setUser(user);
		AddressService addressService=new AddressServiceImpl();
		addressService.addAddressService(address);
		//响应处理结果
		user=userService.FindUserByNameAndPwdService(user.getName(),user.getPwd());
		req.getSession().setAttribute("sessionUser",user);
		resp.getWriter().write("添加成功");
	}

	/**
	 * 用户退出功能
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void userQuitServlet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//销毁Session
		req.getSession().invalidate();
		//重定向到login.jsp
		resp.sendRedirect(req.getContextPath()+"/jsps/user/login.jsp");
		return;
	}

	/**
	 * 修改用户密码的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateUserPwdServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取请求信息
		String oldPwd=req.getParameter("oldPwd");
		String newPwd=req.getParameter("newPwd");
		String newPwd2=req.getParameter("newPwd2");
		String verifyCode=req.getParameter("verifyCode");
		User user=new User(0, null, oldPwd, null, newPwd2, verifyCode,newPwd,null);
		User user2=(User) req.getSession().getAttribute("sessionUser");
		if(user2==null) {
			req.setAttribute("msg", "你还没有登录，请登录");
			req.getRequestDispatcher("/jsps/user/login.jsp");
			return;
		}
		//信息校验
		Map<String,String> errors=validateUpdateUserPwd(user,user2, req);
		if(errors.size()>0) {
			req.setAttribute("updateUserPwd", user);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/jsps/user/pwd.jsp").forward(req, resp);
			return;
		}
		//处理请求信息
		boolean flag=userService.updateUserPwdService(user2.getName(),oldPwd,newPwd);
		//响应处理结果
		if(flag) {//密码修改成功,重定向到登录页面
			req.getSession().invalidate();
			req.getSession().setAttribute("msg", "密码修改成功，请重新登录");
			resp.sendRedirect(req.getContextPath()+"/jsps/user/login.jsp");
			return;
		}else {//密码修改失败，请求转发到pwd.jsp
			req.setAttribute("msg", "密码修改失败，请稍后重试！");
			req.getRequestDispatcher("/jsps/user/pwd.jsp").forward(req, resp);
			return;
		}
		
	}
	
	/**
	 *修改密码校验 
	 * @param user
	 * @param user2
	 * @param req
	 * @return
	 */
	private Map<String, String> validateUpdateUserPwd(User user,User user2,HttpServletRequest req){
		//声明变量
		Map<String,String> errors=new HashMap<String, String>();
		//获取数据
		String oldPwd=user.getPwd();
		String newPwd=user.getNewPwd();
		String newPwd2=user.getLoginPwd2();
		
		//原密码校验
		if(oldPwd==null||oldPwd.trim().isEmpty()) {
			errors.put("oldPwd", "原密码不能为空！");
		}else if(oldPwd.length()<3||oldPwd.length()>20) {
			errors.put("oldPwd", "原密码长度在3~20之间！");
		}else if(!user2.getPwd().equals(oldPwd)) {
			errors.put("oldPwd", "原密码不正确！");
		}
		//密码校验
		if(newPwd==null||newPwd.trim().isEmpty()) {
			errors.put("newPwd", "新密码不能为空！");
		}else if(newPwd.length()<3||newPwd.length()>20) {
			errors.put("newPwd", "新密码长度在3~20之间！");
		}
		//确认密码校验
		if(newPwd2==null||newPwd2.trim().isEmpty()) {
			errors.put("newPwd2", "密码不能为空！");
		}else if(!newPwd2.equals(newPwd)) {
			errors.put("newPwd2", "密码不一致！");
		}
		//验证码校验
		String verifyCode = req.getParameter("verifyCode");
		String vcode = (String)req.getSession().getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
	
	
	/**
	 * 用户登录方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findUserByNameAndPwdServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		//获取请求数据
		String name=req.getParameter("loginname");
		String pwd=req.getParameter("loginpass");
		//处理请求信息
		//数据校验
		Map<String,String> errors=validateLogin(name, pwd,req);
		if(errors.size()>0) {
			req.setAttribute("name", name);
			req.setAttribute("pwd", pwd);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		User user=userService.FindUserByNameAndPwdService(name,pwd);
		//响应处理结果
		if(user!=null) {
			//将用户信息存储到session
			req.getSession().setAttribute("sessionUser", user);
			//创建Cookie,将用户名存储到cookie中，设置cookie有效期，有效路径，添加cookie到响应,中文存储到cookie会乱码，所以需要先处理
			String loginname=user.getName();
			loginname=URLEncoder.encode(loginname,"utf-8");
			Cookie cookie=new Cookie("username",loginname);
			cookie.setMaxAge(3*24*3600);
			cookie.setPath(req.getContextPath()+"/jsps/user/login.jsp");
			resp.addCookie(cookie);
			//重定向到首页
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
			return;
		}else {
			req.setAttribute("name", name);
			req.setAttribute("pwd", pwd);
			req.setAttribute("msg", "用户名不存在！或用户名密码错误");
			req.setAttribute("user", user);
			//请求转发
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		
	}

	/**
	 * 后台注册校验，校验用户名，邮箱，确认密码，验证码
	 * @param formUser 
	 * @return
	 */
	private Map<String, String> validateLogin(String name,String pwd,HttpServletRequest req){
		//声明变量
		Map<String,String> errors=new HashMap<String, String>();
		//获取数据
		String loginname=name;
		String loginpwd=pwd;
		//登录名校验
		if(loginname==null||loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		}else if(loginname.length()<3||loginname.length()>20) {
			errors.put("loginname", "用户名长度在3~20之间！");
		}else if(!userService.UserAjaxValidateLoginnameService(name)) {
			errors.put("loginname", "用户名不存在！");
		}
		//密码校验
		if(loginpwd==null||loginpwd.trim().isEmpty()) {
			errors.put("loginpwd", "密码不能为空！");
		}else if(pwd.length()<3||pwd.length()>20) {
			errors.put("loginpwd", "密码长度在3~20之间！");
		}
		//验证码校验
		String verifyCode = req.getParameter("verifyCode");
		String vcode = (String)req.getSession().getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
	/**
	 * 用户注册方法
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void userRegistServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		//获取请求信息
		String name=req.getParameter("loginname");
		String pwd=req.getParameter("loginpwd");
		String email=req.getParameter("email");
		String pwd2=req.getParameter("loginpwd2");
		String verifyCode=req.getParameter("verifyCode");
		User user=new User(0,name,pwd,email,pwd2,verifyCode,null,null);
		//用户注册校验
		Map<String,String> errors=validateRegist(user,req);
		if(errors.size()>0) {
			req.setAttribute("form", user);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/jsps/user/regist.jsp").forward(req, resp);
			return;
		}
		//处理请求信息,调用业务层
		boolean flag=userService.UserRegistService(user);
		//响应处理结果
		if(flag) {
			req.getSession().setAttribute("msg", "注册成功，请登录！");
			resp.sendRedirect(req.getContextPath()+"/jsps/user/login.jsp");
			return;
		}else {
			resp.getWriter().write("注册失败，请稍后重试");
			return;
		}			
	}
	
	/**
	 * 后台注册校验，校验用户名，邮箱，确认密码，验证码
	 * @param formUser 
	 * @return
	 */
	private Map<String, String> validateRegist(User formUser,HttpServletRequest req){
		//声明变量
		Map<String,String> errors=new HashMap<String, String>();
		//获取数据
		String loginname=formUser.getName();
		String pwd=formUser.getPwd();
		String loginPwd2=formUser.getLoginPwd2();
		String email=formUser.getEmail();
		//登录名校验
		if(loginname==null||loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		}else if(loginname.length()<3||loginname.length()>20) {
			errors.put("loginname", "用户名长度在3~20之间！");
		}else if(userService.UserAjaxValidateLoginnameService(formUser.getName())) {
			errors.put("loginname", "用户名已存在！");
		}
		//密码校验
		if(pwd==null||pwd.trim().isEmpty()) {
			errors.put("pwd", "密码不能为空！");
		}else if(pwd.length()<3||pwd.length()>20) {
			errors.put("pwd", "密码长度在3~20之间！");
		}
		//确认密码校验
		if(loginPwd2==null||loginPwd2.trim().isEmpty()) {
			errors.put("loginPwd2", "密码不能为空！");
		}else if(!loginPwd2.equals(pwd)) {
			errors.put("loginPwd2", "密码不一致！");
		}
		
		//邮箱校验
		if(email==null||email.trim().isEmpty()) {
			errors.put("email", "邮箱不能为空！");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "邮箱格式不正确！");
		}else if(userService.UserAjaxVilidateEmailService(email)) {
			errors.put("email", "邮箱已被注册！");
		}
		//验证码校验
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String)req.getSession().getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}
	
	/**
	 * Ajax校验用户名是否已注册
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void userAjaxValidateLoginnameServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取信息
		String name=req.getParameter("loginname");
		//处理请求信息
		boolean flag=userService.UserAjaxValidateLoginnameService(name);
		//响应处理结果
		resp.getWriter().print(flag);
		return;
	}
	
	/**
	 * Ajax校验邮箱是否已注册
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void userAjaxValidateEmailServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		//获取信息
		String email=req.getParameter("email");
		//处理请求信息
		boolean flag=userService.UserAjaxVilidateEmailService(email);
		//响应处理结果
		resp.getWriter().print(flag);
		return;
	}
	/**
	 * Ajax校验验证码是否正确
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void userAjaxvalidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//获取数据
		String verifyCode=req.getParameter("verifyCode");
		//获取图片验证码
		String vcode = (String) req.getSession().getAttribute("vCode");
		//进行忽略大小写比较，得到结果
		boolean flag = verifyCode.equalsIgnoreCase(vcode);
		resp.getWriter().print(flag);
	}
	
	/**
	 *修改密码功能的原密码校验功能 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException 
	 */
	private void userAjaxValidateLoginPwd(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		//获取数据
		String loginPwd=req.getParameter("loginpwd");
		User user=(User) req.getSession().getAttribute("sessionUser");
		if(user==null) {
			req.setAttribute("msg", "你还没有登录，请登录");
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		String pwd=user.getPwd();
		//处理请求信息
		if(!pwd.equals(loginPwd)) {
			resp.getWriter().print(false);
			return;
		}else {
			resp.getWriter().print(true);
			return;
		}
		
	}
}
