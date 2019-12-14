package cn.edu.lingnan.order.service.impl;

import org.apache.log4j.Logger;

import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.order.dao.OrderDao;
import cn.edu.lingnan.order.dao.impl.OrderDaoImpl;
import cn.edu.lingnan.order.service.OrderService;
import cn.edu.lingnan.pager.PageBean;

/**
 * Order模块的业务层
 * @author Administrator
 *
 */
public class OrderServiceImpl implements OrderService {
	//声明OrderDao对象
	OrderDao orderDao=new OrderDaoImpl();
	//声明日志对象
	Logger logger=Logger.getLogger(OrderServiceImpl.class);
	
	/**
	 * 通过uid查找订单
	 * @param uid
	 * @return
	 */
	public PageBean<Order> findOrderByUidService(int uid,int pc) {
		return orderDao.findOrderByUidDao(uid, pc);
	}

	/**
	 * 添加订单及订单条目
	 * @param order
	 * @return
	 */
	public boolean addOrderAndOrderItemService(Order order) {
		return orderDao.addOrderAndOrderItemDao(order);
	}
	
	/**
	 * 通过oid查询订单信息
	 * @param oid
	 * @return
	 */
	public Order findOrderByOidService(String oid) {
		return orderDao.findOrderByOidDao(oid);
	}

	public int findOrderStatusByOidService(String oid) {
		return orderDao.findOrderStatusByOidDao(oid);
	}

	/**
	 * 通过oid修改订单状态
	 * @param oid
	 * @return
	 */
	public boolean updateOrderStatusByOidService(String oid,int status) {
		return orderDao.updateOrderStatusByOidDao(oid, status);
	}

	/**
	 * 后台管理查询所有订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAllOrderDao(int pc) {
		// 返回结果
		return orderDao.findAllOrderDao(pc);
	}

	/**
	 * 按状态查询订单
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findOrderByStatusService(int status, int pc) {
		// 返回结果
		return orderDao.findOrderByStatusDao(status, pc);
	}

	/**
	 * 按状态查询指定用户的订单
	 * @param uid
	 * @param status
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findOrderByStatusService(int uid, int status, int pc) {
		// 返回结果
		return orderDao.findOrderByStatusDao(uid,status,pc);
	}
}
