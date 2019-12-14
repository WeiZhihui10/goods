package cn.edu.lingnan.book.bean;

import cn.edu.lingnan.category.bean.Category;

public class Book {
	private String bid;//主键
	private String bname;//书名
	private String author;//作者
	private double price;//定价
	private double currPrice;//当前价
	private double discount;//折扣
	private String press;//出版社
	private String publishtime;//出版时间
	private int edition;//版次
	private int pageNum;//页数
	private int wordNum;//字数
	private String printtime;//刷新时间
	private int booksize;//开本
	private String paper;//纸张材料
	private int number;//数量
	private Category category;//所属分类
	private String image_w;//大图路径
	private String image_b;//小图路径
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(double currPrice) {
		this.currPrice = currPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getWordNum() {
		return wordNum;
	}
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}
	public String getPrinttime() {
		return printtime;
	}
	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}
	public int getBooksize() {
		return booksize;
	}
	public void setBooksize(int booksize) {
		this.booksize = booksize;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getImage_w() {
		return image_w;
	}
	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}
	public String getImage_b() {
		return image_b;
	}
	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bid == null) ? 0 : bid.hashCode());
		result = prime * result + ((bname == null) ? 0 : bname.hashCode());
		result = prime * result + booksize;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		long temp;
		temp = Double.doubleToLongBits(currPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(discount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + edition;
		result = prime * result + ((image_b == null) ? 0 : image_b.hashCode());
		result = prime * result + ((image_w == null) ? 0 : image_w.hashCode());
		result = prime * result + number;
		result = prime * result + pageNum;
		result = prime * result + ((paper == null) ? 0 : paper.hashCode());
		result = prime * result + ((press == null) ? 0 : press.hashCode());
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((printtime == null) ? 0 : printtime.hashCode());
		result = prime * result + ((publishtime == null) ? 0 : publishtime.hashCode());
		result = prime * result + wordNum;
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bid == null) {
			if (other.bid != null)
				return false;
		} else if (!bid.equals(other.bid))
			return false;
		if (bname == null) {
			if (other.bname != null)
				return false;
		} else if (!bname.equals(other.bname))
			return false;
		if (booksize != other.booksize)
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (Double.doubleToLongBits(currPrice) != Double.doubleToLongBits(other.currPrice))
			return false;
		if (Double.doubleToLongBits(discount) != Double.doubleToLongBits(other.discount))
			return false;
		if (edition != other.edition)
			return false;
		if (image_b == null) {
			if (other.image_b != null)
				return false;
		} else if (!image_b.equals(other.image_b))
			return false;
		if (image_w == null) {
			if (other.image_w != null)
				return false;
		} else if (!image_w.equals(other.image_w))
			return false;
		if (number != other.number)
			return false;
		if (pageNum != other.pageNum)
			return false;
		if (paper == null) {
			if (other.paper != null)
				return false;
		} else if (!paper.equals(other.paper))
			return false;
		if (press == null) {
			if (other.press != null)
				return false;
		} else if (!press.equals(other.press))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (printtime == null) {
			if (other.printtime != null)
				return false;
		} else if (!printtime.equals(other.printtime))
			return false;
		if (publishtime == null) {
			if (other.publishtime != null)
				return false;
		} else if (!publishtime.equals(other.publishtime))
			return false;
		if (wordNum != other.wordNum)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", author=" + author + ", price=" + price + ", currPrice="
				+ currPrice + ", discount=" + discount + ", press=" + press + ", publishtime=" + publishtime
				+ ", edition=" + edition + ", pageNum=" + pageNum + ", wordNum=" + wordNum + ", printtime=" + printtime
				+ ", booksize=" + booksize + ", paper=" + paper + ", number=" + number + ", category=" + category
				+ ", image_w=" + image_w + ", image_b=" + image_b + "]";
	}
	public Book(String bid, String bname, String author, double price, double currPrice, double discount, String press,
			String publishtime, int edition, int pageNum, int wordNum, String printtime, int booksize, String paper,
			int number, Category category, String image_w, String image_b) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.author = author;
		this.price = price;
		this.currPrice = currPrice;
		this.discount = discount;
		this.press = press;
		this.publishtime = publishtime;
		this.edition = edition;
		this.pageNum = pageNum;
		this.wordNum = wordNum;
		this.printtime = printtime;
		this.booksize = booksize;
		this.paper = paper;
		this.number = number;
		this.category = category;
		this.image_w = image_w;
		this.image_b = image_b;
	}
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
}
