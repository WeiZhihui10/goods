package cn.edu.lingnan.order.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.lingnan.book.dao.BookDao;
import cn.edu.lingnan.book.dao.impl.BookDaoImpl;
import cn.edu.lingnan.order.bean.Order;
import cn.edu.lingnan.order.bean.OrderItem;
import cn.edu.lingnan.order.dao.OrderDao;
import cn.edu.lingnan.pager.Expression;
import cn.edu.lingnan.pager.PageBean;
import cn.edu.lingnan.pager.PageConstants;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.dao.UserDao;
import cn.edu.lingnan.user.dao.impl.UserDaoImpl;
import cn.edu.lingnan.util.JDBC;

/**
 * order模块的持久层的实现类
 * @author Administrator
 *
 */
public class OrderDaoImpl implements OrderDao {
	/**
	 * 通用的分页查询方法
	 * @param expressions
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findByCriteriaDao(List<Expression> exprList, int pc) {
		
		//创建jdbc对象
		ResultSet rSet=null;
		//声明变量
		int tr = 0;//总数据量
		Object[] params=new Object[exprList.size()];//占位符辅赋值数组
		int k=0;
		int ps;//订单每页记录数
		/*
		 * 1.得到ps
		 * 2.得到tr
		 * 3.得到beanList
		 * 4.创建PageBean，返回
		 */
		//1.得到ps
		ps=PageConstants.ORDER_PAGE_SIZE;//每页记录数
		//2.通过exprList生成where子句
		StringBuilder whereSql=new StringBuilder(" where 1=1");
		for(Expression expr:exprList) {
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			if(!"is null".equals(expr.getValue())) {
				whereSql.append("?");
				params[k]=expr.getValue();
				k++;
			}
		}

		//3.得到总记录数
			//创建sql语句
			String sql="select count(*) from t_order"+whereSql;
			try {
				//执行sql语句
				rSet=JDBC.executeQuery(sql, params);
				//获取结果集结果
				if(rSet.next()) {
					tr=rSet.getInt("count(*)");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
			}
		//4.得到beanList,即当前页记录
			//声明变量
			List<Order> beanList=new ArrayList<Order>();
			//创建sql语句
			sql="select * from t_order"+whereSql+"  order by ordertime desc limit ?,?";
			int length=params.length;
			//给占位符赋值
			Object[] params1= {(pc-1)*ps,ps};
			Object[] params2=new Object[params.length+params1.length];
			System.arraycopy(params, 0, params2, 0, length);
			System.arraycopy(params1, 0, params2, length, params1.length);
			ResultSet rSet1=null;
			//执行sql
			rSet1=JDBC.executeQuery(sql, params2);
			beanList=EncapsulationOrderList(rSet1);
		//5.创建PageBean,设置参数
		PageBean<Order> pageBean=new PageBean<Order>();
		pageBean.setBeanList(beanList);
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setTr(tr);
		return pageBean;
	}

	/**
	 * 封装Order信息
	 * @param rSet
	 * @return
	 */
	public Order Encapsulation(ResultSet rSet) {
		Order order=new Order();
		try {
			order=new Order();
			order.setOid(rSet.getString("oid"));
			order.setOrdertime(rSet.getString("ordertime"));
			order.setTotal(rSet.getDouble("total"));
			order.setStatus(rSet.getInt("status"));
			order.setAddress(rSet.getString("address"));
			UserDao userDao=new UserDaoImpl();
			User user=userDao.findUserByUidDao(rSet.getInt("uid"));
			order.setUser(user);
			List<OrderItem> orderItemList=findOrderItemByOidDao(rSet.getString("oid"));
			order.setOrderItemList(orderItemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	/**
	 * 封装Order信息到Order
	 * @param rSet
	 * @return
	 */
	public Order EncapsulationOrder(ResultSet rSet) {
		Order order=null;
		try {
			if(rSet.next()) {
			order=Encapsulation(rSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return order;
	}

	/**
	 * 封装Order信息到List<Order>
	 * @param rSet
	 * @return
	 */
	public List<Order> EncapsulationOrderList(ResultSet rSet) {
		List<Order> orders=new ArrayList<Order>();
		Order order=null;
		try {
			while(rSet.next()){
				order=Encapsulation(rSet);
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return orders;
	}

	public OrderItem EncapsulationItem(ResultSet rSet) {
		OrderItem orderItem=null;
		try {
			orderItem=new OrderItem();
			orderItem.setOrderItemId(rSet.getString("orderItemId"));
			orderItem.setQuantity(rSet.getInt("quantity"));
			orderItem.setSubtotal(rSet.getDouble("subtotal"));
			Order order=new Order();
			order.setOid(rSet.getString("oid"));
			orderItem.setOrder(order);
			BookDao bookDao=new BookDaoImpl();
			orderItem.setBook(bookDao.findBookByBidDao(rSet.getString("bid")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderItem;
	}

	/**
	 * 封装OrderItem
	 * @param rSet
	 * @return
	 */
	public OrderItem EncapsulationOrderItem(ResultSet rSet) {
		OrderItem orderItem=null;
		try {
			if(rSet.next()) {
				orderItem=EncapsulationItem(rSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return orderItem;
	}

	/**
	 * 封装OrderItem信息到List<OrderItem>
	 * @param rSet
	 * @return
	 */
	public List<OrderItem> EncapsulationOrderItemList(ResultSet rSet) {
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		OrderItem orderItem=null;
		try {
			while(rSet.next()) {
				orderItem=EncapsulationItem(rSet);
				orderItems.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return orderItems;
	}

	/**
	 * 通过uid查询我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findOrderByUidDao(int uid, int pc) {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid+""));
		return findByCriteriaDao(exprList, pc);
	}
	
	/**
	 * 通过oid查询订单详情
	 * @param oid
	 * @return
	 */
	public List<OrderItem> findOrderItemByOidDao(String oid) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建OrderItem对象
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		//创建Sql语句
		String sql="select * from t_orderitem where oid=?";
		//给占位符赋值
		Object[] params= {oid};
		//执行sql命令
		rSet=JDBC.executeQuery(sql, params);
		orderItems=EncapsulationOrderItemList(rSet);
		return orderItems;
	}


	
	/**
	 * 添加Order,同时向t_orderItem插入订单条目。一个订单可以包含多个订单条目
	 * @param order
	 * @return
	 */
	public boolean addOrderAndOrderItemDao(Order order) {
		//1.创建sql语句,插入订单
		String sql="insert into t_order values(?,?,?,?,?,?)";
		//给占位符赋值
		Object[] params={order.getOid(),order.getOrdertime(),order.getTotal(),order.getStatus(),
				order.getAddress(),order.getUser().getId()};
		//执行sql语句
		boolean flag=JDBC.executeUpdate(sql, params);
		//2.创建sql语句，插入订单条目
		sql="insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		//给占位符赋值
		OrderItem orderItem=new OrderItem();
		int length=order.getOrderItemList().size();
		for(int i=0;i<length;i++) {
			orderItem=order.getOrderItemList().get(i);
			Object[] params1= {orderItem.getOrderItemId(),orderItem.getQuantity(),orderItem.getSubtotal(),
					orderItem.getBook().getBid(),orderItem.getBook().getBname(),orderItem.getBook().getCurrPrice(),
					orderItem.getBook().getImage_b(),order.getOid()};
			//执行sql命令
			flag=JDBC.executeUpdate(sql, params1);
		}
		
		return flag;
	}

	/**
	 * 通过oid查询Order
	 * @param oid
	 * @return
	 */
	public Order findOrderByOidDao(String oid) {
		//创建jdbc对象
		ResultSet rSet=null;
		Order order=null;
		//创建sql语句
		String sql="select * from t_order where oid=?";
		//给占位符赋值
		Object[] params={oid};
		//执行sql命令
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果	
		order=EncapsulationOrder(rSet);
		return order;
	}

	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findOrderStatusByOidDao(String oid) {
		//创建jdbc对象
		ResultSet rSet=null;
		int index=-1;
		//创建sql语句
		String sql="select * from t_order where oid=?";
		//给占位符赋值
		Object[] params= {oid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果
		Order order=EncapsulationOrder(rSet);
		index=order.getStatus();
		return index;
	}

	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @return
	 */
	public boolean updateOrderStatusByOidDao(String oid, int status) {
		//创建sql语句
		String sql="update t_order set status=? where oid=?";
		//给占位符赋值
		Object[] params= {status,oid};
		//执行sql命令
		boolean flag=JDBC.executeUpdate(sql, params);
		return flag;
	}

	/**
	 * 后台管理查询所有订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAllOrderDao(int pc) {
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteriaDao(exprList, pc);
	}

	/**
	 * 按状态查询订单
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findOrderByStatusDao(int status,int pc) {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status", "=", status+""));
		return findByCriteriaDao(exprList, pc);
	}

	/**
	 * 按状态查询指定用户的订单
	 * @param uid
	 * @param status
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findOrderByStatusDao(int uid, int status, int pc) {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid+""));
		exprList.add(new Expression("status", "=", status+""));
		return findByCriteriaDao(exprList, pc);
	}
}
