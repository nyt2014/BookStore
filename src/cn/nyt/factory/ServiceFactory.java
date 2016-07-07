package cn.nyt.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import cn.nyt.bean.Privilege;
import cn.nyt.bean.User;
import cn.nyt.exception.MySecurityException;
import cn.nyt.service.BusinessService;
import cn.nyt.service.impl.BusinessServiceImpl;
import cn.nyt.utils.Permission;

public class ServiceFactory {

	//��������Ƴɵ���
	private ServiceFactory(){}
	private static ServiceFactory instance =new ServiceFactory();
	public static ServiceFactory genInstance(){
		return instance;
	}
	
	public BusinessService createService(final User user){
		//�������ڲ�����ʹ�õ����������
		final BusinessService service = new BusinessServiceImpl();
															//�������									//���������ʵ�ֵĽӿ�			//ʵ�ֶԱ�����������ǿ	
		return (BusinessService) Proxy.newProxyInstance(ServiceFactory.class.getClassLoader(), service.getClass().getInterfaces(),new InvocationHandler(){
												//���������Ľӿڷ�����methodʵ��	//��Ӧ�����Ĳ�������	
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				//�����service�ķ������д�����ǿ
				
				//��ȡweb����õ�service�еķ���
				String methodName = method.getName();
				
				//�������ʵ���󣨱���������϶�Ӧ�ķ���
				Method realMethod=service.getClass().getMethod(methodName, method.getParameterTypes());
				
				//�����ʵ��������û��Ȩ��ע��
				Permission permission = realMethod.getAnnotation(Permission.class);
				
				//���Ƿ�����û��Ȩ��ע�⣬����С�
				if(permission==null){
					return method.invoke(service, args);
				}
				
				//������ע��,��õ����ʸ÷�����Ҫ��Ȩ��
				Privilege p = new Privilege(permission.value());
				
				//����û��Ƿ���Ȩ��
				//�õ��û����е�Ȩ��
				if(user==null){
					//��web���׳�һ������ʱ���Զ����쳣
					throw new MySecurityException("��û�е�¼��");
				}
				
				List<Privilege> list = service.getUserAllprivilege(user);
				if(list.contains(p)){//����û����ڸ�Ȩ�������
					return method.invoke(service, args);
				}
				
				//����û�û��Ȩ�����׳�һ������ʱ���Զ����쳣
				throw new MySecurityException("��û�и�Ȩ�ޣ�");
				
			}});
		
		
	}
	
}
