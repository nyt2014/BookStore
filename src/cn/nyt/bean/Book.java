package cn.nyt.bean;

public class Book extends PutUUID {
	private String name;//����
	private double price;//�۸�
	private String author;//����
	private String image;//��סͼƬ��λ��
	private String description;
	private Category category;//��Ҫ��ʾ��ķ���
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", price=" + price + ", author=" + author
				+ ", image=" + image + ", description=" + description
				+ ", category=" + category + "]";
	}
	
	
}
