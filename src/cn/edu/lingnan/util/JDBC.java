package cn.edu.lingnan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBC {
	
	//声明JDBC对象
	public static Connection conn=null;
	public static PreparedStatement ps=null;
	public static ResultSet rs=null;
	
	/**
	 * mysql通用的增删改
	 * @param sql sql语句
	 * @param params sql语句中的?参数数组
	 * @return 返回一个boolean值
	 */
	public static boolean executeUpdate(String sql,Object[] params) {
		//声明变量
		boolean flag=false;
		try {
			//创建sql命令对象,给占位符赋值
			ps=getPreparedStatement(sql,params);
			//执行sql
			int index=ps.executeUpdate();
			if(index>0) {
				flag=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(null, ps, conn);
		}
		return flag;
	}
	
	/**
	 * mysql通用的的查询
	 * @param sql 查询的sql语句
	 * @param params sql语句中的参数
	 * @return 返回查询结果
	 */
	
	public static ResultSet executeQuery(String sql, Object[] params) {
		try {
			//创建sql命令对象,给占位符赋值
			ps=getPreparedStatement(sql, params);
			//执行sql
			rs=ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回结果
		return rs;
	}
	
	/**
	 * 加载驱动 获取连接
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		//加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		//获取连接
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/goods","root","root");
		return conn;
	}
	
	/**
	 * 关闭资源
	 * @param rs 结果集
	 * @param state sql命令对象
	 * @param conn 链接对象
	 */
	public static void closeAll(ResultSet rs,Statement state,Connection conn) {
		try {
			if(rs!=null)rs.close();
			if(state!=null)state.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建sql命令对象，给占位符赋值
	 * @param sql sql语句
	 * @param params 数值数组
	 * @return 返回ps对象
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static PreparedStatement getPreparedStatement(String sql,Object[] params)
			throws SQLException, ClassNotFoundException {
		//创建sql命令对象
		ps=getConnection().prepareStatement(sql);
		//给占位符赋值ps.setString(1, newPwd);
		if(params!=null) {
			for(int i=0;i<params.length;i++) {
				ps.setObject(i+1, params[i]);
			}
		}
		return ps;		
	}
	
	/**
	 * 查询数据总数
	 * @param sql sql语句
	 * @return 返回数据总数
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int getTotalCount(String sql) {
		int count=-1;
		try {
			ps=getPreparedStatement(sql,null);
			rs=ps.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			closeAll(rs, ps, conn);
		}
		return count;
	}
}
