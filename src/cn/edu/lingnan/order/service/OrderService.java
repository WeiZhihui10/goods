package cn.edu.lingnan.order.service;
/**
 * Order模块的业务层
 * @author Administrator
 *
 */

import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.pager.PageBean;

public interface OrderService {
	/**
	 * 通过uid查找订单
	 * @param uid
	 * @return
	 */
	PageBean<Order> findOrderByUidService(int uid,int pc);
	/**
	 * 添加订单及订单条目
	 * @param order
	 * @return
	 */
	boolean addOrderAndOrderItemService(Order order);
	/**
	 * 通过oid查询订单信息
	 * @param oid
	 * @return
	 */
	Order findOrderByOidService(String oid);
	/**
	 * 通过oid查询订单的状态
	 * @param oid
	 * @return
	 */
	int findOrderStatusByOidService(String oid);
	/**
	 * 通过oid修改订单状态
	 * @param oid
	 * @return
	 */
	boolean updateOrderStatusByOidService(String oid,int status);
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
	PageBean<Order> findOrderByStatusService(int status ,int pc);
	/**
	 * 按状态查询指定用户的订单
	 * @param uid
	 * @param status
	 * @param pc
	 * @return
	 */
	PageBean<Order> findOrderByStatusService(int uid, int status, int pc);
}
