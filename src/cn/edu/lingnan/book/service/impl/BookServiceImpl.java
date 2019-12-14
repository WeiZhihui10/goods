package cn.edu.lingnan.book.service.impl;
/**
 * 图书模块的业务层的实现类
 * @author Administrator
 *
 */

import org.apache.log4j.Logger;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.book.dao.BookDao;
import cn.edu.lingnan.book.dao.impl.BookDaoImpl;
import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.pager.PageBean;

public class BookServiceImpl implements BookService {
	//声明BookDao层对象
	private BookDao bookDao=new BookDaoImpl();
	//创建日志对象
	Logger logger=Logger.getLogger(BookServiceImpl.class);

	/**
	 * 调用Dao层的findBookByCategoryDao(String cid int pc)方法通过分类查找书籍方法
	 * @param cid
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findBookByCategoryService(String cid, int pc) {
		return bookDao.findBookByCategoryDao(cid, pc);
	}

	/**
	 * 调用Dao层的findBookByBnameDao(String bname,int pc)方法通过书名查找书籍
	 * @param bname
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findBookByBnameService(String bname, int pc) {
		return bookDao.findBookByBnameDao(bname, pc);
	}

	/**
	 * 调用Dao层的findBookByAuthorDao(String author,int pc)方法通过作者查询书籍
	 * @param author
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findBookByAuthorService(String author, int pc) {
		return bookDao.findBookByAuthorDao(author, pc);
	}

	/**
	 * 调用Dao层的findBookByPressDao(String press,int pc)方法通过出版社查询书籍
	 * @param press
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findBookByPressService(String press, int pc) {
		return bookDao.findBookByPressDao(press, pc);
	}

	/**
	 * 调用Dao层的findBookByCombinationDao(Book criteria,int pc)方法多条件组合查询书籍
	 * @param criteria
	 * @param pc
	 * @return
	 */
	public PageBean<Book> findBookByCombinationService(Book criteria, int pc) {
		return bookDao.findBookByCombinationDao(criteria, pc);
	}

	/**
	 * 调用Dao层的findBookByBidDao(String bid)方法查询指定书籍
	 * @param bid
	 * @return
	 */
	public Book findBookByBidService(String bid) {
		return bookDao.findBookByBidDao(bid);
	}

	public int findBookCountByCategoryService(String cid) {
		// 返回结果
		return bookDao.findBookCountByCategoryDao(cid);
	}

	/**
	 * 添加图书
	 * @param book
	 */
	public boolean addBookService(Book book) {
		//返回结果
		return bookDao.addBookDao(book);
	}

	/**
	 * 修改图书信息
	 * @param book
	 * @return
	 */
	public boolean updateBookByBidService(Book book) {
		// 返回结果
		return bookDao.updateBookByBidDao(book);
	}

	/**
	 * 删除图书
	 * @param bid
	 * @return
	 */
	public boolean deleteBookService(String bid) {
		// 返回结果
		return bookDao.deleteBookDao(bid);
	}

	/**
	 * 修改图书数量
	 * @param number
	 * @return
	 */
	public boolean updateBookNumberService(int number, String bid) {
		Book book=bookDao.findBookByBidDao(bid);
		number=book.getNumber()+number;
		if(number<0) {
			return false;
		}else {
			// 返回结果
			return bookDao.updateBookNumberDao(number, bid);
		}
		
	}
}
