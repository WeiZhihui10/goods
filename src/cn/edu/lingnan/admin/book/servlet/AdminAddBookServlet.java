package cn.edu.lingnan.admin.book.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.lingnan.book.bean.Book;
import cn.edu.lingnan.book.service.BookService;
import cn.edu.lingnan.book.service.impl.BookServiceImpl;
import cn.edu.lingnan.category.bean.Category;
import cn.edu.lingnan.category.service.CategoryService;
import cn.edu.lingnan.category.service.impl.CategoryServiceImpl;
import cn.edu.lingnan.util.IDGenerator;
import cn.itcast.commons.CommonUtils;


public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/**
		 * commons-fileupload的上传三步
		 */
		//1.创建工厂
		FileItemFactory factory=new DiskFileItemFactory();
		//2.创建解析器对象
		ServletFileUpload sFileUpload=new ServletFileUpload(factory);
		sFileUpload.setFileSizeMax(80*1024);//设置单个上传的文件上限为80kb
		//3.解析request得到List<FileItem>
		List<FileItem> fileItemList=null;
		try {
			fileItemList=sFileUpload.parseRequest(req);
		} catch (FileUploadException e) {
			error("上传的文件超过80kb", req, resp);
			return;
		}
		/**
		 * 4.把List<FileItem>封装到Book对象中
		 * 	4.1 首先把普通表单字段放到一个Map中，再把Map转换成Book和Category对象，再建立两者的关系
		 */
		Map<String, Object> map=new HashMap<String, Object>();
		for(FileItem fileItem:fileItemList) {
			if(fileItem.isFormField()) {
				map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
			}
		}
		Book book=CommonUtils.toBean(map, Book.class);
		Category category=CommonUtils.toBean(map, Category.class);//把map中的cid封装到category中
		book.setCategory(category);
		/**
		 * 4.2把上传的图片保存起来
		 * 	>获取文件名
		 *  >给文件添加前缀：使用uuid前缀，为了避免文件同名现象
		 *  >校验文件拓展名：只能是jpg
		 *  >校验图片的尺寸
		 *  >指定图片的保存路径，这需要使用ServletContext#getRealPath()
		 *  >保存之
		 *  >把图片的路径设置给Book对象
		 */
		//获取文件名
		FileItem fileItem=fileItemList.get(1);//获取大图
		String filename=fileItem.getName();
		//截取文件名，因为有些浏览器上传的绝对路径
		int index=filename.lastIndexOf("\\");
		if(index!=-1) {
			filename=filename.substring(index+1);
		}
		filename=CommonUtils.uuid()+"-"+filename;
		//校验文件名的扩展名
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的文件必须是jpg", req, resp);
			return;
		}
		//校验图片的尺寸，保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
		
		/**
		 * 保存图片：
		 * 	1.获取真实路径
		 */
		String savePath=this.getServletContext().getRealPath("/book_img");
		//2.创建目标文件
		File destFile=new File(savePath,filename);
		//3.保存文件
		try {
			fileItem.write(destFile);//他会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		/**
		 * 校验尺寸:
		 * 	1.使用文件路径创建ImageIcon,
		 * 	2.通过ImageIcon得到Image对象
		 * 	3.获取宽高来进行校验
		 */
		ImageIcon icon=new ImageIcon(destFile.getAbsolutePath());
		Image image=icon.getImage();
		if(image.getWidth(null)>350||image.getHeight(null)>350) {
			error("您上传的图片超过350*350", req, resp);
			destFile.delete();
			return;
		}
		
		//把图片的路径设置给Book对象
		book.setImage_w("book_img/"+filename);
		
		
		
		//获取文件名
		fileItem=fileItemList.get(2);//获取小图
		filename=fileItem.getName();
		//截取文件名，因为有些浏览器上传的绝对路径
		index=filename.lastIndexOf("\\");
		if(index!=-1) {
			filename=filename.substring(index+1);
		}
		filename=CommonUtils.uuid()+"-"+filename;
		//校验文件名的扩展名
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的文件必须是jpg", req, resp);
			return;
		}
		//校验图片的尺寸，保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
		
		/**
		 * 保存图片：
		 * 	1.获取真实路径
		 */
		savePath=this.getServletContext().getRealPath("/book_img");
		//2.创建目标文件
		destFile=new File(savePath,filename);
		//3.保存文件
		try {
			fileItem.write(destFile);//他会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 校验尺寸:
		 * 	1.使用文件路径创建ImageIcon,
		 * 	2.通过ImageIcon得到Image对象
		 * 	3.获取宽高来进行校验
		 */
		icon=new ImageIcon(destFile.getAbsolutePath());
		image=icon.getImage();
		if(image.getWidth(null)>350||image.getHeight(null)>350) {
			error("您上传的图片超过350*350", req, resp);
			destFile.delete();
			return;
		}
		
		//把图片的路径设置给Book对象
		book.setImage_b("book_img/"+filename);
		
		
		
		
		//调用service保存
		BookService bookService=new BookServiceImpl();
		book.setBid(IDGenerator.getUUID());
		bookService.addBookService(book);
		
		//响应处理结果
		req.setAttribute("msg", "添加成功");
		req.getRequestDispatcher("/adminjsps/admin/book/body.jsp").forward(req, resp);
		
	}
	/**
	 * 保存错误信息，转发到add.jsp
	 * @param msg
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void error(String msg,HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		CategoryService categoryService=new CategoryServiceImpl();
		req.setAttribute("msg", msg);
		req.setAttribute("parents",categoryService.findAllCategoryService());
		req.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(req, resp);
	}

}
