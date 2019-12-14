package cn.edu.lingnan.book.dao;
/**
 * 图书模块的持久层
 * @author Administrator
 *
 */

import java.sql.ResultSet;
import java.util.List;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.pager.Expression;
import cn.edu.lingnan.pager.PageBean;

public interface BookDao {
	/**
	 * 通用的查询方法
	 * @param expressions
	 * @param pc
	 * @return
	 */
	PageBean<Book> findByCriteriaDao(List<Expression> expressions,int pc);
	/**
	 * 通过分类查找书籍
	 * @param cid
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByCategoryDao(String cid,int pc);
	/**
	 * 通过书名查找书籍
	 * @param bname
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByBnameDao(String bname,int pc);
	/**
	 * 通过作者查询书籍
	 * @param author
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByAuthorDao(String author,int pc);
	/**
	 * 通过出版社查询书籍
	 * @param press
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByPressDao(String press,int pc);
	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByCombinationDao(Book criteria,int pc);
	/**
	 * 通过书籍id查询指定书籍
	 * @param bid
	 * @return
	 */
	Book findBookByBidDao(String bid);
	/**
	 * 封装书籍信息
	 * @param rSet
	 * @return
	 */
	Book Encapsulation(ResultSet rSet);
	/**
	 * 封装书籍信息到Book对象中
	 * @param rSet
	 * @return
	 */
	Book EncapsulationBook(ResultSet rSet);
	/**
	 * 封装书籍信息到List<Book>对象中
	 * @param rSet
	 * @return
	 */
	List<Book> EncapsulationBookList(ResultSet rSet);
	/**
	 * 查询指定分类下的图书的个数
	 * @param cid
	 * @return
	 */
	int findBookCountByCategoryDao(String cid);
	/**
	 * 添加图书
	 * @param book
	 * @return
	 */
	boolean addBookDao(Book book);
	/**
	 * 修改图书信息
	 * @param book
	 * @return
	 */
	boolean updateBookByBidDao(Book book);
	/**
	 * 删除图书
	 * @param bid
	 * @return
	 */
	boolean deleteBookDao(String bid);
	/**
	 * 修改图书数量
	 * @param number
	 * @return
	 */
	boolean updateBookNumberDao(int number,String bid);
}
