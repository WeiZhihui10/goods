package cn.edu.lingnan.category.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.service.CategoryService;
import cn.edu.lingnan.category.service.impl.CategoryServiceImpl;

public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryService caService=new CategoryServiceImpl();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取请求信息
		//处理请求请求信息，调用查询所有分类方法
		findAllCategoryServlet(req,resp);
		//响应处理结果
	}
	
	//查询所有分类
	private void findAllCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//声明变量
		List<Category> categories=caService.findAllCategoryService();
		//处理响应信息
		if(categories!=null) {
			req.setAttribute("categories", categories);
			//请求转发到left.jsp
			req.getRequestDispatcher("/jsps/left.jsp").forward(req, resp);
		}else {
			req.setAttribute("msg","还没有分类信息");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
	}

}
