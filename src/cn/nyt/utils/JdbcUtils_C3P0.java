package cn.nyt.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_C3P0 {

	
	//������������̬����
	//�������ݿ����ӳ� c3p0 ��̬����
	//�������뵱ǰ�̰߳� ThreadLocal ��̬���� 
	//����һ����ȡ��ǰ�߳��ϰ󶨵�һ������
	//���ܶ�����ȡ���ӳ�
	//����������������
	//�����ģ��ύ����
	//�����壺�ر����ӣ�ͬʱ��������뵱ǰ�̵߳İ󶨣�����ThreadLocal�������Ƴ�����
	
	private static DataSource ds=null;
	private static DataSource dbbak_ds=null;
	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	static {
		try {
			ds=new ComboPooledDataSource();
			//�������ݿ�ı�����Ϣ�ǵ�����¼������һ�����ݿ⣬�����Ҫ��������ݿ��ٴ���һ�����ӳ�
			dbbak_ds=new ComboPooledDataSource("dbbak");//��ȡ������Ϣ��ָ�����ݿ�����ӳ�
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//��ȡ���ӳ�
	public static DataSource getDataSource(){
		return ds;
	}
	public static DataSource getDataSource_dbbak(){
		return dbbak_ds;
	}
	
	//��ȡ��ǰ�߳��ϵ�һ���������������
	public static Connection getConnection() {
		
		try {
			//��ȡ��ǰ�߳��ϵ�һ������
			Connection conn = tl.get();
			if(conn==null){//���ǵ�ǰ�߳���û������
				conn=ds.getConnection();
				//��������
				conn.setAutoCommit(false);
				//�󶨵���ǰ�߳�t1
				tl.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//��������
	public static void startTransaction(){
		
		try {
			Connection conn = tl.get();
			if(conn==null){//���ǵ�ǰ�߳���û������
				conn=getConnection();
			}
			//��������
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//�ύ����
	public static void commitTransaction() {
		
		try {
			Connection conn = getConnection();
			if(conn!=null){
				//�ύ
				conn.commit();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	//�ر�����
	public static void close() {
		try {
			Connection conn = getConnection();
			if(conn!=null){
				conn.close();
			}
			
		} catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			//�ӵ�ǰ�߳����Ƴ�����
			tl.remove();
		}
		
	} 
	
	
	
}
