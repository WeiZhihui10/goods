package cn.edu.lingnan.user.service;

import cn.edu.lingnan.user.bean.User;

/**
 * 用户模块业务层接口
 * @author 魏智辉
 *
 */
public interface UserService {
	/**
	 * 用户注册
	 * @param user 用户对象
	 * @return 返回true已注册成功，false未注册失败
	 */
	boolean UserRegistService(User user);
	/**
	 * 校验用户名是否注册
	 * @param loginname 用户名
	 * @return 返回true已注册，false未注册
	 */
	boolean UserAjaxValidateLoginnameService(String loginname);
	/**
	 * 校验邮箱是否注册
	 * @param email 邮箱
	 * @return 返回true已注册，false未注册
	 */
	boolean UserAjaxVilidateEmailService(String email);
	/**
	 * 用户登录
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 返回用户信息
	 */
	User FindUserByNameAndPwdService(String name, String pwd);
	/**
	 * 修改用户密码
	 * @param name 用户名
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return true成功，false失败
	 */
	boolean updateUserPwdService(String name, String oldPwd, String newPwd);
}
