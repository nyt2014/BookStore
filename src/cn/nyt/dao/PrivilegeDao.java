package cn.nyt.dao;

import java.util.List;

import cn.nyt.bean.Privilege;
import cn.nyt.bean.User;

public interface PrivilegeDao {

	void add(Privilege p);

	Privilege find(String id);

	List<Privilege> getAll(User u);

}