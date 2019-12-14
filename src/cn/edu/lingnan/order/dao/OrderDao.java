package cn.edu.lingnan.order.dao;

import java.sql.ResultSet;
import java.util.List;


import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.order.bean.OrderItem;
import cn.edu.lingnan.pager.Expression;
import cn.edu.lingnan.pager.PageBean;

/**
 * order模块的持久层
 * @author Administrator
 *
 */
public interface OrderDao {

	/**
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 */
	PageBean<Order> findByCriteriaDao(List<Expression> exprList, int pc);
	/**
	 * 封装Order信息
	 * @param rSet
	 * @return
	 */
	Order Encapsulation(ResultSet rSet); 
	/**
	 * 封装Order信息到Order
	 * @param rSet
	 * @return
	 */
	Order EncapsulationOrder(ResultSet rSet); 
	/**
	 * 封装Order信息到List<Order>
	 * @param rSet
	 * @return
	 */
	List<Order> EncapsulationOrderList(ResultSet rSet);
	/**
	 * 封装OrderItem
	 * @param rSet
	 * @return
	 */
	OrderItem EncapsulationItem(ResultSet rSet);
	/**
	 * 封装OrderItem到OrderItem
	 * @param rSet
	 * @return
	 */
	OrderItem EncapsulationOrderItem(ResultSet rSet);
	/**
	 * 封装OrderItem信息到List<OrderItem>
	 * @param rSet
	 * @return
	 */
	List<OrderItem> EncapsulationOrderItemList(ResultSet rSet);
	/**
	 * 通过uid查询我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	PageBean<Order> findOrderByUidDao(int uid,int pc);
	/**
	 * 通过oid查询订单详情
	 * @param oid
	 * @return
	 */
	List<OrderItem> findOrderItemByOidDao(String oid);
	/**
	 * 添加Order
	 * @param order
	 * @return
	 */
	boolean addOrderAndOrderItemDao(Order order);
	/**
	 * 通过oid查询Order
	 * @param oid
	 * @return
	 */
	Order findOrderByOidDao(String oid);
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	int findOrderStatusByOidDao(String oid);
	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @return
	 */
	boolean updateOrderStatusByOidDao(String oid,int status);
	/**
	 * 后台管理查询所有订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	PageBean<Order> findAllOrderDao(int pc);
	/**
	 * 按状态查询订单
	 * @param pc
	 * @return
	 */
	PageBean<Order> findOrderByStatusDao(int status,int pc);
	/**
	 * 按状态查询指定用户的订单
	 * @param uid
	 * @param status
	 * @param pc
	 * @return
	 */
	PageBean<Order> findOrderByStatusDao(int uid, int status, int pc);
}
