package cn.nyt.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.nyt.bean.Book;
import cn.nyt.bean.Cart;
import cn.nyt.bean.CartItem;
import cn.nyt.bean.Category;
import cn.nyt.bean.DbBak;
import cn.nyt.bean.Order;
import cn.nyt.bean.Orderitem;
import cn.nyt.bean.PageBean;
import cn.nyt.bean.Privilege;
import cn.nyt.bean.QueryInfo;
import cn.nyt.bean.QueryResult;
import cn.nyt.bean.User;
import cn.nyt.dao.BookDao;
import cn.nyt.dao.CategoryDao;
import cn.nyt.dao.DbBakDao;
import cn.nyt.dao.OrderDao;
import cn.nyt.dao.PrivilegeDao;
import cn.nyt.dao.UserDao;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.utils.Permission;

public class BusinessServiceImpl implements BusinessService {

	private CategoryDao cdao=DaoFactory.getInstance().createInstance(CategoryDao.class);
	private BookDao bdao=DaoFactory.getInstance().createInstance(BookDao.class);
	private OrderDao odao=DaoFactory.getInstance().createInstance(OrderDao.class);
	private UserDao udao=DaoFactory.getInstance().createInstance(UserDao.class);
	private DbBakDao ddao=DaoFactory.getInstance().createInstance(DbBakDao.class);
	private PrivilegeDao pdao=DaoFactory.getInstance().createInstance(PrivilegeDao.class);
	
	/****************************************
	 * 
	 * ������صķ���
	 * 
	 **************************************/
	@Permission("��ӷ���")
	public void addCategory(Category c){
		cdao.add(c);
	}
	
	public Category findCategory(String id){
		return cdao.find(id);
	}
	
	public List<Category> getAllCategory(){
		return cdao.getAll();
	}
	
	
	/**
	 * ͼ����ط���
	 */
	public void addBook(Book book){
		bdao.add(book);
	}
	public Book findBook(String id){
		return bdao.findById(id);
	}
	public List<Book> getAllBook(){
		return bdao.getAll();
	}
	public PageBean bookPageQuery(QueryInfo info){
		
		QueryResult result = bdao.pageQuery(info.getStartindex(), info.getPagesize(), info.getWhere(), info.getQueryvalue());
		
		PageBean bean = new PageBean();
		bean.setCurrentpage(info.getCurrentpage());
		bean.setList(result.getList());
		bean.setPagesize(info.getPagesize());
		bean.setTotalrecord(result.getTotalrecord());
		return bean;
	}
	
	/**
	 * �û���ط���
	 */
	public void addUser(User user){
		udao.add(user);
	}
	
	public User findUser(String username,String password){
		return udao.find(username, password);
		
	}
	
	public User findUser(String id){
		return udao.findById(id);
	}
	
	/**
	 * ������ط���
	 */
	public Order findOrder(String id){
		return odao.find(id);
	}
	
	public List<Order> getOrdersByState(boolean state){
		return odao.getAll(state);
	}
	
	public void updateOrder(String id,boolean state){
		odao.update(id, state);
	}
	
	//���û����ﳵ�����Ķ�������,�����浽����
	public void saveOrder(Cart cart ,User user){
		Order order = new Order();
		order.setOrdertime(new Date());
		order.setPrice(cart.getPrice());
		order.setState(false);
		order.setUser(user);
		
		//���弯���û��������еĶ�����
		Set<Orderitem> ois=new HashSet<Orderitem>();
		
		//�ù��ﳵ�еĹ��������ɶ�����
		Set<Map.Entry<String, CartItem>> set=cart.getMap().entrySet();
		for (Map.Entry<String, CartItem> entry : set) {
			//��ȡ������
			CartItem citem = entry.getValue();
			Orderitem oitem = new Orderitem();
			
			oitem.setBook(citem.getBook());
			oitem.setPrice(citem.getPrice());
			oitem.setQuantity(citem.getQuantity());
			
			ois.add(oitem);
		}
		//���浽����
		order.setOrderitems(ois);
		//�������ݿ�
		odao.add(order);
	}
	
	/**
	 * ���ݿⱸ����ط���
	 */
	public void addBak(DbBak bak){
		ddao.add(bak);
	}
	
	public List<DbBak> getAllBak(){
		return ddao.getAll();
	}
	
	public DbBak findBak(String id){
		return ddao.find(id);
	}
	
	public void deleteBak(String id){
		ddao.delete(id);
	}
	
	/**
	 * �û�����Ȩ����ط���
	 */
	public void addPrivilege(Privilege p){
		pdao.add(p);
	}
	
	public Privilege findPrivilege(String id){
		return pdao.find(id);
	}
	public List<Privilege> getUserAllprivilege(User user){
		
		return pdao.getAll(user);
	}
}
