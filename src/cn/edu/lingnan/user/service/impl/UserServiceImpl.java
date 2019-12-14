package cn.edu.lingnan.user.service.impl;

import org.apache.log4j.Logger;

import cn.edu.lingnan.user.bean.User;
import cn.edu.lingnan.user.dao.UserDao;
import cn.edu.lingnan.user.dao.impl.UserDaoImpl;
import cn.edu.lingnan.user.service.UserService;

/**
 * 用户模块业务层实现类
 * @author 魏智辉
 *
 */
public class UserServiceImpl implements UserService {
	//声明日志对象
	Logger logger=Logger.getLogger(UserServiceImpl.class);
	//声明dao层对象
	private UserDao userDao=new UserDaoImpl();
	
	//用户注册ServiceImpl
	public boolean UserRegistService(User user) {
		//声明变量
		boolean flag=false;
		flag=userDao.userRegistDao(user);
		return flag;
	}
	

	//用户注册用户名校验
	public boolean UserAjaxValidateLoginnameService(String loginname) {
		//返回结果
		return userDao.userAjaxValidateLoginnameDao(loginname);
	}

	//邮箱注册校验
	public boolean UserAjaxVilidateEmailService(String email) {
		//返回结果
		return userDao.userAjaxVilidateEmailDao(email);
	}

	//用户登录
	public User FindUserByNameAndPwdService(String name, String pwd) {
		//返回结果
		return userDao.findUserByNameAndPwd(name, pwd);
	}


	public boolean updateUserPwdService(String name, String oldPwd, String newPwd) {
		//校验用户
		User user=userDao.findUserByNameAndPwd(name, oldPwd);
		if(user==null) {
			return false;
		}
		//修改密码
		return userDao.updateUserPwdDao(name,newPwd);
	}
}
