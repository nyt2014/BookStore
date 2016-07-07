package cn.nyt.dao;

import java.util.List;

import cn.nyt.bean.DbBak;

public interface DbBakDao {

	void add(DbBak bak);

	List<DbBak> getAll();

	DbBak find(String id);
	
	public void delete(String id);
	

}