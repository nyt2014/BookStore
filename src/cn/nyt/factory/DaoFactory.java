package cn.nyt.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {

	//����properties����
	//˽�л����캯��������ģʽ���������汩©һ�������Ļ�ȡ�ù���Ψһһ��ʵ���ľ�̬����
	//��ȡ����ȡ�����ļ�
	//�����ж�ȡ�������ݼ��ص�properties����
	//�ṩ��������Dao����Ĺ���
	//���似��Ϊ���������ഴ�������
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
		
		//��ȡ����ӿڵļ�����
		String name = interfaceClass.getSimpleName();
		//��properties�����и������Ʋ��Ҷ�Ӧ��Ҫ�������������
		String daoClass = daoconfig.getProperty(name);
		
		try {
			//ͨ�����似�������ݻ�ȡ������������ʵ��
			T instance_T=(T)Class.forName(daoClass).newInstance();
			return instance_T;
		} catch (Exception e) {
			throw new RuntimeException();
		} 
	}
}
