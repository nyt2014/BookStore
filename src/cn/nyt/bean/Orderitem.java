package cn.nyt.bean;

/**
 *������ 
 */
public class Orderitem extends PutUUID {

	private Book book;//��ס�������������ı���
	private int quantity;//�������
	private double price;// �۸�
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
