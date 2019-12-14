package cn.edu.lingnan.category.bean;
/**
 * 分类模块的实体类
 * @author Administrator
 *
 */

import java.util.List;

public class Category {
	private String cid;//主键
	private String cname;//分类名称
	private Category parent;//父分类
	private String desc;//分类描述
	private List<Category> children;//子分类
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		Category other = (Category) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (cname == null) {
			if (other.cname != null)
				return false;
		} else if (!cname.equals(other.cname))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname + ", parent=" + parent + ", desc=" + desc + ", children="
				+ children + "]";
	}
	public Category(String cid, String cname, Category parent, String desc, List<Category> children) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.parent = parent;
		this.desc = desc;
		this.children = children;
	}
	public Category(String cid, String cname, Category parent, String desc) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.parent = parent;
		this.desc = desc;
	}
	public Category() {
		super();
		
	}
	
}
