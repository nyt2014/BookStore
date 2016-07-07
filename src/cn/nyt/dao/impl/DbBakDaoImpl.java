package cn.nyt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.nyt.bean.DbBak;
import cn.nyt.dao.DbBakDao;
import cn.nyt.utils.JdbcUtils_C3P0;

public class DbBakDaoImpl implements DbBakDao {

	public void add(DbBak bak){
		try {
			
			QueryRunner runner = new QueryRunner(JdbcUtils_C3P0.getDataSource_dbbak());
			String sql="insert into dbbak(id,filename,baktime,description) values(?,?,?,?)";
			Object[] params={bak.getId(),bak.getFilename(),bak.getBaktime(),bak.getDescription()};
			runner.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<DbBak> getAll(){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils_C3P0.getDataSource_dbbak());
			String sql="select * from dbbak order by baktime desc";
			return (List<DbBak>)runner.query(sql, new BeanListHandler(DbBak.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DbBak find(String id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils_C3P0.getDataSource_dbbak());
			String sql="select * from dbbak where id=?";
			return (DbBak)runner.query(sql, id, new BeanHandler(DbBak.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(String id){
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils_C3P0.getDataSource_dbbak());
			String sql="delete from dbbak where id=?";
			runner.update(sql, id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
