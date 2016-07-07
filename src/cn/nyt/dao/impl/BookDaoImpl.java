package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.nyt.bean.Book;
import cn.nyt.bean.Category;
import cn.nyt.bean.QueryResult;
import cn.nyt.dao.BookDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class BookDaoImpl implements BookDao {

	public void add(Book b){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="insert into book(id,name,price,author,image,description,category_id) values(?,?,?,?,?,?,?)";
			Object[] params={b.getId(),b.getName(),b.getPrice(),b.getAuthor(),b.getImage(),b.getDescription(),b.getCategory().getId()};
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Book findById(String id){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from book where id=?";
			return (Book)runner.query(conn, sql, id, new BeanHandler(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Book> getAll()  {
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from book";
			List<Book> list=(List<Book>)runner.query(conn, sql, new BeanListHandler(Book.class));
			
			//测试代码
			/*for (Book book : list) {
				byte[] bs = book.getImage().getBytes("gb2312");
				String image=new String(bs,"utf-8");
				book.setImage(image);
			}*/
			return list; 
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Book> getPageDate(int startindex,int pagesize,String where,Object param){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql;
			Object[] params;
			if(where==null ||where.trim().equals("")){
				sql="select * from book order by name desc limit ?,?";
				params=new Object[]{startindex,pagesize};
//				return (List<Book>)runner.query(conn, sql, params,new BeanListHandler(Book.class));
			}else{
				//where=where id=?;
				sql="select * from book order by name desc "+where+" limit ?,?";
				params=new Object[]{param,startindex,pagesize};
			}
			
			List<Book> list=(List<Book>)runner.query(conn, sql, params,new BeanListHandler(Book.class));
			
			for (Book book : list) {
				sql="select c.* from book b,category c where b.id=? and c.id=b.category_id";
				Category c=(Category)runner.query(conn, sql, book.getId(), new BeanHandler(Category.class));
				book.setCategory(c);
			}
			
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//记录总的页数
	private int getPageTotalRecord(String where,Object param){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			if(where==null ||where.trim().equals("")){
				String sql="select count(*) from book";
				return ((Long)runner.query(conn, sql, new ScalarHandler())).intValue();
			}else{
				//where=where id=?;
				String sql="select count(*) from book "+where;
				return ((Long)runner.query(conn, sql,param, new ScalarHandler())).intValue();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public QueryResult pageQuery(int startindex,int pagesize,String where,Object param){
		List<Book> list=getPageDate(startindex, pagesize, where, param);
		int totalrecord=getPageTotalRecord(where, param);
		QueryResult result = new QueryResult();
		result.setList(list);
		result.setTotalrecord(totalrecord);
		return result;
	}
}
