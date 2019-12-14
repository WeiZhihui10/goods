package cn.edu.lingnan.user.bean;

import java.util.List;


/**
 * 用户模快实体类
 * @author 魏智辉
 *
 *属性：
 *1、t_user表
 *2、该模块所有表单，因为需要把表单的数据封装到User对象中
 */
public class User {
	//对应数据库表
	private int id;//主键，用户id
	private String name;//用户名
	private String pwd;//密码
	private String email;//邮箱
	//对应注册表单
	private String loginPwd2;//确认密码
	private String verifyCode;//验证码
	//对应修改密码表单
	private String newPwd;
	private List<Address> addresseList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginPwd2() {
		return loginPwd2;
	}

	public void setLoginPwd2(String loginPwd2) {
		this.loginPwd2 = loginPwd2;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public List<Address> getAddresseList() {
		return addresseList;
	}

	public void setAddresseList(List<Address> addresseList) {
		this.addresseList = addresseList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresseList == null) ? 0 : addresseList.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((loginPwd2 == null) ? 0 : loginPwd2.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((newPwd == null) ? 0 : newPwd.hashCode());
		result = prime * result + ((pwd == null) ? 0 : pwd.hashCode());
		result = prime * result + ((verifyCode == null) ? 0 : verifyCode.hashCode());
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
		User other = (User) obj;
		if (addresseList == null) {
			if (other.addresseList != null)
				return false;
		} else if (!addresseList.equals(other.addresseList))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (loginPwd2 == null) {
			if (other.loginPwd2 != null)
				return false;
		} else if (!loginPwd2.equals(other.loginPwd2))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newPwd == null) {
			if (other.newPwd != null)
				return false;
		} else if (!newPwd.equals(other.newPwd))
			return false;
		if (pwd == null) {
			if (other.pwd != null)
				return false;
		} else if (!pwd.equals(other.pwd))
			return false;
		if (verifyCode == null) {
			if (other.verifyCode != null)
				return false;
		} else if (!verifyCode.equals(other.verifyCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", pwd=" + pwd + ", email=" + email + ", loginPwd2=" + loginPwd2
				+ ", verifyCode=" + verifyCode + ", newPwd=" + newPwd + ", addresseList=" + addresseList + "]";
	}

	public User(int id, String name, String pwd, String email, String loginPwd2, String verifyCode, String newPwd,
			List<Address> addresseList) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.loginPwd2 = loginPwd2;
		this.verifyCode = verifyCode;
		this.newPwd = newPwd;
		this.addresseList = addresseList;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, String pwd, String email) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
	}
}
