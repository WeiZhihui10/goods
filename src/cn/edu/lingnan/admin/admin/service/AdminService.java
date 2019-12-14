package cn.edu.lingnan.admin.admin.service;

import cn.edu.lingnan.admin.admin.bean.Admin;

/**
 * admin模块的业务层
 * @author Administrator
 *
 */

public interface AdminService {
	/**
	 * 查询管理员用户
	 * @param adminName
	 * @param adminPwd
	 */
	Admin findAdminByAdminNameAndAdminPwdDao(String adminName, String adminPwd);
}
