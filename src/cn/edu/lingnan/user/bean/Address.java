package cn.edu.lingnan.user.bean;

public class Address {
	private String aid;
	private String address;
	private int commonly;
	private User user;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCommonly() {
		return commonly;
	}
	public void setCommonly(int commonly) {
		this.commonly = commonly;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((aid == null) ? 0 : aid.hashCode());
		result = prime * result + commonly;
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
		Address other = (Address) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (aid == null) {
			if (other.aid != null)
				return false;
		} else if (!aid.equals(other.aid))
			return false;
		if (commonly != other.commonly)
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
		return "Address [aid=" + aid + ", address=" + address + ", commonly=" + commonly + ", user=" + user + "]";
	}
	public Address(String aid, String address, int commonly, User user) {
		super();
		this.aid = aid;
		this.address = address;
		this.commonly = commonly;
		this.user = user;
	}
	public Address() {
		super();
	}
	
	
}
