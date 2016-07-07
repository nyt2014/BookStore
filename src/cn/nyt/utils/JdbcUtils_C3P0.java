package cn.nyt.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_C3P0 {

	
	//加载驱动，静态加载
	//创建数据库连接池 c3p0 静态创建
	//将连接与当前线程绑定 ThreadLocal 静态容器 
	//功能一：获取当前线程上绑定的一个连接
	//功能二：获取连接池
	//功能三：开启事务
	//功能四：提交事务
	//功能五：关闭连接，同时解除连接与当前线程的绑定，并从ThreadLocal容器中移除连接
	
	private static DataSource ds=null;
	private static DataSource dbbak_ds=null;
	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	static {
		try {
			ds=new ComboPooledDataSource();
			//由于数据库的备份信息是单独记录在另外一个数据库，因此需要给这个数据库再创建一个连接池
			dbbak_ds=new ComboPooledDataSource("dbbak");//获取配置信息中指定数据库的连接池
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//获取连接池
	public static DataSource getDataSource(){
		return ds;
	}
	public static DataSource getDataSource_dbbak(){
		return dbbak_ds;
	}
	
	//获取当前线程上的一个开启事务的连接
	public static Connection getConnection() {
		
		try {
			//获取当前线程上的一个连接
			Connection conn = tl.get();
			if(conn==null){//若是当前线程上没有连接
				conn=ds.getConnection();
				//开启事务
				conn.setAutoCommit(false);
				//绑定到当前线程t1
				tl.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//开启事务
	public static void startTransaction(){
		
		try {
			Connection conn = tl.get();
			if(conn==null){//若是当前线程上没有连接
				conn=getConnection();
			}
			//开启事务
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//提交事务
	public static void commitTransaction() {
		
		try {
			Connection conn = getConnection();
			if(conn!=null){
				//提交
				conn.commit();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//关闭连接
	public static void close() {
		try {
			Connection conn = getConnection();
			if(conn!=null){
				conn.close();
			}
			
		} catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			//从当前线程上移除连接
			tl.remove();
		}
		
	} 
	
	
	
}
