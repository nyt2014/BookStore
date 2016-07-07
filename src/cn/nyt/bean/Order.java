package cn.nyt.bean;

import java.util.Date;
import cn.nyt.bean.User;
import java.util.HashSet;
import java.util.Set;

public class Order extends PutUUID {
	private Date ordertime;//�µ�ʱ��
	private boolean state;//����״̬
	private double price;//�����ܼ�
	private User user;//��ס�µ���
	private Set<Orderitem> orderitems=new HashSet<Orderitem>();//��ס�������еĶ�����
	
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
