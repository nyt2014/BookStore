package cn.nyt.bean;

public class QueryInfo {

	private int currentpage=1;//Ĭ�ϵ�һҳ
	private int pagesize=9;//Ĭ����ʾ������¼
	private int startindex;//��ʼ����
	
	private String queryname;
	private String queryvalue;
	
	private String where;
	
	public String getWhere() {
		if(this.queryname==null || this.queryname.trim().equals("")){
			return "";
		}else{
			this.where="where "+queryname+"=?";
		}
		return where;
	}
	
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getStartindex() {
		this.startindex=(this.currentpage-1)*this.pagesize;
		return startindex;
	}
	
	public String getQueryname() {
		return queryname;
	}
	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}
	public String getQueryvalue() {
		return queryvalue;
	}
	public void setQueryvalue(String queryvalue) {
		this.queryvalue = queryvalue;
	}
	
	
}
