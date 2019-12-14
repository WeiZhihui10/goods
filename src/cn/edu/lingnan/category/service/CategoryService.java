package cn.edu.lingnan.category.service;
/**
 * 分类模块的业务层
 * @author Administrator
 *
 */

import java.util.List;

import cn.edu.lingnan.category.bean.Category;

public interface CategoryService {
	/**
	 * 查找所有分类
	 * @return
	 */
	List<Category> findAllCategoryService();
	/**
	 * 添加分类
	 * @param category
	 * @return
	 */
	boolean addCategoryService(Category category);
	/**
	 * 查询所有一级分类
	 * @return
	 */
	List<Category> findParentCategoryService();
	/**
	 * 加载Category
	 * @param cid
	 * @return
	 */
	Category loadCategoryByCidService(String cid);
	/**
	 * 修改分类信息
	 * @param category
	 * @return
	 */
	boolean updateCategoryMsgService(Category category);
	/**
	 * 查询一级分类下是否有二级分类
	 * @param cid
	 * @return
	 */
	int findChildrenCategoryByCidService(String cid);
	/**
	 * 通过cid删除分类
	 * @param cid
	 * @return
	 */
	boolean deleteCategoryByCidService(String cid);
	/**
	 * 查询一级分类下的二级分类
	 * @param pid
	 * @return
	 */
	List<Category> findParentByPidService(String pid);
}
