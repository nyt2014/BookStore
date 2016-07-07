package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.nyt.bean.Category;
import cn.nyt.dao.CategoryDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class CategoryDaoImpl implements CategoryDao {
	
	public void add(Category c){
		try {
			//从当前线程上获取一个开启事务的链接
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="insert into category(id,name,description) values(?,?,?)";
			Object[] params ={c.getId(),c.getName(),c.getDescription()};
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Category find(String id){
		try {
			//从当前线程上获取一个开启事务的链接
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from category where id=?";
			Category c=(Category) runner.query(conn, sql, id, new BeanHandler(Category.class));
			return c;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Category> getAll(){
		try {
			//从当前线程上获取一个开启事务的链接
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from category";
			List<Category> list=(List<Category>) runner.query(conn, sql, new BeanListHandler(Category.class));
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
