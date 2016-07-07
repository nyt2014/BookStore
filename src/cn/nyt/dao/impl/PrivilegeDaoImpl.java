package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.nyt.bean.Privilege;
import cn.nyt.bean.User;
import cn.nyt.dao.PrivilegeDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class PrivilegeDaoImpl implements PrivilegeDao {

	
	public void add(Privilege p){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="insert into privilege(id,name,description) values(?,?,?)";
			Object[] params={p.getId(),p.getName(),p.getDescription()};
			runner.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Privilege find(String id){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select * from privilege where id=?";
			Privilege p=(Privilege)runner.query(conn, sql, id, new BeanHandler(Privilege.class));
			return p;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Privilege> getAll(User u){
		try {
			Connection conn = JdbcUtils_C3P0.getConnection();
			QueryRunner runner = new QueryRunner();
			String sql="select p.* from privilege_user pu,user u where pu.user_id=? and p.id=pu.privilege_id";
			List<Privilege> list=(List<Privilege>)runner.query(conn, sql, u.getId(),new BeanListHandler(Privilege.class));
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
