package cn.edu.lingnan.category.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.dao.CategoryDao;
import cn.edu.lingnan.category.dao.impl.CategoryDaoImpl;
import cn.edu.lingnan.category.service.CategoryService;

/**
 * 分类模块的业务层的实现类
 * @author Administrator
 *
 */
public class CategoryServiceImpl implements CategoryService {
	//声明dao层对象
	private CategoryDao caDao=new CategoryDaoImpl();
	//声明日志对象
	Logger logger=Logger.getLogger(CategoryServiceImpl.class);

	/**
	 * 查找所有分类
	 * @return
	 */
	public List<Category> findAllCategoryService() {
		//返回结果
		return caDao.findAllCategoryDao();
	}

	/**
	 * 添加分类
	 * @param category
	 * @return
	 */
	public boolean addCategoryService(Category category) {
		//返回结果
		return caDao.addCategoryDao(category);
	}

	/**
	 * 查询所有一级分类
	 * @return
	 */
	public List<Category> findParentCategoryService() {
		//返回结果
		return caDao.findParentCategoryDao();
	}
	
	/**
	 * 加载Category
	 * @param cid
	 * @return
	 */
	public Category loadCategoryByCidService(String cid) {
		// 返回结果
		return caDao.loadCategoryByCidDao(cid);
	}

	/**
	 * 修改分类信息
	 * @param category
	 * @return
	 */
	public boolean updateCategoryMsgService(Category category) {
		// 返回结果
		return caDao.updateCategoryMsgDao(category);
	}

	/**
	 * 查询一级分类下是否有二级分类
	 * @param cid
	 * @return
	 */
	public int findChildrenCategoryByCidService(String cid) {
		// 返回结果
		return caDao.findChildrenCategoryByCidDao(cid);
	}

	/**
	 * 通过cid删除分类
	 * @param cid
	 * @return
	 */
	public boolean deleteCategoryByCidService(String cid) {
		// 返回结果
		return caDao.deleteCategoryByCidDao(cid);
	}

	public List<Category> findParentByPidService(String pid) {
		// 返回结果
		return caDao.findParentByPid(pid);
	}
}
