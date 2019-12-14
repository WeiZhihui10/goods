package cn.edu.lingnan.cart.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.cart.dao.CartItemDao;
import cn.edu.lingnan.cart.dao.impl.CartItemDaoImpl;
import cn.edu.lingnan.cart.service.CartItemService;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.util.IDGenerator;

/**
 * 购物车条目的业务层的实现类
 * @author Administrator
 *
 */
public class CartItemServiceImpl implements CartItemService {
	//声明日志对象
	Logger logger=Logger.getLogger(CartItemServiceImpl.class);
	//声明CartItemDao对象
	CartItemDao cartItemDao=new CartItemDaoImpl();

	/**
	 * 通过用户查询购物车条目的方法
	 * @param user
	 * @return
	 */
	public List<CartItem> findCartItemByUserService(User user) {
		return cartItemDao.findCartItemByUserDao(user);
	}

	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	public boolean addCartItemByUidAndBidService(CartItem cartItem) {
		//声明变量
		int uid=cartItem.getUser().getId();
		String bid=cartItem.getBook().getBid();
		String cartItemId=IDGenerator.getUUID();
		cartItem.setCartItemId(cartItemId);
		//调用cartItemDao的findCartItemByUidAndBid(int uid,String bid);查询购物车条目
		CartItem _cartItem=cartItemDao.findCartItemByUidAndBidDao(uid,bid);
		if(_cartItem!=null) {
			//如果订单条目已经存在，修改条目数量
			return cartItemDao.updateQuantityByCartItemIdDao(_cartItem.getCartItemId(),_cartItem.getQuantity()+cartItem.getQuantity());
		}else {
			return cartItemDao.addCartItemByUidAndBidDao(cartItem);
		}
		
	}

	/**
	 * 批量删除购物车条目
	 * @param cartItemIds
	 */
	public void batchDeleteCartItemService(String cartItemIds) {
		//调用cartItemDao的 batchDeleteCartItemDao(String cartItemIds)删除购物车条目
		cartItemDao.batchDeleteCartItemDao(cartItemIds);
	}

	/**
	 * 修改购物车条目中的数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateCartItemQuantityByCartItemIdService(String cartItemId, int quantity) {
		return cartItemDao.updateCartItemQuantityByCartItemIdDao(cartItemId,quantity);
	}

	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItemsService(String cartItemIds) {
		return cartItemDao.loadCartItemsDao(cartItemIds);
	}
}
