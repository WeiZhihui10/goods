package cn.edu.lingnan.order.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.cart.service.CartItemService;
import cn.edu.lingnan.cart.service.impl.CartItemServiceImpl;
import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.order.bean.OrderItem;
import cn.edu.lingnan.order.service.OrderService;
import cn.edu.lingnan.order.service.impl.OrderServiceImpl;
import cn.edu.lingnan.pager.PageBean;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.util.IDGenerator;
/**
 * Order模块的控制层
 * @author Administrator
 *
 */

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//声明OrderService的对象
	private OrderService orderService=new OrderServiceImpl();
	private CartItemService cartItemService=new CartItemServiceImpl();
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
		if("findOrderByUid".equals(oper)) {
			//调用查询我的订单的方法
			findOrderByUidServlet(req,resp);
		}else if("addOrderAndOrderItem".equals(oper)) {
			//调用添加订单和订单条目的方法
			addOrderAndOrderItemServlet(req,resp);
		}else if("findOrderByOid".equals(oper)) {
			//调用通过订单id查询订单信息
			findOrderByOidServlet(req,resp);
		}else if("cancelOrderByOid".equals(oper)) {
			//调用取消订单的方法
			cancelOrderByOidServlet(req,resp);
		}else if("confirmOrderByOid".equals(oper)) {
			//调用确认收货的方法
			confirmOrderByOidServlet(req,resp);
		}else if("paymentPre".equals(oper)) {
			//调用支付准备方法
			paymentPreServlet(req,resp);
		}else if("payment".equals(oper)) {
			//调用支付方法
			paymentServlet(req, resp);
		}else if("findOrderByStatus".equals(oper)) {
			//调用通过订单状态查询订单的方法
			findOrderByStatusServlet(req,resp);
		}else if("returnGoods".equals(oper)||"cancelReturnGoods".equals(oper)) {
			//调用退货或取消退货的方法
			returnGoodsServlet(req,resp);
		}
	}
	
	/**
	 * 退货或取消退货的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void returnGoodsServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// 获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		int index=orderService.findOrderStatusByOidService(oid);
		if(index==3) {
			//设置状态为退货中
			orderService.updateOrderStatusByOidService(oid, 6);
			req.setAttribute("msg", "退货中，等待商家确认");
		}else if(index==6) {
			//设置状态为3
			orderService.updateOrderStatusByOidService(oid, 3);
			req.setAttribute("msg", "已取消退货，等待确认收货");
		}else{
			req.setAttribute("msg", "当前状态暂不支持该操作");
		}
		findOrderByUidServlet(req, resp);
		return;
	}
	/**
	 * 通过订单状态查询订单的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findOrderByStatusServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 获取请求数据
		int status=Integer.parseInt(req.getParameter("status"));
		int uid=((User)req.getSession().getAttribute("sessionUser")).getId();
		int pc=getPc(req);
		String url=getUrl(req);
		//处理请求信息
		PageBean<Order> pageBean=orderService.findOrderByStatusService(uid,status, pc);
		pageBean.setUrl(url);
		//响应处理结果
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("/jsps/order/list.jsp").forward(req, resp);
	}
	/**
	 * 支付方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void paymentServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		//获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		int index=orderService.findOrderStatusByOidService(oid);
		if(index!=1) {
			req.setAttribute("msg", "支付失败，请稍后重试");
			findOrderByUidServlet(req, resp);
			return;
		}
		//设置状态为待发货
		orderService.updateOrderStatusByOidService(oid, 2);
		req.setAttribute("msg", "订单支付成功");
		findOrderByUidServlet(req, resp);
		return;
	}
	
	/**
	 * 支付准备方法，即查询订单详细信息，让客户知道订单详情
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void paymentPreServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		Order order=orderService.findOrderByOidService(oid);
		req.setAttribute("order", order);
		//请求转发到/jsps/order/pay.jsp
		req.getRequestDispatcher("/jsps/order/pay.jsp").forward(req, resp);
		return;
	}
	
	/**
	 * 确认收货的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void confirmOrderByOidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String oid=req.getParameter("oid");
		//处理请求信息
		int index=orderService.findOrderStatusByOidService(oid);
		if(index!=3) {
			req.setAttribute("msg", "您还没有支付，不能确认收货");
			findOrderByUidServlet(req, resp);
			return;
		}
		//设置状态为交易成功
		orderService.updateOrderStatusByOidService(oid, 4);
		req.setAttribute("msg", "订单已确认收货，交易完成");
		findOrderByUidServlet(req, resp);
		return;
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
			findOrderByUidServlet(req, resp);
			return;
		}
		List<OrderItem> orderList=order.getOrderItemList();
		//修改订单中每个订单条目中图书的数量
		for(OrderItem orderItem:orderList) {
			bookService.updateBookNumberService(orderItem.getQuantity(), orderItem.getBook().getBid());
		}
		//设置状态为取消
		orderService.updateOrderStatusByOidService(oid, 5);
		req.setAttribute("msg", "订单已取消");
		findOrderByUidServlet(req, resp);
		return;
	}
	
	/**
	 * 通过订单id查询订单信息的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findOrderByOidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String oid=req.getParameter("oid");
		String btn=req.getParameter("btn");
		//处理请求信息
		Order order=orderService.findOrderByOidService(oid);
		//响应处理结果
		req.setAttribute("order", order);
		req.setAttribute("btn", btn);
		//请求转发
		req.getRequestDispatcher("/jsps/order/desc.jsp").forward(req, resp);
		return;
	}
	
	/**
	 * 添加订单和订单条目的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addOrderAndOrderItemServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String cartItemIds=req.getParameter("cartItemIds");
		User user=(User) req.getSession().getAttribute("sessionUser");
		//加载多个cartItem
		List<CartItem> cartItemList=cartItemService.loadCartItemsService(cartItemIds);
		//判断用户是否进行返回操作重复提交同一订单
		if(cartItemList.size()==0) {
			req.setAttribute("msg", "提交订单失败，您未选择图书");
			cartItemList=cartItemService.findCartItemByUserService(user);
			req.setAttribute("cartItemList", cartItemList);
			req.getRequestDispatcher("/jsps/cart/list.jsp").forward(req, resp);
			return;
		}
		BigDecimal total=new BigDecimal("0");
		boolean flag=false;
		//判断图书的数量是否小于用户购买的数量
		for(CartItem cartItem:cartItemList) {
			total=total.add(new BigDecimal(cartItem.getSubtotal()+""));
			flag=bookService.updateBookNumberService(-cartItem.getQuantity(), cartItem.getBook().getBid());
			if(!flag) {
				cartItemList=cartItemService.findCartItemByUserService(user);
				req.setAttribute("msg",cartItem.getBook().getBname()+"商品数量不足，提交订单失败");
				req.setAttribute("cartItemList", cartItemList);
				req.getRequestDispatcher("/jsps/cart/list.jsp").forward(req, resp);
				return;
			}
		}
		//创建Order
		Order order=new Order();
		order.setOid(IDGenerator.getUUID());//订单id
		order.setOrdertime(String.format("%tF %<tT", new Date()));//设置下单时间
		order.setStatus(1);//设置状态
		order.setAddress(req.getParameter("address"));//设置地址
		order.setUser(user);//设置所属用户
		order.setTotal(total.doubleValue());//设置总计
		
		
		//创建List<OrderItem>,一个CareItem对应一个OrderItem
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		for(CartItem cartItem:cartItemList) {
			OrderItem orderItem=new OrderItem();
			orderItem.setOrderItemId(IDGenerator.getUUID());//设置主键
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		
		//调用service完成添加
		orderService.addOrderAndOrderItemService(order);
		//删除购物车条目
		cartItemService.batchDeleteCartItemService(cartItemIds);
		req.setAttribute("order", order);
		req.getRequestDispatcher("/jsps/order/desc.jsp").forward(req, resp);
	}
	
	/**
	 *通过uid查询我的订单的方法 
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findOrderByUidServlet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//获取数据
		int pc=getPc(req);
		String url=getUrl(req);
		User user=(User) req.getSession().getAttribute("sessionUser");
		int uid=user.getId();
		//调用Service的findOrderByUidService(uid, pc)方法通过uid查找订单
		PageBean<Order> pageBean=orderService.findOrderByUidService(uid, pc);
		pageBean.setUrl(url);
		req.setAttribute("pageBean", pageBean);
		//处理响应结果
		req.getRequestDispatcher("/jsps/order/list.jsp").forward(req, resp);
	}
}
