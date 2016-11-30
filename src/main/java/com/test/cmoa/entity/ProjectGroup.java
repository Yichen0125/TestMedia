package com.test.cmoa.entity;

import java.util.Date;
import java.util.List;

public class ProjectGroup extends IdEntity{
		//项目组编号
		private String groupnum;
		//负责人
		private User user;
		//成员
		private Employee emp;
		//建档日期
		private Date date;
		public String getGroupnum() {
			return groupnum;
		}
		public void setGroupnum(String groupnum) {
			this.groupnum = groupnum;
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
		public Employee getEmp() {
			return emp;
		}
		public void setEmp(Employee emp) {
			this.emp = emp;
		}
		@Override
		public String toString() {
			return "ProjectGroup [groupnum=" + groupnum + ", user=" + user + ", emp=" + emp + ", date=" + date + "]";
		}
		public ProjectGroup(String groupnum, User user, Employee emp, Date date) {
			super();
			this.groupnum = groupnum;
			this.user = user;
			this.emp = emp;
			this.date = date;
		}
		public ProjectGroup() {
			super();
		}
		
}
