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

	//将工厂设计成单例
	private ServiceFactory(){}
	private static ServiceFactory instance =new ServiceFactory();
	public static ServiceFactory genInstance(){
		return instance;
	}
	
	public BusinessService createService(final User user){
		//在匿名内部类中使用到了这个对象
		final BusinessService service = new BusinessServiceImpl();
															//类加载器									//被代理对象实现的接口			//实现对被代理对象的增强	
		return (BusinessService) Proxy.newProxyInstance(ServiceFactory.class.getClassLoader(), service.getClass().getInterfaces(),new InvocationHandler(){
												//被代理对象的接口方法的method实例	//对应方法的参数数组	
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				//这里对service的方法进行代理增强
				
				//获取web层调用的service中的方法
				String methodName = method.getName();
				
				//反射出真实对象（被代理对象）上对应的方法
				Method realMethod=service.getClass().getMethod(methodName, method.getParameterTypes());
				
				//检查真实对象上有没有权限注解
				Permission permission = realMethod.getAnnotation(Permission.class);
				
				//若是方法上没有权限注解，则放行、
				if(permission==null){
					return method.invoke(service, args);
				}
				
				//若是有注解,则得到访问该方法需要的权限
				Privilege p = new Privilege(permission.value());
				
				//检查用户是否有权限
				//得到用户所有的权限
				if(user==null){
					//向web层抛出一个编译时的自定义异常
					throw new MySecurityException("您没有登录！");
				}
				
				List<Privilege> list = service.getUserAllprivilege(user);
				if(list.contains(p)){//如果用户存在该权限则放行
					return method.invoke(service, args);
				}
				
				//如果用户没有权限则抛出一个编译时的自定义异常
				throw new MySecurityException("您没有该权限！");
				
			}});
		
		
	}
	
}
