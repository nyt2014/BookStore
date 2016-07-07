package cn.nyt.dao;

import java.util.List;

import cn.nyt.bean.Order;

public interface OrderDao {

	void add(Order o);

	Order find(String id);

	List<Order> getAll(boolean state);

	void update(String id, boolean state);

}