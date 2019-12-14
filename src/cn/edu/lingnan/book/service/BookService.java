package cn.edu.lingnan.book.service;
/**
 * 图书模块的业务层
 * @author Administrator
 *
 */

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.pager.PageBean;

public interface BookService {
	/**
	 * 调用Dao层的findBookByCategoryDao(String cid int pc)方法通过分类查找书籍方法
	 * @param cid
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByCategoryService(String cid,int pc);
	/**
	 * 调用Dao层的findBookByBnameDao(String bname,int pc)方法通过书名查找书籍
	 * @param bname
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByBnameService(String bname,int pc);
	/**
	 * 调用Dao层的findBookByAuthorDao(String author,int pc)方法通过作者查询书籍
	 * @param author
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByAuthorService(String author,int pc);
	/**
	 * 调用Dao层的findBookByPressDao(String press,int pc)方法通过出版社查询书籍
	 * @param press
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByPressService(String press,int pc);
	/**
	 * 调用Dao层的findBookByCombinationDao(Book criteria,int pc)方法多条件组合查询书籍
	 * @param criteria
	 * @param pc
	 * @return
	 */
	PageBean<Book> findBookByCombinationService(Book criteria,int pc);
	/**
	 * 调用Dao层的findBookByBidDao(String bid)方法查询指定书籍
	 * @param bid
	 * @return
	 */
	Book findBookByBidService(String bid);
	/**
	 * 查询指定分类下的图书的个数
	 * @param cid
	 * @return
	 */
	int findBookCountByCategoryService(String cid);
	/**
	 * 添加图书
	 * @param book
	 */
	boolean addBookService(Book book);
	/**
	 * 修改图书信息
	 * @param book
	 * @return
	 */
	boolean updateBookByBidService(Book book);
	/**
	 * 删除图书
	 * @param bid
	 * @return
	 */
	boolean deleteBookService(String bid);
	/**
	 * 修改图书数量
	 * @param number
	 * @return
	 */
	boolean updateBookNumberService(int number,String bid);
}
