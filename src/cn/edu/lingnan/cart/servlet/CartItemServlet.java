package cn.edu.lingnan.cart.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.cart.service.CartItemService;
import cn.edu.lingnan.cart.service.impl.CartItemServiceImpl;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.service.impl.UserServiceImpl;

/**
 * 购物车条目的控制层
 * @author Administrator
 *
 */
public class CartItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//声明Service层对象
	CartItemService caService=new CartItemServiceImpl();
	
	
	
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取操作符
		String oper=req.getParameter("oper");
		//调用操作符对应的方法
		if("findCartItemByUser".equals(oper)) {
			//调用通过User查找购物车条目的方法
			findCartItemByUserServlet(req,resp);
		}else if("addCartItemByUidAndBid".equals(oper)) {
			//调用添加购物车条目的方法
			addCartItemByUidAndBidServlet(req,resp);
		}else if("batchDeleteCartItem".equals(oper)) {
			//调用批量删除购物车条目的方法
			batchDeleteCartItemServlet(req,resp);
		}else if("updateCartItemQuantityByCartItemId".equals(oper)) {
			//调用修改购物车条目中数量的方法
			updateCartItemQuantityByCartItemIdServlet(req,resp);
		}else if("loadCartItems".equals(oper)) {
			//调用加载多个CartItem方法
			loadCartItemsServlet(req,resp);
		}
	}
	
	
	/**
	 * 加载多个CartItem方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void loadCartItemsServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String cartItemIds=req.getParameter("cartItemIds");
		//声明变量，调用Service的loadCartItemsService(String cartItemIds);
		List<CartItem> cartItemsList=caService.loadCartItemsService(cartItemIds);
		//响应处理结果
		req.setAttribute("cartItemsList", cartItemsList);
		req.setAttribute("cartItemIds", cartItemIds);
		req.getRequestDispatcher("jsps/cart/showitem.jsp").forward(req, resp);
	}



	/**
	 * 修改购物车条目中数量的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private void updateCartItemQuantityByCartItemIdServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		//声明变量
		CartItem cartItem=null;
		//获取数据
		User user=(User) req.getSession().getAttribute("sessionUser");
		if(user==null) {
			resp.getWriter().write("您还没有登录，请登录");
			return;
		}
		String cartItemId=req.getParameter("cartItemId");
		int quantity=Integer.parseInt(req.getParameter("quantity"));
		//调用Service的updateCartItemQuantityByCartItemIdService(String cartItemId,int quantity)方法
		cartItem=caService.updateCartItemQuantityByCartItemIdService(cartItemId,quantity);
		Object[] quanAndSub= {cartItem.getQuantity(),cartItem.getSubtotal()};
		//响应处理结果
		resp.getWriter().write(new Gson().toJson(quanAndSub));
		
	}




	/**
	 * 批量删除购物车条目的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void batchDeleteCartItemServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		String cartItemIds=req.getParameter("cartItemIds");
		User user=(User) req.getSession().getAttribute("sessionUser");
		if(user==null) {
			resp.getWriter().write("您还没有登录，请登录");
			return;
		}
		caService.batchDeleteCartItemService(cartItemIds);
		findCartItemByUserServlet(req, resp);
		return;
	}





	/**
	 * 添加购物车条目的方法
	 * @param req
	 * @param resp
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addCartItemByUidAndBidServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//声明CartItem对象
		CartItem cartItem=null;
		//声明Book对象
		Book book=new Book();
		//获取数据
		String bid=req.getParameter("bid");
		book.setBid(bid);
		int quantity=Integer.parseInt(req.getParameter("quantity"));
		User user=(User) req.getSession().getAttribute("sessionUser");
		if(user!=null) {
			cartItem=new CartItem(null,quantity,book,user);
			//调用Service的addCartItemByUidAndBidService(CartItem cartItem)添加购物车条目的方法
			caService.addCartItemByUidAndBidService(cartItem);
			findCartItemByUserServlet(req,resp);
			return;
		}else {
			resp.getWriter().write("您还没有登录，请登录");
			return;
		}
	}
	/**
	 * 通过User查找购物车条目的方法
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findCartItemByUserServlet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//获取数据
		User user=(User) req.getSession().getAttribute("sessionUser");
		if(user==null) {
			resp.getWriter().write("您还没有登录，请登录");
			return;
		}
		//调用Service中的findCartItemByUserService(user);方法查询
		List<CartItem> cartItemList=caService.findCartItemByUserService(user);
		user=new UserServiceImpl().FindUserByNameAndPwdService(user.getName(), user.getPwd());
		//响应处理结果
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("sessionUser", user);
		req.getRequestDispatcher("/jsps/cart/list.jsp").forward(req, resp);
		return;	
	}

}
