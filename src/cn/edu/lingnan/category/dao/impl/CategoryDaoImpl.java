package cn.edu.lingnan.category.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.dao.CategoryDao;
import cn.edu.lingnan.util.JDBC;

/**
 * 分类模块的持久层的实现类
 * @author 魏智辉
 *
 */
public class CategoryDaoImpl implements  CategoryDao{
	
	/**
	 * 为了将查询出的pid封装到category的parent 中
	 * @param pid
	 * @return
	 */
	public Category toParent(String pid) {
		Category parent=null;
		if(pid!=null) {
			parent=new Category();
			parent.setCid(pid);
		}
		return parent;
	}
	
	/**
	 * 为了将查询出的二级分类封装到category的children 中
	 * @param pid
	 * @return
	 */
	public List<Category> findParentByPid(String pid){
		//声明变量
		List<Category> children=new ArrayList<Category>();
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_category where pid=?";
		//给占位符赋值
		Object[] params= {pid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果
		children=EncapsulationCategoryList(rSet);
		return children;
	}
	
	/**
	 * 查找所有分类
	 * @return
	 */
	public List<Category> findAllCategoryDao() {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_category where pid is null order by orderBy";
		//执行sql语句
		rSet=JDBC.executeQuery(sql, null);
		//遍历结果
		List<Category> categories=EncapsulationCategoryList(rSet);
		return categories;
	}

	/**
	 * 添加分类
	 * @param category
	 * @return
	 */
	public boolean addCategoryDao(Category category) {
		//创建sql语句
		String sql="insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		//给占位股赋值
		String pid=null;
		if(category.getParent()!=null) {
			pid=category.getParent().getCid();
		}
		Object[] params= {category.getCid(),category.getCname(),pid,category.getDesc()};
		//执行sql语句
		boolean flag=JDBC.executeUpdate(sql, params);
		return flag;
	}

	/**
	 * 查询所有一级分类
	 * @return
	 */
	public List<Category> findParentCategoryDao() {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_category where pid is null order by orderBy";
		//执行sql语句
		rSet=JDBC.executeQuery(sql, null);
		List<Category> categories=EncapsulationCategoryList(rSet);
		return categories;
	}

	/**
	 * 封装Category
	 * @param rSet
	 * @return
	 */
	public Category Encapsulation(ResultSet rSet) {
		//声明变量
		Category category=new Category();
		try {
			category.setCid(rSet.getString("cid"));
			category.setCname(rSet.getString("cname"));
			category.setParent(toParent(rSet.getString("pid")));
			category.setDesc(rSet.getString("desc"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}

	/**
	 * 对于查询操作，遍历结果的时候，需要封装信息到Category对象中，将封装代码提取出来，供每一个查询的操作使用
	 * @param rSet
	 * @return
	 */
	public Category EncapsulationCategory(ResultSet rSet) {
		//声明变量
		Category category=null;
		try {
			//遍历结果
			if(rSet.next()) {
				category=Encapsulation(rSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		//返回结果
		return category;
	}

	/**
	 * 封装Category信息到List<Category>
	 * @param rSet
	 * @return
	 */
	public List<Category> EncapsulationCategoryList(ResultSet rSet) {
		//声明变量
		List<Category> categories=new ArrayList<Category>();
		Category category=null;
		try {
			while(rSet.next()) {
				category=Encapsulation(rSet);
				category.setChildren(findParentByPid(rSet.getString("cid")));
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return categories;
	}

	/**
	 * 加载分类信息
	 * @param cid
	 * @return
	 */
	public Category loadCategoryByCidDao(String cid) {
		//创建jdbc对象
		ResultSet rSet=null;
		//创建sql语句
		String sql="select * from t_category where cid=?";
		//给占位符赋值
		Object[] params= {cid};
		//执行sql语句
		rSet=JDBC.executeQuery(sql, params);
		//遍历结果
		Category category=EncapsulationCategory(rSet);
		return category;
	}

	/**
	 * 修改分类信息,既可以修改一级分类也可以修改二级分类
	 * @param category
	 * @return
	 */
	public boolean updateCategoryMsgDao(Category category) {
		//创建sql语句
		String sql="update t_category set cname=?,pid=?,`desc`=? where cid=?";
		//给占位符赋值
		String pid=null;
		if(category.getParent()!=null) {
			pid=category.getParent().getCid();
		}
		Object[] params= {category.getCname(),pid,category.getDesc(),category.getCid()};
		//执行sql语句
		return JDBC.executeUpdate(sql, params);
	}

	/**
	 * 查询一级分类下是否有二级分类
	 * @param cid
	 * @return
	 */
	public int findChildrenCategoryByCidDao(String cid) {
		// 创建jdbc对象
		ResultSet rSet=null;
		//声明变量
		int index=0;
		//创建sql语句
		String sql="select count(*) from t_category where pid=?";
		//给占位符赋值
		Object[] params= {cid};
		try {
			//执行sql语句
			rSet=JDBC.executeQuery(sql, params);
			if(rSet.next()) {
				index=rSet.getInt("count(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBC.closeAll(rSet, JDBC.ps, JDBC.conn);
		}
		return index;
	}

	/**
	 * 通过cid删除分类
	 * @param cid
	 * @return
	 */
	public boolean deleteCategoryByCidDao(String cid) {
		// 创建sql语句
		String sql="delete from t_category where cid=?";
		//给占位符赋值
		Object[] params= {cid};
		//执行sql语句
		boolean flag=JDBC.executeUpdate(sql, params);
		return flag;
	}

}
