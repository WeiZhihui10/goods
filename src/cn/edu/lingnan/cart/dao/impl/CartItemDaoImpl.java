package cn.edu.lingnan.cart.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.book.dao.BookDao;
import cn.edu.lingnan.book.dao.impl.BookDaoImpl;
import cn.edu.lingnan.cart.bean.CartItem;
import cn.edu.lingnan.cart.dao.CartItemDao;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.dao.UserDao;
import cn.edu.lingnan.user.dao.impl.UserDaoImpl;
import cn.edu.lingnan.util.JDBC;
/**
 * 购物车条目的持久层的实现类
 * @author Administrator
 *
 */
public class CartItemDaoImpl implements CartItemDao {
	/**
	 * 通过user查询购物车条目
	 * @param user
	 * @return
	 */
	public List<CartItem> findCartItemByUserDao(User user) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_cartitem where uid=? order by orderBy";
		//给占位符赋值
		Object[] params= {user.getId()};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果集
		List<CartItem> cartItemList=EncapsulationCartItemList(rSet, user.getId());
		return cartItemList;
	}

	/**
	 * 通过uid和bid查询购物车条目
	 * @param uid
	 * @param bid
	 * @return
	 */
	public CartItem findCartItemByUidAndBidDao(int uid, String bid) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_cartitem where uid=? and bid=?";
		//给占位符赋值
		Object[] params= {uid,bid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//声明CartItem对象
		CartItem cartItem=EncapsulationCartItem(rSet, uid);
		return cartItem;
	}

	/**
	 * 修改订单条目的数量
	 * @param cartItemId
	 * @param quantity
	 */
	public boolean updateQuantityByCartItemIdDao(String cartItemId, int quantity) {
		//创建sql语句
		String sql="update t_cartitem set quantity=? where cartitemid=?";
		//给占位符赋值
		Object[] params= {quantity,cartItemId};
		//执行sql语句
		return JDBC.executeUpdate(sql, params);
	}

	/**
	 * 添加订单条目信息
	 * @param cartItem
	 * @return
	 */
	public boolean addCartItemByUidAndBidDao(CartItem cartItem) {
		//创建sql语句
		String sql="insert into t_cartitem values(?,?,?,?,default)";
		//给占位符赋值
		Object[] params= {cartItem.getCartItemId(),cartItem.getQuantity(),cartItem.getBook().getBid(),cartItem.getUser().getId()};
		//执行sql语句
		return JDBC.executeUpdate(sql, params);
	}

	/**
	 * 删除购物车条目时生成where后面的sql语句
	 * @param length
	 * @return
	 */
	public String tpWhereSql(int length) {
		StringBuilder sBuilder=new StringBuilder("cartItemId in (");
		for(int i=0;i<length;i++) {
			sBuilder.append("?");
			if(i<length-1) {
				sBuilder.append(",");
			}
		}
		sBuilder.append(")");
		return sBuilder.toString();
	}

	/**
	 * 批量删除购物车条目
	 * @param cartItems
	 */
	public void batchDeleteCartItemDao(String cartItemIds) {
		//需要将cartItems转换成数组。
		Object[] cartItemIdArray=cartItemIds.split(",");
		String whereSql=tpWhereSql(cartItemIdArray.length);
		//创建sql语句
		String sql="delete from t_cartitem where "+whereSql;
		//执行sql语句
		JDBC.executeUpdate(sql, cartItemIdArray);
	}

	/**
	 * 修改购物车条目中的数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateCartItemQuantityByCartItemIdDao(String cartItemId, int quantity) {
		//创建sql语句
		String sql="update t_cartitem set quantity=? where cartitemid=?";
		//给占位符赋值
		Object[] params= {quantity,cartItemId};
		JDBC.executeUpdate(sql, params);
		return findCartItemByCartItemIdDao(cartItemId);
	}

	/**
	 * 通过cartItemId查询购物车条目
	 * @param cartItemId
	 * @return
	 */
	public CartItem findCartItemByCartItemIdDao(String cartItemId) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_cartitem where cartitemid=?";
		//给占位符赋值
		Object[] params= {cartItemId};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//声明变量
		CartItem cartItem=EncapsulationCartItem(rSet, -1);
		return cartItem;
	}

	/**
	 * 加载多个CartItem
	 * @param cartItems
	 * @return
	 */
	public List<CartItem> loadCartItemsDao(String cartItemIds) {
		//创建jdbc对象
		ResultSet rSet=null;
		//将cartItems转换成数组
		Object[] params=cartItemIds.split(",");
		String whereSql=tpWhereSql(params.length);
		//创建sql语句
		String sql="select * from t_cartitem where "+whereSql;
		//执行sql命令
		rSet=JDBC.executeQuery(sql, params);
		//声明变量
		List<CartItem> cartItemList=EncapsulationCartItemList(rSet, -1);
		return cartItemList;
	}

	/**
	 * 封装CartItem信息
	 * @param rSet
	 * @return
	 */
	public CartItem Encapsulation(ResultSet rSet, int uid) {
		BookDao bookDao=new BookDaoImpl();
		CartItem cartItem=null;
		try {
			cartItem=new CartItem();
			cartItem.setCartItemId(rSet.getString("cartitemid"));
			cartItem.setQuantity(rSet.getInt("quantity"));
			Book book=new Book();
			book=bookDao.findBookByBidDao(rSet.getString("bid"));
			cartItem.setBook(book);
			UserDao userDao=new UserDaoImpl();
			User user=userDao.findUserByUidDao(uid);
			cartItem.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartItem;
	}

	/**
	 * 封装CartItem信息到CartItem
	 * @param rSet
	 * @return
	 */
	public CartItem EncapsulationCartItem(ResultSet rSet, int uid) {
		CartItem cartItem=null;
		try {
			
			if(rSet.next()) {
				cartItem=Encapsulation(rSet, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return cartItem;
	}

	/**
	 * 封装CartItem信息到List<CartItem>
	 * @param rSet
	 * @return
	 */
	public List<CartItem> EncapsulationCartItemList(ResultSet rSet, int uid) {
		List<CartItem> cartItemList=new ArrayList<CartItem>();
		CartItem cartItem=null;
		try {
			while(rSet.next()) {
				cartItem=new CartItem();
				cartItem=Encapsulation(rSet, -1);
				cartItemList.add(cartItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return cartItemList;
	}
	
}
