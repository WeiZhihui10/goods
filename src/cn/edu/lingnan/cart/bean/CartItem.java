package cn.edu.lingnan.cart.bean;
/**
 * 购物车条目的实体类
 * @author Administrator
 *
 */

import java.math.BigDecimal;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.user.bean.User;

public class CartItem {
	private String cartItemId;//主键
	private int quantity;//数量
	private Book book;//条目对应的图书
	private User user;//条目所属用户
	
	//添加小计方法
	public double getSubtotal() {
		//计算钱的时候，需要精确。使用BigDecimal不会有误差，要求必须使用String类型构造器
		BigDecimal b1=new BigDecimal(book.getCurrPrice()+"");
		BigDecimal b2=new BigDecimal(quantity+"");
		BigDecimal b3=b1.multiply(b2);
		return b3.doubleValue();
	}
	
	public String getCartItemId() {
		return cartItemId;
	}
	
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((cartItemId == null) ? 0 : cartItemId.hashCode());
		result = prime * result + quantity;
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
		CartItem other = (CartItem) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (cartItemId == null) {
			if (other.cartItemId != null)
				return false;
		} else if (!cartItemId.equals(other.cartItemId))
			return false;
		if (quantity != other.quantity)
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
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity + ", book=" + book + ", user=" + user
				+ "]";
	}

	public CartItem(String cartItemId, int quantity, Book book, User user) {
		super();
		this.cartItemId = cartItemId;
		this.quantity = quantity;
		this.book = book;
		this.user = user;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
