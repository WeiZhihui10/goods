package cn.edu.lingnan.book.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 图书模块的控制层
 * @author Administrator
 *
 */

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.pager.PageBean;

public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//声明BookService对象
	private BookService bookService=new BookServiceImpl();
	//获取当前页码
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
	//获取Url
	private String getUrl(HttpServletRequest req) {
		String url=req.getRequestURI()+"?"+req.getQueryString();
		int index=url.lastIndexOf("&pc=");
		if(index!=-1) {
			url=url.substring(0,index);
		}
		return url;
	}
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作符
		String oper=req.getParameter("oper");
		//调用操作符对应方法
		if("findBookByCategory".equals(oper)) {
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
			//调用通过bid查询书籍的方法
			findBookByBidServlet(req,resp);
		}
	}

	/**
	 * 通过bid查询书籍的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findBookByBidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获得数据
		String bid=req.getParameter("bid");
		//调用Service的findBookByBidService(press, pc)方法通过分类查询书籍
		Book bookByBid=new Book();
		bookByBid=bookService.findBookByBidService(bid);
		req.setAttribute("bookByBid", bookByBid);
		req.getRequestDispatcher("/jsps/book/desc.jsp").forward(req, resp);
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
		//获得数据
		int pc=getPc(req);
		String url=getUrl(req);
		String bname=req.getParameter("bname");
		String author=req.getParameter("author");
		String press=req.getParameter("press");
		Book criteria=new Book();
		criteria.setAuthor(author);
		criteria.setBname(bname);
		criteria.setPress(press);
		//调用Service的findBookByPressService(press, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=null;
		pageBean=bookService.findBookByCombinationService(criteria, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
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
		//获得数据
		int pc=getPc(req);
		String url=getUrl(req);
		String press=req.getParameter("press");
		//调用Service的findBookByPressService(press, pc)方法通过分类查询书籍
		PageBean<Book> pageBean=null;
		pageBean=bookService.findBookByPressService(press, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
		
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
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
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
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
		
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
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
		
	}

}
