package cn.edu.lingnan.pager;

import java.util.List;

public class PageBean<E> {
	private int pc;//当前页码
	private int tr;//总记录数
	private int ps;//每页记录数
	private String url;//请求路径和参数，例如：/BookServlet?oper=findBook&cid=1
	private List<E> beanList;
	private int tp;//总页数
	
	//计算总页数
	public int getTp() {
		tp=tr/ps;
		return tr%ps==0 ? tp:tp+1;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageBean(int pc, int tr, int ps, String url, List<E> beanList) {
		super();
		this.pc = pc;
		this.tr = tr;
		this.ps = ps;
		this.url = url;
		this.beanList = beanList;
	}
	@Override
	public String toString() {
		return "PageBean [pc=" + pc + ", tr=" + tr + ", ps=" + ps + ", url=" + url + ", beanList=" + beanList + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beanList == null) ? 0 : beanList.hashCode());
		result = prime * result + pc;
		result = prime * result + ps;
		result = prime * result + tp;
		result = prime * result + tr;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		PageBean<?> other = (PageBean<?>) obj;
		if (beanList == null) {
			if (other.beanList != null)
				return false;
		} else if (!beanList.equals(other.beanList))
			return false;
		if (pc != other.pc)
			return false;
		if (ps != other.ps)
			return false;
		if (tp != other.tp)
			return false;
		if (tr != other.tr)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<E> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<E> beanList) {
		this.beanList = beanList;
	}
	
}
