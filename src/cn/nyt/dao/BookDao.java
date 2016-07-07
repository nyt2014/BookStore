package cn.nyt.dao;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.nyt.bean.Book;
import cn.nyt.bean.QueryResult;

public interface BookDao {

	void add(Book b);

	Book findById(String id);

	List<Book> getAll() ;

	QueryResult pageQuery(int startindex, int pagesize, String where,
			Object param);

}