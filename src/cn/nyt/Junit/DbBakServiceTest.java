package cn.nyt.Junit;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import cn.nyt.bean.DbBak;
import cn.nyt.factory.DaoFactory;
import cn.nyt.service.BusinessService;
import cn.nyt.utils.JdbcUtils_C3P0;

public class DbBakServiceTest {

	BusinessService service =DaoFactory.getInstance().createInstance(BusinessService.class);
	@Test
	public void testAdd(){
		for(int i=1;i<4;i++){
			DbBak bak=new DbBak();
			bak.setBaktime(new Date());
			bak.setDescription("测试备份"+i);
			bak.setFilename("测试备份"+i);
			service.addBak(bak);
		}
	}
	@Test
	public void testGetAll(){
		List<DbBak> baks = service.getAllBak();
		for (DbBak dbBak : baks) {
			System.out.println(dbBak);
			
		}
	}
	@Test
	public void testFindBak(){
		DbBak bak = service.findBak("058c9b9f-4ea9-4ab8-8b7f-ec77f06c6ae1");
		System.out.println(bak);
			
	}
	
}
