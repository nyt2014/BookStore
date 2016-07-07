package cn.nyt.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {

	//创建properties对象
	//私有化构造函数（单例模式），向外面暴漏一个公共的获取该工厂唯一一个实例的静态方法
	//获取流读取配置文件
	//将流中读取到的数据加载到properties对象
	//提供创建生产Dao对象的工厂
	//反射技术为传进来的类创建类对象
	private static Properties daoconfig=new Properties();
	private DaoFactory(){}
	static {
		try {
			InputStream in = DaoFactory.class.getClassLoader().getResourceAsStream("cn/nyt/factory/dao.properties");
			daoconfig.load(in);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	private static final DaoFactory instance=new DaoFactory(); 
	public static  DaoFactory getInstance(){
		return instance;
	}
	
	public <T> T createInstance(Class<T> interfaceClass){
		
		//获取传入接口的简单名称
		String name = interfaceClass.getSimpleName();
		//到properties对象中根据名称查找对应需要产生对象的类名
		String daoClass = daoconfig.getProperty(name);
		
		try {
			//通过反射技术，根据获取到的类名创建实例
			T instance_T=(T)Class.forName(daoClass).newInstance();
			return instance_T;
		} catch (Exception e) {
			throw new RuntimeException();
		} 
	}
}
