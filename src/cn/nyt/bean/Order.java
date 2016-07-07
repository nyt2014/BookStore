package cn.nyt.bean;

import java.util.Date;
import cn.nyt.bean.User;
import java.util.HashSet;
import java.util.Set;

public class Order extends PutUUID {
	private Date ordertime;//下单时间
	private boolean state;//订单状态
	private double price;//订单总价
	private User user;//记住下单人
	private Set<Orderitem> orderitems=new HashSet<Orderitem>();//记住订单所有的订单项
	
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Orderitem> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(Set<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}
	@Override
	public String toString() {
		return "Order [ordertime=" + ordertime + ", state=" + state
				+ ", price=" + price + ", user=" + user + ", orderitems="
				+ orderitems + ", getId()=" + getId() + "]";
	}
	
}
