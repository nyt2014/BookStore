package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.nyt.bean.User;
import cn.nyt.dao.UserDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class UserDaoImpl implements UserDao {

	public void add(User u){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="insert into user(id,username,password,phone,cellphone,email,address) values(?,?,?,?,?,?,?)";
			Object[] params={u.getId(),u.getUsername(),u.getPassword(),u.getPhone(),u.getCellphone(),u.getEmail(),u.getAddress()};
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User findById(String id){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from user where id=?";
			return (User)runner.query(conn, sql, id, new BeanHandler(User.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User find(String username,String password){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from user where username=? and password=?";
			Object[] params={username,password};
			return (User)runner.query(conn, sql, params, new BeanHandler(User.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*public List<User> getAll(){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from user where";
			return (List<User>)runner.query(conn, sql, new BeanListHandler(User.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}*/
}
