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
 *�������ȫվ�������� 
 *����get��ʽ�ύ������
 *ʹ�ð�װ���ģʽ��request���������ǿ,���get��ʽ�ύ��������
 */
public class CharacterEncodingFilter implements Filter {

	private FilterConfig config;
	//����ȱʡ�ַ���
	private String defaultCharset="UTF-8";
	public void init(FilterConfig filterConfig) throws ServletException {

		this.config=filterConfig;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//��ȡ���õĳ�ʼ���������ַ�����
		String charset = config.getInitParameter("charset");
//		System.out.println("charset="+charset);
		if(charset==null){
			charset=this.defaultCharset;
		}
		
		request.setCharacterEncoding(charset);//ֻ�����post�������⣬û�н��get�ύ����������
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);
		
		//����
//		chain.doFilter(new MyRequest(request), response);
		chain.doFilter(request, response);
		
		

	}
	
	/**
	 * ��װ���ģʽ���裺
	 * 1.дһ���࣬ʵ���뱻��ǿ������ͬ�Ľӿ�
	 * 2.����һ���뱻��ǿ����������ͬ�ı�������ס����ǿ����
	 * 3.����һ�����캯�������ܱ���ǿ����
	 * 4.������Ҫ��ǿ�ķ���
	 * 5.���ڲ�����ǿ�ķ�����ֱ�ӵ��ñ���ǿ����ķ���
	 */
	
	//����ʹ��sun��˾�ṩ��request��Ĭ�ϰ�װ��
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
