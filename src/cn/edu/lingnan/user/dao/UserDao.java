package cn.edu.lingnan.user.dao;

import java.sql.ResultSet;
import cn.edu.lingnan.user.bean.User;

/**
 * 用户模块持久层接口
 * @author 魏智辉
 *
 */
public interface UserDao {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	boolean userRegistDao(User user);
	/**
	 * 校验用户名是否注册
	 * @param loginname 用户名
	 * @return 返回查询到的个数
	 */
	boolean userAjaxValidateLoginnameDao(String loginname);
	/**
	 * 校验邮箱是否已注册
	 * @param email
	 * @return
	 */
	boolean userAjaxVilidateEmailDao(String email);
	/**
	 * 用户登录功能
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 返回用户信息
	 */
	User findUserByNameAndPwd(String name,String pwd);
	/**
	 * 修改用户密码
	 * @param name
	 * @param newPwd
	 * @return
	 */
	boolean updateUserPwdDao(String name, String newPwd);
	/**
	 * 通过uid查找用户
	 * @param uid
	 * @return
	 */
	User findUserByUidDao(int uid);
	/**
	 * 封装user信息
	 * @param rSet
	 * @return
	 */
	User EncapsulationUser(ResultSet rSet);
}
