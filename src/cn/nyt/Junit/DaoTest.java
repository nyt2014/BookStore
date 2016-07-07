package cn.nyt.Junit;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import cn.nyt.bean.Book;
import cn.nyt.bean.Category;
import cn.nyt.bean.Order;
import cn.nyt.bean.Orderitem;
import cn.nyt.bean.QueryResult;
import cn.nyt.bean.User;
import cn.nyt.dao.BookDao;
import cn.nyt.dao.CategoryDao;
import cn.nyt.dao.OrderDao;
import cn.nyt.dao.UserDao;
import cn.nyt.dao.impl.BookDaoImpl;
import cn.nyt.dao.impl.CategoryDaoImpl;
import cn.nyt.dao.impl.OrderDaoImpl;
import cn.nyt.dao.impl.UserDaoImpl;
import cn.nyt.utils.JdbcUtils_C3P0;

public class DaoTest {

	
	//用户dao层操作相关测试
	UserDao udao=new UserDaoImpl();
	@Test
	public void testUserAdd(){
		User user = new User();
		user.setAddress("北京上城");
		user.setCellphone("13671491313");
		user.setEmail("456@qq.com");
		user.setPassword("1456");
		user.setPhone("7777777");
		user.setUsername("王琪");
		udao.add(user);
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(user);
	}
	@Test
	public void testFindUser(){
		User user = udao.findById("3e0d5de9-ded7-4a04-a74e-15ed5b477b3c");
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(user);
	}
	@Test
	public void testFindUser2(){
		User user1 = udao.find("王琪", "1456");
		User user2 = udao.find("nongyt", "123");
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(user1);
		System.out.println(user2);
	}
	
	/**
	 * 分类相关测试
	 */
	CategoryDao cdao=new CategoryDaoImpl();
	@Test
	public void testAddCategory(){
		for(int i=0;i<5;i++){
			Category c = new Category();
			c.setDescription("javaee高级"+i);
			c.setName("javaee"+i);
			cdao.add(c);
		}
		JdbcUtils_C3P0.commitTransaction();
	}
	@Test
	public void testFindCategory(){
		Category c = cdao.find("034a0a51-78df-493f-8623-afef3d2cbd66");
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(c);
	}
	@Test
	public void testGetAllCategory(){
		List<Category> list = cdao.getAll();
		JdbcUtils_C3P0.commitTransaction();
		for (Category c : list) {
			System.out.println(c);
		}
	}
	
	/**
	 * 书籍相关测试
	 */
	BookDao bdao=new BookDaoImpl();
	@Test
	public void testAddBook(){
		for(int i=0;i<10;i++){
			Book b = new Book();
			b.setAuthor("nyt"+i);
			
			Category c = new Category();
			c.setDescription("javase"+i);
			c.setName("javase"+i);
			cdao.add(c);
			b.setCategory(c);
			
			b.setDescription("java入门基础");
			b.setImage("C:/java/javase"+i+".jpg");
			b.setName("java入门基础"+i);
			b.setPrice(49.8);
			bdao.add(b);
		}
		JdbcUtils_C3P0.commitTransaction();
	}
	@Test
	public void testFindBook(){
		Book book = bdao.findById("16526e98-f279-46b7-845b-c59c5654e78d");
		JdbcUtils_C3P0.commitTransaction();
		System.out.println(book);
	}
	@Test
	public void testGetAllBook(){
		List<Book> list = bdao.getAll();
		JdbcUtils_C3P0.commitTransaction();
		for (Book b : list) {
			System.out.println(b);
		}
	}
	@Test
	public void testPageQuery(){
		QueryResult r1 = bdao.pageQuery(1, 3, null, null);
		int totalrecord = r1.getTotalrecord();
		List<Book> list = r1.getList();
		for (Book b : list) {
			System.out.println(b);
		}
		System.out.println("总记录数："+totalrecord);
	}
	
	/**
	 * 订单相关测试
	 */
	OrderDao odao=new OrderDaoImpl();
	@Test
	public void testAddOrder(){
		//添加10个订单
		for(int i=10;i<20;i++){
			Order o = new Order();
			o.setOrdertime(new Date());
			o.setPrice(250+i);
			o.setState(true);
			
			User user = new User();
			user.setAddress("北京中关村"+i*10);
			user.setCellphone("1367149131"+i*10);
			user.setEmail("45"+i*10+"@qq.com");
			user.setPassword("45"+i*10);
			user.setPhone("777777"+i*10);
			user.setUsername("wangQi"+i*10);
			udao.add(user);
			o.setUser(user);
	
			//每个订单添加3个订单项并保存到set集合当中
			Set<Orderitem> set=new HashSet<Orderitem>();
			for (int j=10+i*10;j<13+i*10;j++) {
			Orderitem oi = new Orderitem();
			Book b = new Book();
			b.setAuthor("nytao"+j);
			
			Category c = new Category();
			c.setDescription("javase+ee"+j);
			c.setName("javase+ee"+j);
			cdao.add(c);
			b.setCategory(c);
			
			b.setDescription("java入门基础");
			b.setImage("C:/java/javaseee"+j+".jpg");
			b.setName("javaee入门基础"+j);
			b.setPrice(49.8);
			bdao.add(b);
			oi.setBook(b);
			
			oi.setPrice(100+j);
			oi.setQuantity(3);
			set.add(oi);
			}
			o.setOrderitems(set);
			odao.add(o);
		}
		//insert into orders(id,ordertime,state,price,user_id) values(1,20150703,false,49,'6dc6d458-ad97-4c67-9308-7d46ba082d12');
		JdbcUtils_C3P0.commitTransaction();
	}
	@Test
	public void testFindOrder(){
		Order order = odao.find("25aa31b6-4e37-41f8-938a-17894a2c7649");
		JdbcUtils_C3P0.commitTransaction();
		order.getId();
		order.getOrdertime();
		order.getPrice();
		order.isState();
		System.out.println(order);
		
		User user = order.getUser();
		System.out.println(user);
		
		Set<Orderitem> set = order.getOrderitems();
		for (Orderitem item : set) {
			System.out.println(item);
			System.out.println("book="+item.getBook());
		}
	}
	@Test
	public void testGetAllOrder(){
		List<Order> list = odao.getAll(false);
		JdbcUtils_C3P0.commitTransaction();
		for (Order o : list) {
			System.out.println(o);
		}
	}
	
	
	@Test
	public void test1(){
		User user = new User();
		System.out.println(user.getId());
	}
	
}
