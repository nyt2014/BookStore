package cn.nyt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.nyt.utils.JdbcUtils_C3P0;

public class TransactionFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		
		try {
			//��Ϊ�û�����ÿһ�ζ�����Dao��������ݿ⣬���Ϊ�����վ�ķ������ܺͷ���Ч�ʣ��ڵ�ǰ�߳���
			//�����ӳػ�ȡһ�����ӿ������񣬲������Ӱ󶨵���ǰ�߳��ϵĶ�������ͷ���Dao��ִ��
			//����û������ȷ���
			chain.doFilter(request, response);
			
			//�ǵ��ύ����
			JdbcUtils_C3P0.commitTransaction();
		} finally{
			//ÿһ�η�����֮�󲻹��Ƿ�������Ŀ�����Ҫ�ر���Դ
			JdbcUtils_C3P0.close();
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
