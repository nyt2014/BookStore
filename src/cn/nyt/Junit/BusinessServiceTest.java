package cn.nyt.Junit;

import org.junit.Test;

import cn.nyt.bean.Book;
import cn.nyt.bean.Cart;
import cn.nyt.bean.Category;
import cn.nyt.bean.PageBean;
import cn.nyt.bean.QueryInfo;
import cn.nyt.bean.User;
import cn.nyt.dao.BookDao;
import cn.nyt.dao.UserDao;
import cn.nyt.dao.impl.BookDaoImpl;
import cn.nyt.dao.impl.CategoryDaoImpl;
import cn.nyt.dao.impl.UserDaoImpl;
import cn.nyt.service.BusinessService;
import cn.nyt.service.impl.BusinessServiceImpl;
import cn.nyt.utils.JdbcUtils_C3P0;

public class BusinessServiceTest {

	/**
	 * 图书相关
	 */
	private BusinessService bs=new BusinessServiceImpl();
	@Test
	public void testBookPageQuery(){
		QueryInfo info = new QueryInfo();
		info.setCurrentpage(1);
		info.setPagesize(3);
		/*info.setQueryname(queryname);
		info.setQueryvalue(queryvalue);*/
		PageBean bean = bs.bookPageQuery(info);
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(bean);
	}
	
	/**
	 * 订单相关测试
	 */
	@Test
	public void testSaveOrder(){
		
		UserDao udao = new UserDaoImpl();
		User user = new User();
		user.setAddress("天津");
		user.setCellphone("13671491966");
		user.setEmail("456@163.com");
		user.setPassword("666");
		user.setPhone("6666666");
		user.setUsername("王琪2");
		udao.add(user);
		
		
		Cart cart = new Cart();
		Book book = new Book();
		book.setAuthor("老张");
		Category c = new Category();
		c.setDescription("javaweb");
		c.setName("javaweb");
		CategoryDaoImpl cdao = new CategoryDaoImpl();
		cdao.add(c);
		
		book.setCategory(c);
		book.setDescription("sdfsfd");
		book.setImage("323");
		book.setName("javaweb开发");
		book.setPrice(89);
		BookDao bdao = new BookDaoImpl();
		bdao.add(book);
		cart.addBook(book);
		
		bs.saveOrder(cart, user);
		JdbcUtils_C3P0.commitTransaction();
	}
	
}
