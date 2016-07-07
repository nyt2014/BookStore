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
			//因为用户不是每一次都访问Dao层操作数据库，因此为提高网站的访问性能和访问效率，在当前线程上
			//从连接池获取一个链接开启事务，并把链接绑定到当前线程上的动作代码就放在Dao在执行
			//因此用户访问先放行
			chain.doFilter(request, response);
			
			//记得提交事务
			JdbcUtils_C3P0.commitTransaction();
		} finally{
			//每一次访问完之后不管是否有事务的开启都要关闭资源
			JdbcUtils_C3P0.close();
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
