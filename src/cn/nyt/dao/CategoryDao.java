package cn.nyt.dao;

import java.util.List;

import cn.nyt.bean.Category;

public interface CategoryDao {

	void add(Category c);

	Category find(String id);

	List<Category> getAll();

}