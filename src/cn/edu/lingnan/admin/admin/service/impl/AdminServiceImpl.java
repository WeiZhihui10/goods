package cn.edu.lingnan.admin.admin.service.impl;

import org.apache.log4j.Logger;

import cn.edu.lingnan.admin.admin.bean.Admin;
import cn.edu.lingnan.admin.admin.dao.AdminDao;
import cn.edu.lingnan.admin.admin.dao.impl.AdminDaoImpl;
import cn.edu.lingnan.admin.admin.service.AdminService;
/**
 * admin模块的业务层的实现类
 * @author Administrator
 *
 */
public class AdminServiceImpl implements AdminService {
	//声明日志对象
	Logger logger=Logger.getLogger(AdminServiceImpl.class);
	//声明AdminDao对象
	AdminDao adminDao=new AdminDaoImpl();
	
	/**
	 * 查询管理员用户
	 * @param adminName
	 * @param adminPwd
	 */
	public Admin findAdminByAdminNameAndAdminPwdDao(String adminName, String adminPwd) {
		return adminDao.findAdminByAdminNameAndAdminPwdDao(adminName, adminPwd);
	}
	
}
