package cn.nyt.bean;

import java.util.List;

public class QueryResult {
	
	private List<Book> list;
	private int totalrecord;
	public List<Book> getList() {
		return list;
	}
	public void setList(List<Book> list) {
		this.list = list;
	}
	public int getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
}
