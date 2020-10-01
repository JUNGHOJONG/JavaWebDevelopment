package spms.vo;

import java.util.Date;

public class Project {
	
	protected int no;
	protected String title;
	protected String content;
	protected Date startDate;
	protected Date endDate;
	protected int state;
	protected Date createdDate;
	protected String tags;
	
	public Project setNo(int no) {
		this.no = no;
		return this;
	}
	
	public int getNo() {
		return this.no;
	}
	
	public Project setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Project setContent(String content) {
		this.content = content;
		return this;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public Project setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Project setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public Project setState(int state) {
		this.state = state;
		return this;
	}
	
	public int getState() {
		return this.state;
	}
	
	public Project setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	public Project setTags(String tags) {
		this.tags = tags;
		return this;
	}
	
	public String getTag() {
		return this.tags;
	}

}
