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
	//һ��Ҫ��set�����������޷������ɵ�Id��ס���������������
	public void setId(String id) {
		this.id = id;
	}
	
	
	/*@Test
	public void testUUID(){
		
//		this.id=UUID.randomUUID().toString();
		System.out.println(id);
	}*/
}
