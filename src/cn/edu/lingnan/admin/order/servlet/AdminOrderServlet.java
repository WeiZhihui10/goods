package cn.edu.lingnan.admin.order.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.order.bean.OrderItem;
import cn.edu.lingnan.order.service.OrderService;
import cn.edu.lingnan.order.service.impl.OrderServiceImpl;
import cn.edu.lingnan.pager.PageBean;

/**
 * 后台管理订单模块的servlet
 * @author Administrator
 *
 */
public class AdminOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //声明OrderService对象
	private OrderService orderService=new OrderServiceImpl();
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
	
	
	/**
	 * 
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作值
		String oper=req.getParameter("oper");
		//执行操作值对应的操作
		if("findAllOrder".equals(oper)) {
			//调用查询所有订单的方法
			findAllOrderServlet(req,resp);
		}else if("findOrderByStatus".equals(oper)) {
			//调用通过状态查询订单的方法
			findOrderByStatusServlet(req,resp);
		}else if("findOrderByOid".equals(oper)) {
			//调用查询订单详情的方法
			findOrderByOidServlet(req,resp);
		}else if("cancelOrder".equals(oper)) {
			//调用取消订单方法
			cancelOrderByOidServlet(req,resp);
		}else if("deliver".equals(oper)) {
			//调用发货方法
			deliverServlet(req,resp);
		}else if("returnGoods".equals(oper)) {
			//调用退货方法
			returnGoodsServlet(req,resp);
		}
	}

	/**
	 * 退货方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void returnGoodsServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		Order order=orderService.findOrderByOidService(oid);
		List<OrderItem> orderItems=order.getOrderItemList();
		if(order.getStatus()!=6) {
			req.setAttribute("msg", "当前状态不支持退货");
			req.getRequestDispatcher("/adminjsps/admin/order/list.jsp").forward(req, resp);
			return;
		}
		//修改订单条目中每本书的数量
		for(OrderItem orderItem:orderItems) {
			bookService.updateBookNumberService(orderItem.getQuantity(), orderItem.getBook().getBid());
		}
		//修改订单状态
		orderService.updateOrderStatusByOidService(oid, 5);
		//响应处理结果
		req.setAttribute("msg", "已确认退货");
		findAllOrderServlet(req, resp);
		return;
	}
	/**
	 * 发货
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void deliverServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		int index=orderService.findOrderStatusByOidService(oid);
		if(index!=2) {
			req.setAttribute("msg", "订单还未支付");
			findAllOrderServlet(req, resp);
			return;
		}
		//设置状态为取消
		orderService.updateOrderStatusByOidService(oid, 3);
		req.setAttribute("msg", oid+"订单已发货");
		findAllOrderServlet(req, resp);
		
	}
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void cancelOrderByOidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		Order order=orderService.findOrderByOidService(oid);
		int index=order.getStatus();
		if(index!=1&&index!=2) {
			req.setAttribute("msg", "订单当前状态无法取消");
			findAllOrderServlet(req, resp);
			return;
		}
		//修改订单条目中每本书的数量
		List<OrderItem> orderItems=order.getOrderItemList();
		for(OrderItem orderItem:orderItems) {
			bookService.updateBookNumberService(orderItem.getQuantity(), orderItem.getBook().getBid());
		}
		//设置状态为取消
		orderService.updateOrderStatusByOidService(oid, 5);
		//响应处理结果
		req.setAttribute("msg", oid+"订单已取消");
		findAllOrderServlet(req, resp);
		return;
	}
	
	/**
	 * 查询订单详情的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findOrderByOidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取请求数据
		String oid=req.getParameter("oid");
		String btn=req.getParameter("btn");
		//处理请求信息
		Order order=orderService.findOrderByOidService(oid);
		//响应处理结果
		req.setAttribute("order", order);
		req.setAttribute("btn", btn);
		req.getRequestDispatcher("/adminjsps/admin/order/desc.jsp").forward(req, resp);
	}
	/**
	 * 通过状态查询订单
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findOrderByStatusServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取请求数据
		int pc=getPc(req);
		int status=Integer.parseInt(req.getParameter("status"));
		String url=getUrl(req);
		//处理请求信息
		PageBean<Order> pageBean=orderService.findOrderByStatusService(status, pc);
		pageBean.setUrl(url);
		//响应处理结果
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/adminjsps/admin/order/list.jsp").forward(req, resp);
		return;
		
	}
	/**
	 * 查询所有订单的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findAllOrderServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取请求数据
		int pc=getPc(req);
		String url=getUrl(req);
		//处理请求信息
		PageBean<Order> pageBean=orderService.findAllOrderDao(pc);
		pageBean.setUrl(url);
		//响应处理结果
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/adminjsps/admin/order/list.jsp").forward(req, resp);
		return;
	}
}
