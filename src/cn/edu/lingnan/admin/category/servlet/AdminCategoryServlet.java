package cn.edu.lingnan.admin.category.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.service.CategoryService;
import cn.edu.lingnan.category.service.impl.CategoryServiceImpl;
import cn.edu.lingnan.util.IDGenerator;


public class AdminCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //声明category对象
	private CategoryService categoryService=new CategoryServiceImpl();
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作值
		String oper =req.getParameter("oper");
		//调用操作值对应方法
		if("findAllCategory".equals(oper)) {
			//调用查询所有分类的方法
			findAllCategoryByAdminServlet(req,resp);
		}else if("addParentCategory".equals(oper)) {
			//调用添加一级分类的方法
			addCategoryServlet(req,resp);
		}else if("findParentCategory".equals(oper)) {
			//调用查询所有一级分类的方法
			findParentCategoryServlet(req,resp);
		}else if("addChildrenCategory".equals(oper)) {
			//调用添加二级分类的方法
			addChildrenCategoryServlet(req,resp);
		}else if("findCategoryByCid".equals(oper)) {
			//调用加载一级分类信息的方法
			loadCategoryByCidServlet(req,resp);
		}else if("updateCategory".equals(oper)) {
			//调用修改分类信息的方法
			updateCategoryMsgServlet(req,resp);
		}else if("findChildrenCategoryByCid".equals(oper)) {
			//调用加载二级分类信息的方法
			loadChildrenCategoryByCidServlet(req, resp);
		}else if("deleteCategoryByCid".equals(oper)) {
			//调用删除一级分类方法
			deleteCategoryByCidServlet(req,resp);
		}else if("deleteChildreneCategoryByCid".equals(oper)) {
			//调用删除二级分类的方法
			deleteChildreneCategoryByCidServlet(req,resp);
		}
	}
	
	/**
	 * 删除二级分类的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteChildreneCategoryByCidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String cid=req.getParameter("cid");
		//处理请求信息
		BookService bookService=new BookServiceImpl();
		int index=bookService.findBookCountByCategoryService(cid);
		if(index>0) {
			req.setAttribute("msg", "该分类下还有图书，不能删除");
			findAllCategoryByAdminServlet(req, resp);
			return;
		}else {
			categoryService.deleteCategoryByCidService(cid);
			findAllCategoryByAdminServlet(req, resp);
			return;
		}
		
	}

	/**
	 * 删除分类的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteCategoryByCidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String cid=req.getParameter("cid");
		//处理请求信息
		int index=categoryService.findChildrenCategoryByCidService(cid);
		if(index>0) {
			req.setAttribute("msg", "该分类下还有子分类，无法删除");
			findAllCategoryByAdminServlet(req, resp);
			return;
		}else {
			categoryService.deleteCategoryByCidService(cid);
			findAllCategoryByAdminServlet(req, resp);
			return;
		}
	}

	/**
	 * 加载二级分类信息
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loadChildrenCategoryByCidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String cid=req.getParameter("cid");
		//处理请求结果
		Category children=categoryService.loadCategoryByCidService(cid);
		//响应处理结果
		req.setAttribute("children", children);
		List<Category> parents=categoryService.findParentCategoryService();
		req.setAttribute("parents", parents);
		req.getRequestDispatcher("/adminjsps/admin/category/edit2.jsp").forward(req, resp);
		return;
	}

	/**
	 * 修改一级分类信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateCategoryMsgServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String cid=req.getParameter("cid");
		String cname=req.getParameter("cname");
		String pid=req.getParameter("pid");
		String desc=req.getParameter("desc");
		//处理请求信息
		Category parent=null;
		if(pid!=null) {
			parent=new Category();
			parent.setCid(pid);
		}
		Category category=new Category(cid,cname,parent,desc);
		categoryService.updateCategoryMsgService(category);
		//响应处理结果
		findAllCategoryByAdminServlet(req, resp);
	}

	/**
	 * 加载一级分类信息
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void loadCategoryByCidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String cid=req.getParameter("cid");
		//处理请求信息
		Category category=categoryService.loadCategoryByCidService(cid);
		//响应处理结果
		req.setAttribute("category", category);
		req.getRequestDispatcher("/adminjsps/admin/category/edit.jsp").forward(req, resp);
		return;
	}
	
	/**
	 * 添加二级分类的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addChildrenCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String cid=IDGenerator.getUUID();
		String cname=req.getParameter("cname");
		String pid=req.getParameter("pid");
		String desc=req.getParameter("desc");
		Category parent=new Category();
		parent.setCid(pid);
		//处理请求信息
		Category category=new Category(cid,cname,parent,desc,null);
		categoryService.addCategoryService(category);
		//响应处理结果
		findAllCategoryByAdminServlet(req, resp);
		return;
	}

	/**
	 * 查询所有一级分类
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findParentCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String pid=req.getParameter("pid");//获取当前点击的父分类
		//处理请求信息
		List<Category> parents=categoryService.findParentCategoryService();
		//响应处理结果
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		req.getRequestDispatcher("/adminjsps/admin/category/add2.jsp").forward(req, resp);;
	}
	
	/**
	 *添加分类的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String cname=req.getParameter("cname");
		String desc=req.getParameter("desc");
		//处理请求信息
		Category category=new Category();
		category.setCid(IDGenerator.getUUID());
		category.setCname(cname);
		category.setDesc(desc);
		categoryService.addCategoryService(category);
		findAllCategoryByAdminServlet(req, resp);
		return;
	}
	
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findAllCategoryByAdminServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//创建List<Category>对象
		List<Category> categorieList=categoryService.findAllCategoryService();
		//处理响应信息
		if(categorieList!=null) {
			req.setAttribute("categorieList", categorieList);		
			//请求转发到left.jsp
			req.getRequestDispatcher("/adminjsps/admin/category/list.jsp").forward(req, resp);
			return;
		}else {
			req.setAttribute("msg","还没有分类信息");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			return;
		}
	}

}
