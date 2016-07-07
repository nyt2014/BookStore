package cn.nyt.bean;

import java.util.Date;

public class DbBak extends PutUUID {

	private String filename;
	private Date baktime;
	private String description;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getBaktime() {
		return baktime;
	}
	public void setBaktime(Date baktime) {
		this.baktime = baktime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DbBak [filename=" + filename + ", baktime=" + baktime
				+ ", description=" + description + ", getId()=" + getId() + "]";
	}
	
	
}
