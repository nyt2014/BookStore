package cn.nyt.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

	//键为对应的书的id，值为购物项CartItem（对应的书和价格、数目）
	private Map<String,CartItem> map=new LinkedHashMap<String ,CartItem>();
	private double price;
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	//购物车内的商品总价(购物车内每个购物项价格之和)
	public double getPrice() {
		double totalprice=0;
		for(Map.Entry<String,CartItem> entry: map.entrySet()){
			totalprice+=entry.getValue().getPrice();
		}
		this.price=totalprice;
		return price;
	}
	
	
	public void addBook(Book book){
		CartItem item = map.get(book.getId());
		if(item==null){//购物车内没有添加这本书
			item=new CartItem();
			item.setBook(book);
			item.setQuantity(1);
			map.put(book.getId(), item);
		}else{
			item.setQuantity(item.getQuantity()+1);
		}
	}
}
