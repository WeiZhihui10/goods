package cn.edu.lingnan.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cn.edu.lingnan.user.bean.Address;
import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.dao.AddressDao;
import cn.edu.lingnan.user.dao.UserDao;
import cn.edu.lingnan.util.JDBC;

/**
 * 用户模块持久层实现类
 * @author 魏智辉
 *
 */
public class UserDaoImpl implements UserDao {

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public boolean userRegistDao(User user) {
		//创建sql命令
		String sql="insert into t_user values(default,?,?,?)";
		//为占位符赋值
		Object[] params= {user.getName(),user.getPwd(),user.getEmail()};
		//执行sql
		return JDBC.executeUpdate(sql, params);
	}
	
	/**
	 * 校验用户名是否注册
	 * @param loginname 用户名
	 * @return 返回查询到的个数
	 */	
	public boolean userAjaxValidateLoginnameDao(String loginname) {
		//创建jdbc对象
		ResultSet rSet=null;
		//声明变量
		boolean flag=false;
		//创建sql语句
		String sql="select * from t_user where name=?";
		//给占位符赋值
		Object[] params= {loginname};
		//返回结果
		try {
			rSet=JDBC.executeQuery(sql, params);
			if(rSet.next()) {
				flag=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return flag;
	}
	
	/**
	 * 校验邮箱是否已注册
	 * @param email
	 * @return
	 */
	public boolean userAjaxVilidateEmailDao(String email) {
		//创建jdbc对象
		ResultSet rSet=null;
		//声明变量
		boolean flag=false;
		//创建sql语句
		String sql="select * from t_user where email=?";
		//给占位符赋值
		Object[] params= {email};
		//返回结果
		try {
			rSet=JDBC.executeQuery(sql, params);
			if(rSet.next()) {
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return flag;
	}
	
	/**
	 * 用户登录功能
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 返回用户信息
	 */
	public User findUserByNameAndPwd(String name, String pwd) {
		//创建jdbc对象
		ResultSet rSet=null;
		//声明变量
		User user=null;
		//创建sql语句
		String sql="select * from t_user where name=? and pwd=?";
		//给占位符赋值
		Object[] params= {name,pwd};
		//执行sql命令
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果集
		user=EncapsulationUser(rSet);
		//返回结果
		return user;
	}
	
	/**
	 * 修改用户密码
	 * @param name
	 * @param newPwd
	 * @return
	 */
	public boolean updateUserPwdDao(String name, String newPwd) {
		//创建sql语句
		String sql="update t_user set pwd=? where name=?";
		//给占位符赋值
		Object[] params= {newPwd,name};
		//执行sql命令,返回结果
		return JDBC.executeUpdate(sql, params);
	}

	/**
	 * 通过uid查找用户
	 * @param uid
	 * @return
	 */
	public User findUserByUidDao(int uid) {
		//创建jdbc对象
		ResultSet rSet=null;
		User user=null;
		//创建sql语句
		String sql="select * from t_user where id=?";
		//给占位符赋值
		Object[] params= {uid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		user=EncapsulationUser(rSet);
		return user;
	}

	/**
	 * 封装user信息
	 * @param rSet
	 * @return
	 */
	public User EncapsulationUser(ResultSet rSet) {
		AddressDao addressDao=new AddressDaoImpl();
		User user=null;
		try {
			if(rSet.next()) {
				user=new User();
				user.setId(rSet.getInt("id"));
				user.setName(rSet.getString("name"));
				user.setPwd(rSet.getString("pwd"));
				List<Address> addresseList=addressDao.findAddressByUidDao(rSet.getInt("id"));
				user.setAddresseList(addresseList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return user;
	}	
}
