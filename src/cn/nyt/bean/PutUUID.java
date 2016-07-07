package cn.nyt.bean;

import java.util.UUID;

import org.junit.Test;

public class PutUUID {

	private String id;
	public PutUUID(){
		this.id=UUID.randomUUID().toString();
	}
	public String getId() {
		return id;
	}
	//一定要有set方法，否则无法将生成的Id记住到它的子类对象当中
	public void setId(String id) {
		this.id = id;
	}
	
	
	/*@Test
	public void testUUID(){
		
//		this.id=UUID.randomUUID().toString();
		System.out.println(id);
	}*/
}
