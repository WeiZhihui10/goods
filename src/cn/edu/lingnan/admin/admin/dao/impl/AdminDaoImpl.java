package cn.edu.lingnan.admin.admin.dao.impl;

import java.sql.ResultSet;

import cn.edu.lingnan.admin.admin.bean.Admin;
import cn.edu.lingnan.admin.admin.dao.AdminDao;
import cn.edu.lingnan.util.JDBC;
/**
 * admin模块持久层的实现类
 * @author 魏智辉
 *
 */
public class AdminDaoImpl implements AdminDao {

	/**
	 * 查询管理员
	 * @param adminName
	 * @param adminPwd
	 * @return
	 */
	public Admin findAdminByAdminNameAndAdminPwdDao(String adminName, String adminPwd) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_admin where adminname=? and adminpwd=?";
		//给占位符赋值
		Object[] params= {adminName,adminPwd};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//声明Admin对象
		Admin admin=EncapsulationAdmin(rSet);
		return admin;
	}

	/**
	 * 封装admin信息
	 * @param rSet
	 * @return
	 */
	public Admin EncapsulationAdmin(ResultSet rSet) {
		Admin admin=null;
		try {
			//遍历结果
			if(rSet.next()) {
				admin=new Admin();
				admin.setAdminId(rSet.getString("adminId"));
				admin.setAdminName(rSet.getString("adminname"));
				admin.setAdminPwd(rSet.getString("adminpwd"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}	
		return admin;
	}
	
	
}
