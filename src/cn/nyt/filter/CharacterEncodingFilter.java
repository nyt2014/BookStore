package cn.nyt.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
/**
 *真正解决全站乱码问题 
 *包括get方式提交的数据
 *使用包装设计模式对request对象进行增强,解决get方式提交乱码问题
 */
public class CharacterEncodingFilter implements Filter {

	private FilterConfig config;
	//设置缺省字符集
	private String defaultCharset="UTF-8";
	public void init(FilterConfig filterConfig) throws ServletException {

		this.config=filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//获取配置的初始化参数（字符集）
		String charset = config.getInitParameter("charset");
//		System.out.println("charset="+charset);
		if(charset==null){
			charset=this.defaultCharset;
		}
		
		request.setCharacterEncoding(charset);//只解决了post乱码问题，没有解决get提交的乱码问题
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);
		
		//放行
//		chain.doFilter(new MyRequest(request), response);
		chain.doFilter(request, response);
		
		

	}
	
	/**
	 * 包装设计模式步骤：
	 * 1.写一个类，实现与被增强对象相同的接口
	 * 2.定义一个与被增强对象类型相同的变量，记住被增强对象
	 * 3.定义一个构造函数，接受被增强对象
	 * 4.覆盖需要增强的方法
	 * 5.对于不想增强的方法，直接调用被增强对象的方法
	 */
	
	//这里使用sun公司提供的request的默认包装类
	public class MyRequest extends HttpServletRequestWrapper{

		private HttpServletRequest request;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request=request;
		}
		@Override
		public String getParameter(String name) {
			String value = request.getParameter(name);
			if(!request.getMethod().equalsIgnoreCase("get")){
				return value;
			}
			if(value==null){
				return null;
			}
			
			try {
				return new String(value.getBytes("iso8859-1"), request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
