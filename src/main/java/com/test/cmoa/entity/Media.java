package com.test.cmoa.entity;

import java.util.Date;
public class Media {
	private Integer id;
	private String title;
	private String src;
	private String picture;
	private String descript;
	private Date uptime;
	//是否已经转码
	private boolean convert;
	//远程服务器IP
	private String remoteUrl;
	//是否在转码区
	private boolean convertArea;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	public boolean isConvert() {
		return convert;
	}
	public void setConvert(boolean convert) {
		this.convert = convert;
	}
	public String getRemoteUrl() {
		return remoteUrl;
	}
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	public boolean isConvertArea() {
		return convertArea;
	}
	public void setConvertArea(boolean convertArea) {
		this.convertArea = convertArea;
	}
	public Media(Integer id, String title, String src, String picture, String descript, Date uptime, boolean convert,
			String remoteUrl, boolean convertArea) {
		super();
		this.id = id;
		this.title = title;
		this.src = src;
		this.picture = picture;
		this.descript = descript;
		this.uptime = uptime;
		this.convert = convert;
		this.remoteUrl = remoteUrl;
		this.convertArea = convertArea;
	}
	public Media() {
		super();
	}
	@Override
	public String toString() {
		return "Media [id=" + id + ", title=" + title + ", src=" + src + ", picture=" + picture + ", descript="
				+ descript + ", uptime=" + uptime + ", convert=" + convert + ", remoteUrl=" + remoteUrl
				+ ", convertArea=" + convertArea + "]";
	}
	
}
