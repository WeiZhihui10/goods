package cn.edu.lingnan.admin.admin.dao;
/**
 * admin模块的持久层
 * @author 魏智辉
 *
 */

import java.sql.ResultSet;

import cn.edu.lingnan.admin.admin.bean.Admin;

public interface AdminDao {
	
	
	
	/**
	 * 查询管理员
	 * @param adminName
	 * @param adminPwd
	 * @return
	 */
	Admin findAdminByAdminNameAndAdminPwdDao(String adminName,String adminPwd);
	
	/**
	 * 封装admin信息
	 * @param rSet
	 * @return
	 */
	Admin EncapsulationAdmin(ResultSet rSet);
}
