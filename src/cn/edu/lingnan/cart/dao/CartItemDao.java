package cn.edu.lingnan.cart.dao;

import java.sql.ResultSet;
import java.util.List;

import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.user.bean.User;

/**
 * 购物车条目的持久层
 * @author Administrator
 *
 */
public interface CartItemDao {

	/**
	 * 通过user查询购物车条目
	 * @param user
	 * @return
	 */
	List<CartItem> findCartItemByUserDao(User user);
	/**
	 * 通过uid和bid查询购物车条目
	 * @param uid
	 * @param bid
	 * @return
	 */
	CartItem findCartItemByUidAndBidDao(int uid, String bid);
	/**
	 * 修改订单条目的数量
	 * @param cartItemId
	 * @param quantity
	 */
	boolean updateQuantityByCartItemIdDao(String cartItemId, int quantity);
	/**
	 * 添加订单条目信息
	 * @param cartItem
	 * @return
	 */
	boolean addCartItemByUidAndBidDao(CartItem cartItem);
	/**
	 * 批量删除购物车条目中生成where后面的sql语句
	 * @param length
	 * @return
	 */
	String tpWhereSql(int length);
	/**
	 * 批量删除购物车条目
	 * @param cartItems
	 */
	void batchDeleteCartItemDao(String cartItemIds);
	/**
	 * 修改购物车条目中的数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	CartItem updateCartItemQuantityByCartItemIdDao(String cartItemId, int quantity);
	/**
	 * 通过cartItemId查询购物车条目
	 * @param cartItemId
	 * @return
	 */
	CartItem findCartItemByCartItemIdDao(String cartItemId);
	/**
	 * 加载多个CartItem
	 * @param cartItems
	 * @return
	 */
	List<CartItem> loadCartItemsDao(String cartItemIds);
	/**
	 * 封装CartItem信息
	 * @param rSet
	 * @return
	 */
	CartItem Encapsulation(ResultSet rSet,int uid);
	/**
	 * 封装CartItem信息到CartItem
	 * @param rSet
	 * @return
	 */
	CartItem EncapsulationCartItem(ResultSet rSet,int uid);
	/**
	 * 封装CartItem信息到CartItem
	 * @param rSet
	 * @return
	 */
	List<CartItem> EncapsulationCartItemList(ResultSet rSet,int uid);
}
