package cn.edu.lingnan.order.bean;
/**
 * order模块的实体类
 * @author Administrator
 *
 */

import java.util.List;

import cn.edu.lingnan.user.bean.User;

public class Order {
	private String oid;//主键
	private String ordertime;//下达时间
	private double total;//总计
	private int status;//订单状态：1表示未付款，2表示已付款，3已发货但未确认收获，4确认收货了交易完成，5已取消（只有未付款才能取消）
	private String address;//地址
	private User user; //订单所属用户
	private List<OrderItem> orderItemList;//订单条目
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		result = prime * result + ((orderItemList == null) ? 0 : orderItemList.hashCode());
		result = prime * result + ((ordertime == null) ? 0 : ordertime.hashCode());
		result = prime * result + status;
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		if (orderItemList == null) {
			if (other.orderItemList != null)
				return false;
		} else if (!orderItemList.equals(other.orderItemList))
			return false;
		if (ordertime == null) {
			if (other.ordertime != null)
				return false;
		} else if (!ordertime.equals(other.ordertime))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", status=" + status
				+ ", address=" + address + ", user=" + user + ", orderItemList=" + orderItemList + "]";
	}
	public Order(String oid, String ordertime, double total, int status, String address, User user,
			List<OrderItem> orderItemList) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.status = status;
		this.address = address;
		this.user = user;
		this.orderItemList = orderItemList;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
