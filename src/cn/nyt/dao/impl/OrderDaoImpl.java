package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.nyt.bean.Book;
import cn.nyt.bean.Category;
import cn.nyt.bean.Order;
import cn.nyt.bean.Orderitem;
import cn.nyt.bean.User;
import cn.nyt.dao.OrderDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class OrderDaoImpl implements OrderDao {

	public void add(Order o){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			
			String sql = "insert into orders(id,ordertime,state,price,user_id) values(?,?,?,?,?)";
			Object params[] = {o.getId(),o.getOrdertime(),o.isState(),o.getPrice(),o.getUser().getId()};
			runner.update(conn,sql, params);
			
			//将该订单的Set集合当中的订单项也存入到数据库的orderitem表中
			Set<Orderitem> set=o.getOrderitems();
			for (Orderitem item : set) {
				sql = "insert into orderitem(id,quantity,price,book_id,orders_id) values(?,?,?,?,?)";
				params=new Object[]{item.getId(),item.getQuantity(),item.getPrice(),item.getBook().getId(),o.getId()};
				runner.update(conn, sql, params);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public Order find(String id){
		try {
			//找出订单信息
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql = "select * from orders where id =?";
			Order order=(Order)runner.query(conn, sql, id, new BeanHandler(Order.class));

			//找出所有订单项信息
			
			sql = "select * from orderitem where orders_id=?";
			List<Orderitem> items=(List<Orderitem>)runner.query(conn, sql, id, new BeanListHandler(Orderitem.class));

			//找出每一个订单项对应的书
			for (Orderitem item : items) {
//				System.out.println("orderitemI="+item.getId());//测试
				sql="select b.* from orderitem oi,book b where oi.id=? and b.id=oi.book_id";
				Book book=(Book)runner.query(conn, sql, item.getId(), new BeanHandler(Book.class));
				item.setBook(book);
				
//				System.out.println(item.getBook().getId());//测试
				
				//找出每本书所属的分类
				sql="select c.* from book b,category c where b.id=? and c.id=b.category_id ";
				Category c=(Category)runner.query(conn, sql, item.getBook().getId(), new BeanHandler(Category.class));
				
//				System.out.println("category"+c);//测试
				item.getBook().setCategory(c);
				
			}
			//将找出了相应书本的订单项的集合全部添加到该订单的Set集合当中
			order.getOrderitems().addAll(items);
			
			//找出该订单的下单人
			sql="select u.* from orders o,user u where o.id=? and u.id=o.user_id";
			User user=(User)runner.query(conn, sql, id, new BeanHandler(User.class));
			order.setUser(user);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Order> getAll(boolean state){
	try {
		Connection conn = JdbcUtils_C3P0.getConnection();
		QueryRunner runner = new QueryRunner();
		String sql="select * from orders where state=?";
		List<Order> list=(List<Order>)runner.query(conn, sql, state, new BeanListHandler(Order.class));
		
		for (Order order : list) {
			sql="select u.* from orders o,user u where o.id=? and u.id=o.user_id";
			User user=(User)runner.query(conn, sql, order.getId(), new BeanHandler(User.class));
			order.setUser(user);
			
			/*HashSet<Orderitem> set = new HashSet<Orderitem>();
			sql="select * from orderitem where orders_id=?";
			List<Orderitem> items=(List<Orderitem>) runner.query(conn, sql, order.getId(), new BeanListHandler(Orderitem.class));
			order.getOrderitems().addAll(items);*/
		}
		
		 return list;
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
	}
	
	public void update(String id,boolean state){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="update orders set state=? where id=?";
			Object[] params={state,id};
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}

