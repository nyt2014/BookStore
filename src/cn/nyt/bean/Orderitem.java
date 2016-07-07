package cn.nyt.bean;

/**
 *订单项 
 */
public class Orderitem extends PutUUID {

	private Book book;//记住订单项代表的事哪本书
	private int quantity;//书的数量
	private double price;// 价格
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Orderitem [book=" + book + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
	
	
}
