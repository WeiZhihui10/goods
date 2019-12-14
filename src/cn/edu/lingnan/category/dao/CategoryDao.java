package cn.edu.lingnan.category.dao;
/**
 * 分类模块的持久层
 * @author 魏智辉
 *
 */

import java.sql.ResultSet;
import java.util.List;

import cn.edu.lingnan.category.bean.Category;

public interface CategoryDao {
	/**
	 * 为了将查询出的pid封装到category的parent 中
	 * @param pid
	 * @return
	 */
	Category toParent(String pid);
	/**
	 * 为了将查询出的二级分类封装到category的children 中
	 * @param pid
	 * @return
	 */
	List<Category> findParentByPid(String pid);
	/**
	 * 查找所有分类
	 * @return
	 */
	List<Category> findAllCategoryDao();
	/**
	 * 添加分类
	 * @param category
	 * @return
	 */
	boolean addCategoryDao(Category category);
	/**
	 * 查询所有一级分类
	 * @return
	 */
	List<Category> findParentCategoryDao();
	/**
	 * 封装Category
	 * @param rSet
	 * @return
	 */
	Category Encapsulation(ResultSet rSet);
	/**
	 * 对于查询操作，遍历结果的时候，需要封装信息到Category对象中，将封装代码提取出来，供每一个查询的操作使用
	 * @param rSet
	 * @return
	 */
	Category EncapsulationCategory(ResultSet rSet);
	/**
	 * 封装Category信息到List<Category>
	 * @param rSet
	 * @return
	 */
	List<Category> EncapsulationCategoryList(ResultSet rSet);
	/**
	 * 修改分类信息的时候，加载需要修改的Category的信息，返回到修改页面显示出来
	 * @param cid
	 * @return
	 */
	Category loadCategoryByCidDao(String cid);
	/**
	 * 修改分类信息
	 * @param category
	 * @return
	 */
	boolean updateCategoryMsgDao(Category category);
	/**
	 * 查询一级分类下是否有二级分类
	 * @param cid
	 * @return
	 */
	int findChildrenCategoryByCidDao(String cid);
	/**
	 * 通过cid删除分类
	 * @param cid
	 * @return
	 */
	boolean deleteCategoryByCidDao(String cid);
}
