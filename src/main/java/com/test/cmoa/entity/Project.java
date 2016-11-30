package com.test.cmoa.entity;

import java.util.Date;

public class Project extends IdEntity{
	
	private String cus;

	//项目编号
	private String pronum;
	//
	private String name;
	//进度
	private Integer status;
	//负责人
	private User user;
	//建档日期
	private Date date;
	//地址
	private String url;
	//项目小组
	private String groupnum;
	
	private String yq;

	public String getPronum() {
		return pronum;
	}

	public void setPronum(String pronum) {
		this.pronum = pronum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getYq() {
		return yq;
	}

	public void setYq(String yq) {
		this.yq = yq;
	}

	
	public String getCus() {
		return cus;
	}

	public void setCus(String cus) {
		this.cus = cus;
	}

	public String getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(String groupnum) {
		this.groupnum = groupnum;
	}

	public Project(String cus, String pronum, String name, Integer status, User user, Date date, String url,
			String groupnum, String yq) {
		super();
		this.cus = cus;
		this.pronum = pronum;
		this.name = name;
		this.status = status;
		this.user = user;
		this.date = date;
		this.url = url;
		this.groupnum = groupnum;
		this.yq = yq;
	}

	public Project() {
		super();
	}

	@Override
	public String toString() {
		return "Project [cus=" + cus + ", pronum=" + pronum + ", name=" + name + ", status=" + status + ", user=" + user
				+ ", date=" + date + ", url=" + url + ", groupnum=" + groupnum + ", yq=" + yq + "]";
	}

	
	
}
