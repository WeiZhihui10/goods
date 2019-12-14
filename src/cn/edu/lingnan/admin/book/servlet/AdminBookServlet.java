package cn.edu.lingnan.admin.book.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.service.CategoryService;
import cn.edu.lingnan.category.service.impl.CategoryServiceImpl;
import cn.edu.lingnan.pager.PageBean;
import cn.itcast.commons.CommonUtils;


public class AdminBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//声明BookService对象
	private BookService bookService=new BookServiceImpl();
	//声明CategoryService对象
	private CategoryService caService=new CategoryServiceImpl();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作值
		String oper=req.getParameter("oper");
		//调用操作值对应方法
		if("findAllCategory".equals(oper)) {
			//调用查询所有分类的方法
			findAllCategoryServlet(req,resp);
		}else if("findBookByCategory".equals(oper)) {
			//调用通过分类查询书籍的方法
			findBookByCategoryServlet(req,resp);
		}else if("findBookByBname".equals(oper)) {
			//调用通过书名查询书籍的方法
			findBookByBnameServlet(req,resp);
		}else if("findBookByAuthor".equals(oper)) {
			//调用通过作者查询书籍的方法
			findBookByAuthorServlet(req,resp);
		}else if("findBookByPress".equals(oper)) {
			//调用通过出版社查询书籍的方法
			findBookByPressServlet(req,resp);
		}else if("findBookByCombination".equals(oper)) {
			//调用通过多条件组合查询书籍的方法
			findBookByCombinationServlet(req,resp);
		}else if("findBookByBid".equals(oper)) {
			//调用通过bid查询书籍的方法,加载图书信息的方法
			loadBookByBidServlet(req,resp);
		}else if("findParentCategory".equals(oper)) {
			//调用查询一级分类的方法
			 findParentCategoryServlet(req, resp);
		}else if("ajaxFindChildren".equals(oper)) {
			//调用查询指定一级分类下的所有二级分类的方法
			ajaxFindChildrenServlet(req,resp);
		}else if("updateBookByBid".equals(oper)) {
			//调用修改图书信息的方法
			updateBookByBidServlet(req,resp);
		}else if("deleteBook".equals(oper)) {
			//调用删除图书的方法
			deleteBookServlet(req,resp);
		}
	}
	
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteBookServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取请求数据
		String bid=req.getParameter("bid");
		Book book=bookService.findBookByBidService(bid);
		String savePath=this.getServletContext().getRealPath("/");//获取真实路径
		//处理请求信息
		new File(savePath,book.getImage_w()).delete();//删除图片
		new File(savePath,book.getImage_b()).delete();//删除图片
		bookService.deleteBookService(bid);
		//响应处理结果
		req.setAttribute("msg", "图书删除成功");
		req.getRequestDispatcher("/adminjsps/admin/book/body.jsp").forward(req, resp);
	}

	/**
	 * 修改图书信息的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateBookByBidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取请求数据
		Map<String, String[]> map=req.getParameterMap();
		Book book=CommonUtils.toBean(map, Book.class);
		Category category=CommonUtils.toBean(map, Category.class);
		book.setCategory(category);
		//处理请求信息
		bookService.updateBookByBidService(book);
		//响应处理结果
		req.setAttribute("msg", "图书信息修改成功");
		req.getRequestDispatcher("/adminjsps/admin/book/body.jsp").forward(req, resp);
		return;
	}

	/**
	 * 加载图书信息的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void loadBookByBidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String bid=req.getParameter("bid");
		//处理请求信息
		Book bookByBid=bookService.findBookByBidService(bid);
		req.setAttribute("bookByBid", bookByBid);
		req.setAttribute("parents", caService.findParentCategoryService());
		String cid=bookByBid.getCategory().getCid();//获取书籍的二级分类信息
		Category category=caService.loadCategoryByCidService(cid);//通过二级分类的id查询二级分类信息
		String pid=category.getParent().getCid();//父分类id
		req.setAttribute("pid", pid);//将二级分类的父分类id保存到req中
		req.setAttribute("childrens", caService.findParentByPidService(pid));//通过父分类id查询所有该分类下的子分类
		//响应处理结果
		req.getRequestDispatcher("/adminjsps/admin/book/desc.jsp").forward(req, resp);
		return;
		
	}

	/**
	 * 查询指定一级分类下的所有二级分类的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void ajaxFindChildrenServlet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//获取数据
		String pid=req.getParameter("pid");
		//处理请求信息
		List<Category> childrenCategories=caService.findParentByPidService(pid);
		//响应处理结果
		resp.getWriter().write(new Gson().toJson(childrenCategories));
		return;
	}
	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc=1;
		String param=req.getParameter("pc");
		if(param!=null&&!param.trim().isEmpty()) {
			try {
				pc=Integer.parseInt(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pc;
	}
	
	/**
	 * 获取Url
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		String url=req.getRequestURI()+"?"+req.getQueryString();
		int index=url.lastIndexOf("&pc=");
		if(index!=-1) {
			url=url.substring(0,index);
		}
		return url;
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
		//处理请求信息
		List<Category> parents=caService.findParentCategoryService();
		//响应处理结果
		req.setAttribute("parents", parents);
		req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
		return;
	}
	
	/**
	 * //通过多条件组合查询书籍的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findBookByCombinationServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取请求数据
		int pc=getPc(req);
		String url=getUrl(req);
		String bname=req.getParameter("bname");
		String author=req.getParameter("author");
		String press=req.getParameter("press");
		//处理请求信息
		Book criteria=new Book();
		criteria.setAuthor(author);
		criteria.setBname(bname);
		criteria.setPress(press);
		//调用Service的findBookByPressService(press, pc)方法通过分类查询书籍
		//响应处理结果
		PageBean<Book> pageBean=null;
		pageBean=bookService.findBookByCombinationService(criteria, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
		return;
	}
	/**
	 * 通过出版社查询书籍的Servlet
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findBookByPressServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//获取请求数据
		int pc=getPc(req);
		String url=getUrl(req);
		String press=req.getParameter("press");
		//处理请求信息
		//调用Service的findBookByPressService(press, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=null;
		pageBean=bookService.findBookByPressService(press, pc);
		pageBean.setUrl(url);
		//响应处理结果
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
		return;
		
	}
	/**
	 * 通过作者查询书籍的Servlet
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findBookByAuthorServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获得数据
		int pc=getPc(req);
		String url=getUrl(req);
		String author=req.getParameter("author");
		//调用Service的findBookByAuthorService(author, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=null;
		pageBean=bookService.findBookByAuthorService(author, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
		return;
	}
	/**
	 * 通过书名查询书籍的Servlet
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findBookByBnameServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//获得数据
		int pc=getPc(req);
		String url=getUrl(req);
		String bname=req.getParameter("bname");
		//调用Service的findBookByBnameService(bname, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=bookService.findBookByBnameService(bname, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		//处理响应结果
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
		return;
		
	}
	/**
	 * 通过分类查询书籍的Servlet
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findBookByCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		int pc=getPc(req);
		String url=getUrl(req);
		String cid=req.getParameter("cid");
		//调用Service的findBookByCategoryService(cid, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=bookService.findBookByCategoryService(cid, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		//处理响应结果
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
		return;
	}
	/**
	 * 管理员查询所有分类
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findAllCategoryServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//声明变量
		List<Category> categories=caService.findAllCategoryService();
		//处理响应信息
		if(categories!=null) {
			req.setAttribute("categories", categories);
			//请求转发到left.jsp
			req.getRequestDispatcher("/adminjsps/admin/book/left.jsp").forward(req, resp);
			return;
		}else {
			req.setAttribute("msg","还没有分类信息");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			return;
		}
	}

}
