package cn.edu.lingnan.order.bean;
/**
 * 订单条目实体类
 * @author Administrator
 *
 */

import cn.edu.lingnan.book.bean.Book;

public class OrderItem {
	private String orderItemId;//主键
	private int quantity;//数量
	private double subtotal;//小计
	private Book book;//所关联的book
	private Order order;//所属的订单
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((orderItemId == null) ? 0 : orderItemId.hashCode());
		result = prime * result + quantity;
		long temp;
		temp = Double.doubleToLongBits(subtotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		OrderItem other = (OrderItem) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (orderItemId == null) {
			if (other.orderItemId != null)
				return false;
		} else if (!orderItemId.equals(other.orderItemId))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(subtotal) != Double.doubleToLongBits(other.subtotal))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", quantity=" + quantity + ", subtotal=" + subtotal + ", book="
				+ book + ", order=" + order + "]";
	}
	public OrderItem(String orderItemId, int quantity, double subtotal, Book book, Order order) {
		super();
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.book = book;
		this.order = order;
	}
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
