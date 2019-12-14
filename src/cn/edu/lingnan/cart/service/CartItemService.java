package cn.edu.lingnan.cart.service;

import java.util.List;

import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.user.bean.User;

/**
 * 购物车条目的业务层
 * @author Administrator
 *
 */
public interface CartItemService {

	/**
	 * 通过用户查询购物车条目
	 * @param user
	 * @return
	 */
	List<CartItem> findCartItemByUserService(User user);
	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	boolean addCartItemByUidAndBidService(CartItem cartItem);
	/**
	 * 批量删除购物车条目
	 * @param cartItemIds
	 */
	void batchDeleteCartItemService(String cartItemIds);
	/**
	 * 修改购物车条目中的数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	CartItem updateCartItemQuantityByCartItemIdService(String cartItemId, int quantity);
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 */
	List<CartItem> loadCartItemsService(String cartItemIds);
	
}
