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
	 * ������صķ���
	 * 
	 **************************************/
	void addCategory(Category c);

	Category findCategory(String id);

	List<Category> getAllCategory();

	/**
	 * ͼ����ط���
	 */
	void addBook(Book book);

	Book findBook(String id);

	List<Book> getAllBook();

	PageBean bookPageQuery(QueryInfo info);

	/**
	 * �û���ط���
	 */
	void addUser(User user);

	User findUser(String username, String password);

	User findUser(String id);

	/**
	 * ������ط���
	 */
	Order findOrder(String id);

	List<Order> getOrdersByState(boolean state);

	void updateOrder(String id, boolean state);

	//���û����ﳵ�����Ķ�������,�����浽����
	void saveOrder(Cart cart, User user);

	/**
	 * ���ݿⱸ����ط���
	 */
	void addBak(DbBak bak);

	List<DbBak> getAllBak();

	DbBak findBak(String id);
	
	public void deleteBak(String id);
	
	/**
	 * �û�����Ȩ����ط���
	 */
	public void addPrivilege(Privilege p);
	
	public Privilege findPrivilege(String id);
	
	public List<Privilege> getUserAllprivilege(User user);
}