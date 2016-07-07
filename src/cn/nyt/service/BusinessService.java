package cn.nyt.service;

import java.util.List;

import cn.nyt.bean.Book;
import cn.nyt.bean.Cart;
import cn.nyt.bean.Category;
import cn.nyt.bean.DbBak;
import cn.nyt.bean.Order;
import cn.nyt.bean.PageBean;
import cn.nyt.bean.Privilege;
import cn.nyt.bean.QueryInfo;
import cn.nyt.bean.User;

public interface BusinessService {

	/****************************************
	 * 
	 * 分类相关的服务
	 * 
	 **************************************/
	void addCategory(Category c);

	Category findCategory(String id);

	List<Category> getAllCategory();

	/**
	 * 图书相关服务
	 */
	void addBook(Book book);

	Book findBook(String id);

	List<Book> getAllBook();

	PageBean bookPageQuery(QueryInfo info);

	/**
	 * 用户相关服务
	 */
	void addUser(User user);

	User findUser(String username, String password);

	User findUser(String id);

	/**
	 * 订单相关服务
	 */
	Order findOrder(String id);

	List<Order> getOrdersByState(boolean state);

	void updateOrder(String id, boolean state);

	//由用户购物车产生的订单对象,并保存到数据
	void saveOrder(Cart cart, User user);

	/**
	 * 数据库备份相关服务
	 */
	void addBak(DbBak bak);

	List<DbBak> getAllBak();

	DbBak findBak(String id);
	
	public void deleteBak(String id);
	
	/**
	 * 用户访问权限相关服务
	 */
	public void addPrivilege(Privilege p);
	
	public Privilege findPrivilege(String id);
	
	public List<Privilege> getUserAllprivilege(User user);
}